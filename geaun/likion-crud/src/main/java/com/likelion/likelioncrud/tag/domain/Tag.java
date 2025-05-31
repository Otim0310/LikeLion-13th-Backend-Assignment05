package com.likelion.likelioncrud.tag.domain;


import com.likelion.likelioncrud.posttag.domain.PostTag;
import com.likelion.likelioncrud.tag.api.dto.request.TagUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @Builder
    public Tag(String name) {
        this.name = name;
    }

    public void update(TagUpdateRequestDto tagUpdateRequestDto) {
        this.name = tagUpdateRequestDto.name();
    }
}