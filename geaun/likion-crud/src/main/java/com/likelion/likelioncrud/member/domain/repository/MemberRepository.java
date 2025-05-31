package com.likelion.likelioncrud.member.domain.repository;


import com.likelion.likelioncrud.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
