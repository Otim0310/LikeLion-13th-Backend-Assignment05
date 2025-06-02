package com.likelion.likelioncrud.post.api;

import com.likelion.likelioncrud.common.template.ApiResTemplate;
import com.likelion.likelioncrud.common.error.SuccessCode;
import com.likelion.likelioncrud.post.api.dto.request.PostSaveRequestDto;
import com.likelion.likelioncrud.post.api.dto.request.PostUpdateRequestDto;
import com.likelion.likelioncrud.post.api.dto.response.PostListResponseDto;
import com.likelion.likelioncrud.post.application.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResTemplate<Void>> postSave(@RequestBody @Valid PostSaveRequestDto postSaveRequestDto) {
        postService.postSave(postSaveRequestDto);
        return ResponseEntity.ok(ApiResTemplate.successWithNoContent(SuccessCode.POST_SAVE_SUCCESS));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResTemplate<PostListResponseDto>> myPostFindAll(@PathVariable("memberId") Long memberId) {
        PostListResponseDto postListResponseDto = postService.postFindMember(memberId);
        return ResponseEntity.ok(ApiResTemplate.successResponse(SuccessCode.GET_SUCCESS, postListResponseDto));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResTemplate<Void>> postUpdate(
            @PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        postService.postUpdate(postId, postUpdateRequestDto);
        return ResponseEntity.ok(ApiResTemplate.successWithNoContent(SuccessCode.POST_UPDATE_SUCCESS));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResTemplate<Void>> postDelete(@PathVariable("postId") Long postId) {
        postService.postDelete(postId);
        return ResponseEntity.ok(ApiResTemplate.successWithNoContent(SuccessCode.POST_DELETE_SUCCESS));
    }
}
