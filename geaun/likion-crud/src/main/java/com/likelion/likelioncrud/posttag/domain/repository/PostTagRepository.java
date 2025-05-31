package com.likelion.likelioncrud.posttag.domain.repository;

import com.likelion.likelioncrud.posttag.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
