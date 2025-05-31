package com.likelion.likelioncrud.member.api.dto.request;

import com.likelion.likelioncrud.member.domain.Part;

public record MemberSaveRequestDto(
        String name,
        int age,
        Part part
) {
}
