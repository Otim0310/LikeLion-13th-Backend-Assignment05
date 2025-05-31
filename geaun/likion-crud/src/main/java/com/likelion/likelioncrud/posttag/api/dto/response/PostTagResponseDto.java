package com.likelion.likelioncrud.posttag.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.likelion.likelioncrud.tag.domain.Tag;

@Getter
@AllArgsConstructor
public class PostTagResponseDto {
    private Long tagId;
    private String tagName;

    public static PostTagResponseDto from(Tag tag) {
        return new PostTagResponseDto(tag.getId(), tag.getName());
    }
}
