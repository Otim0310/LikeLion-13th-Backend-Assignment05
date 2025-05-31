package com.likelion.likelioncrud.tag.application;

import com.likelion.likelioncrud.common.error.ErrorCode;
import com.likelion.likelioncrud.common.exception.BusinessException;
import com.likelion.likelioncrud.tag.api.dto.request.TagSaveRequestDto;
import com.likelion.likelioncrud.tag.api.dto.request.TagUpdateRequestDto;
import com.likelion.likelioncrud.tag.api.dto.response.TagInfoResponseDto;
import com.likelion.likelioncrud.tag.api.dto.response.TagListResponseDto;
import com.likelion.likelioncrud.tag.domain.Tag;
import com.likelion.likelioncrud.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public void tagSave(TagSaveRequestDto tagSaveRequestDto) {
        Optional<Tag> existingTag = tagRepository.findByName(tagSaveRequestDto.name());
        if (existingTag.isPresent()) {
            return;
        }

        Tag tag = Tag.builder()
                .name(tagSaveRequestDto.name())
                .build();

        tagRepository.save(tag);
    }

    public TagListResponseDto getAllTags() {
        List<Tag> tags = tagRepository.findAll();

        List<TagInfoResponseDto> tagInfoResponseDtos = tags.stream().map(TagInfoResponseDto::from).toList();
        return TagListResponseDto.from(tagInfoResponseDtos);
    }

    public TagInfoResponseDto getTag(Long tagId) {
        Tag tag = findTag(tagId);
        return TagInfoResponseDto.from(tag);
    }

    @Transactional
    public void tagUpdate(Long tagId, TagUpdateRequestDto tagUpdateRequestDto) {
        Tag tag = findTag(tagId);
        tag.update(tagUpdateRequestDto);
    }

    @Transactional
    public void tagDelete(Long tagId) {
        Tag tag = findTag(tagId);
        tagRepository.delete(tag);
    }

    private Tag findTag(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new BusinessException(
                ErrorCode.TAG_NOT_FOUND_EXCEPTION, ErrorCode.TAG_NOT_FOUND_EXCEPTION.getMessage()));
    }
}