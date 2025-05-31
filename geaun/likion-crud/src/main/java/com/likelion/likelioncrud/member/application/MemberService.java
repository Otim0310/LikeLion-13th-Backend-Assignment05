package com.likelion.likelioncrud.member.application;

import java.util.List;

import com.likelion.likelioncrud.common.error.ErrorCode;
import com.likelion.likelioncrud.common.exception.BusinessException;
import com.likelion.likelioncrud.member.api.dto.request.MemberSaveRequestDto;
import com.likelion.likelioncrud.member.api.dto.request.MemberUpdateRequestDto;
import com.likelion.likelioncrud.member.api.dto.response.MemberInfoResponseDto;
import com.likelion.likelioncrud.member.api.dto.response.MemberListResponseDto;
import com.likelion.likelioncrud.member.domain.Member;
import com.likelion.likelioncrud.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    // 사용자 정보 저장
    @Transactional
    public void memberSave(MemberSaveRequestDto memberSaveRequestDto) {
        Member member = Member.builder()
                .name(memberSaveRequestDto.name())
                .age(memberSaveRequestDto.age())
                .part(memberSaveRequestDto.part())
                .build();
        memberRepository.save(member);
    }

    // 사용자 모두 조회
    public MemberListResponseDto memberFindAll() {
        List<Member> members = memberRepository.findAll();
        List<MemberInfoResponseDto> memberInfoResponseDtoList = members.stream()
                .map(MemberInfoResponseDto::from)
                .toList();
        return MemberListResponseDto.from(memberInfoResponseDtoList);
    }

    // 단일 사용자 조회
    public MemberInfoResponseDto memberFindOne(Long memberId) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(
                        () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                                ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage() + memberId)
                );
        return MemberInfoResponseDto.from(member);
    }

    //사용자 수정
    @Transactional
    public void memberUpdate(Long memberId,
                             MemberUpdateRequestDto memberUpdateRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        member.update(memberUpdateRequestDto);
    }

    //사용자 삭제
    @Transactional
    public void memberDelete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        memberRepository.delete(member);
    }
}