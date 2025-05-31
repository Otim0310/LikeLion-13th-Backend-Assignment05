package com.likelion.likelioncrud.tag.api;

import com.likelion.likelioncrud.common.error.SuccessCode;
import com.likelion.likelioncrud.common.template.ApiResTemplate;
import com.likelion.likelioncrud.tag.api.dto.request.TagSaveRequestDto;
import com.likelion.likelioncrud.tag.api.dto.request.TagUpdateRequestDto;
import com.likelion.likelioncrud.tag.api.dto.response.TagInfoResponseDto;
import com.likelion.likelioncrud.tag.api.dto.response.TagListResponseDto;
import com.likelion.likelioncrud.tag.application.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/save")
    public ApiResTemplate<String> tagSave(@RequestBody @Valid TagSaveRequestDto tagSaveRequestDto) {
        tagService.tagSave(tagSaveRequestDto);
        return ApiResTemplate.successResponse(SuccessCode.TAG_SAVE_SUCCESS, SuccessCode.TAG_SAVE_SUCCESS.getMessage());
    }

    @GetMapping()
    public ApiResTemplate<TagListResponseDto> getAllTags() {
        TagListResponseDto tagListResponseDto = tagService.getAllTags();
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, tagListResponseDto);
    }

    @GetMapping("/{tagId}")
    public ApiResTemplate<TagInfoResponseDto> getTag(@PathVariable("tagId") Long tagId) {
        TagInfoResponseDto tagInfoResponseDto = tagService.getTag(tagId);
        return ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, tagInfoResponseDto);
    }

    @PatchMapping("/{tagId}")
    public ApiResTemplate<String> tagUpdate(@PathVariable("tagId") Long tagId,
                                            @RequestBody TagUpdateRequestDto tagUpdateRequestDto) {
        tagService.tagUpdate(tagId, tagUpdateRequestDto);

        return ApiResTemplate.successResponse(SuccessCode.TAG_UPDATE_SUCCESS, SuccessCode.TAG_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{tagId}")
    public ApiResTemplate<String> tagDelete(@PathVariable("tagId") Long tagId) {
        tagService.tagDelete(tagId);
        return ApiResTemplate.successResponse(SuccessCode.TAG_DELETE_SUCCESS, SuccessCode.TAG_DELETE_SUCCESS.getMessage());
    }
}