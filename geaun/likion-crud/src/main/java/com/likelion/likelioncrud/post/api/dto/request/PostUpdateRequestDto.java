package com.likelion.likelioncrud.post.api.dto.request;

import java.util.List;

public record PostUpdateRequestDto(
        String title,
        String contents,
        List<String> tags
) {
}