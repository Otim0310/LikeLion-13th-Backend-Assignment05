package com.likelion.likelioncrud.post.application;

import com.likelion.likelioncrud.common.error.ErrorCode;
import com.likelion.likelioncrud.common.exception.BusinessException;
import com.likelion.likelioncrud.member.domain.Member;
import com.likelion.likelioncrud.member.domain.repository.MemberRepository;
import com.likelion.likelioncrud.post.api.dto.request.PostSaveRequestDto;
import com.likelion.likelioncrud.post.api.dto.request.PostUpdateRequestDto;
import com.likelion.likelioncrud.post.api.dto.response.PostInfoResponseDto;
import com.likelion.likelioncrud.post.api.dto.response.PostListResponseDto;
import com.likelion.likelioncrud.post.domain.Post;
import com.likelion.likelioncrud.post.domain.repository.PostRepository;
import com.likelion.likelioncrud.posttag.domain.PostTag;
import com.likelion.likelioncrud.tag.domain.Tag;
import com.likelion.likelioncrud.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Transactional
    public void postSave(PostSaveRequestDto postSaveRequestDto) {
        Member member = getMember(postSaveRequestDto);

        Post post = Post.builder()
                .title(postSaveRequestDto.title())
                .contents(postSaveRequestDto.contents())
                .member(member)
                .build();

        for (Long tagId : postSaveRequestDto.tags()) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.TAG_NOT_FOUND_EXCEPTION, "Tag not found: " + tagId));

            post.getPostTags().add(new PostTag(post, tag));
        }

        postRepository.save(post);
    }

    public PostListResponseDto postFindMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        List<Post> posts = postRepository.findByMember(member);
        List<PostInfoResponseDto> postInfoResponseDtos = posts.stream()
                .map(PostInfoResponseDto::from)
                .toList();

        return PostListResponseDto.from(postInfoResponseDtos);
    }

    public PostInfoResponseDto findPost(Long postId) {
        Post post = getPost(postId);
        return PostInfoResponseDto.from(post);
    }

    @Transactional
    public void postUpdate(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = getPost(postId);

        post.update(postUpdateRequestDto);

        post.getPostTags().clear();

        for (Long tagId : postUpdateRequestDto.tags()) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.TAG_NOT_FOUND_EXCEPTION, "Tag not found: " + tagId));

            post.getPostTags().add(new PostTag(post, tag));
        }
    }

    @Transactional
    public void postDelete(Long postId) {
        Post post = getPost(postId);
        postRepository.delete(post);
    }

    private Member getMember(PostSaveRequestDto postSaveRequestDto) {
        return memberRepository.findById(postSaveRequestDto.memberId()).orElseThrow(() -> new BusinessException(
                ErrorCode.MEMBER_NOT_FOUND_EXCEPTION, ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage() + postSaveRequestDto.memberId()));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND_EXCEPTION,
                ErrorCode.POST_NOT_FOUND_EXCEPTION.getMessage() + postId));
    }
}
