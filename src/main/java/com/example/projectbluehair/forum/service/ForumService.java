package com.example.projectbluehair.forum.service;

import com.example.projectbluehair.forum.dto.*;
import com.example.projectbluehair.forum.entity.Forum;
import com.example.projectbluehair.forum.entity.ForumLike;
import com.example.projectbluehair.forum.exception.CustomForumErrorCode;
import com.example.projectbluehair.forum.exception.CustomForumException;
import com.example.projectbluehair.forum.repository.ForumLikeRepository;
import com.example.projectbluehair.forum.repository.ForumRepository;
import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ForumService {
    private final MemberRepository memberRepository;
    private final ForumRepository forumRepository;
    private final ForumLikeRepository forumLikeRepository;

    //1. 전체게시글 조회
    @Transactional(readOnly = true)
    public ForumListResponseDto getForumList(String memberName) {
        List<Forum> forumList =  forumRepository.findAllByOrderByCreatedAtDesc();

        ForumListResponseDto forumListResDto = new ForumListResponseDto();

        for (Forum forum : forumList) {
            //2. 좋아요 개수 조회
            Long likeCount = forumLikeRepository.countByForum_ForumId(forum.getForumId());

            //3. 게시글 좋아요 이력 조회 - 사용자 조회 => 게시글 좋아요 유무 조회
            Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                    () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
            );

            boolean liked = forumLikeRepository.existsByForum_ForumIdAndMember_Id(forum.getForumId(), member.getId());

            forumListResDto.addForum(new ForumResponseDto(forum, likeCount, liked));
        }

        return forumListResDto;
    }

    //2. 게시글 작성
    @Transactional
    public ForumSaveResponseDto saveForum(ForumSaveRequestDto forumSaveReqDto, String memberName) {

        //1. 회원 검색
        Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
        );

        //2. 게시글 Dto -> Entity
        Forum forum = new Forum(forumSaveReqDto, member);

        //3. 게시글 저장
        forum = forumRepository.save(forum);

        //4. 작성한 게시글 반환 Entity -> Dto
        return new ForumSaveResponseDto(forum);
    }

    //3. 게시글 조회
    @Transactional(readOnly = true)
    public ForumResponseDto getForum(Long forumId, String memberName) {
        //1. 게시글 조회
        Forum forum = forumRepository.findById(forumId).orElseThrow(
                ()-> new CustomForumException(CustomForumErrorCode.FORUM_NOT_FOUND)
        );

        //2. 좋아요 개수 조회
        Long likeCount = forumLikeRepository.countByForum_ForumId(forumId);

        //3. 게시글 좋아요 이력 조회 - 사용자 조회 => 게시글 좋아요 유무 조회
        Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
        );

        boolean liked = forumLikeRepository.existsByForum_ForumIdAndMember_Id(forumId , member.getId());

        //System.out.println("좋아요 존재 : " + liked);
        //System.out.println("3. 좋아요 개수 ForumService.getForum : " + likeCount);

        //4. 게시글 반환 Entity -> Dto
        return new ForumResponseDto(forum, likeCount, liked);
    }

    //4. 게시물 수정
    @Transactional
    public ForumResponseDto updateForum(Long forumId, ForumUpdateRequestDto forumUpdateReqDto, String memberName) {
        //1. 게시글 조회
        Forum forum = forumRepository.findById(forumId).orElseThrow(
                ()-> new CustomForumException(CustomForumErrorCode.FORUM_NOT_FOUND)
        );

        //2. 사용자 조회
        Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
        );

        //3. 게시글 소유자 검사
        if (!forum.getMember().getId().equals(member.getId())){
            throw new CustomForumException(CustomForumErrorCode.FORUM_NOT_PERMISSION);
        }

        //4. 게시글 수정
        forum.setTitle(forumUpdateReqDto.getTitle());
        forum.setContent(forumUpdateReqDto.getContent());

        /** 구현 방법 논의 필요 - >>>>>
        //5. 좋아요 개수 조회
        Long likeCount = forumLikeRepository.countByForum_ForumId(forumId);

        //6. 게시글 좋아요 이력 조회 - 사용자 조회 => 게시글 좋아요 유무 조회
        member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
        );

        boolean liked = forumLikeRepository.existsByForum_ForumIdAndMember_Id(forumId , member.getId());

        //7. 수정 게시글 반환 Entity -> Dto
        //return new ForumResponseDto(forum, likeCount, liked);
        **/
        return getForum(forumId, memberName);
    }

    //5. 게시글 삭제
    @Transactional
    public void deleteForum(Long forumId, String memberName) {
        //1. 게시글 조회
        Forum forum = forumRepository.findById(forumId).orElseThrow(
                () -> new CustomForumException(CustomForumErrorCode.FORUM_NOT_FOUND)
        );

        //2. 사용자 조회
        Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
        );

        //3. 게시글 소유자 검사
        if (!forum.getMember().getId().equals(member.getId())){
            throw new CustomForumException(CustomForumErrorCode.FORUM_NOT_PERMISSION);
        }

        //4. 게시글 삭제 - 댓글과 삭제 검토 필요>>>>>
        //4-1. 댓글 좋아요 삭제
        //4-2. 댓글 삭제
        //4-3. 게시글 좋아요 삭제
        //forumLikeRepository.deleteByForum_ForumIdAndMember_Id(forum.getForumId(), member.getId());
        //4-4. 게시글 삭제
        forumRepository.deleteById(forum.getForumId());
    }


    //////////////////////////게시글 좋아요///////////////////////////
    // 6, 7 합쳐서 토글 방식 구현 가능 - 검토필요>>>>

    //6. 게시글 좋아요 추가
    @Transactional
    public void addForumLike(Long forumId, String memberName) {
        //1. 게시글 조회
        Forum forum = forumRepository.findById(forumId).orElseThrow(
                ()-> new CustomForumException(CustomForumErrorCode.FORUM_NOT_FOUND)
        );

        //2. 사용자 조회
        Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
        );

        //3. 게시글 좋아요 이력 조회
        boolean liked = forumLikeRepository.existsByForum_ForumIdAndMember_Id(forumId , member.getId());

        if (liked) {
            throw new CustomForumException(CustomForumErrorCode.FORUM_LIKE_ALREADY_EXIST);
        }

        //4. 좋아요 추가
        ForumLike forumLike = new ForumLike(forum, member);
        forumLikeRepository.save(forumLike);
    }

    //7. 게시글 좋아요 삭제
    @Transactional
    public void deleteForumLike(Long forumId, String memberName) {
        //1. 게시글 조회
        Forum forum = forumRepository.findById(forumId).orElseThrow(
                ()-> new CustomForumException(CustomForumErrorCode.FORUM_NOT_FOUND)
        );

        //2. 사용자 조회
        Member member = memberRepository.findByMemberName(memberName).orElseThrow(
                () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
        );

        //3. 게시글 좋아요 조회
        ForumLike forumLike = forumLikeRepository.findByForum_ForumIdAndMember_Id(forumId,member.getId()).orElseThrow(
                () -> new CustomForumException(CustomForumErrorCode.FORUM_LIKE_NOT_FOUND)
        );

        //4. 좋아요 삭제
        forumLikeRepository.delete(forumLike);
    }
}
