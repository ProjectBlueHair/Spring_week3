package com.example.projectbluehair.comment.service;

import com.example.projectbluehair.comment.dto.CommentSaveDto;
import com.example.projectbluehair.comment.dto.CommentSaveResponseDto;
import com.example.projectbluehair.comment.dto.CommentUpdateDto;
import com.example.projectbluehair.comment.dto.CommentUpdateResponseDto;
import com.example.projectbluehair.comment.entity.Comment;
import com.example.projectbluehair.comment.entity.CommentLike;
import com.example.projectbluehair.comment.repository.CommentLikeRepository;
import com.example.projectbluehair.comment.repository.CommentRepository;
import com.example.projectbluehair.forum.entity.Forum;
import com.example.projectbluehair.forum.repository.ForumRepository;
import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:auth.properties")
public class CommentService {

    private final CommentRepository commentRepository;
    private final ForumRepository forumRepository;
    private final MemberRepository memberRepository;
    private final CommentLikeRepository commentLikeRepository;

    //댓글 생성.
    @Transactional
    public CommentSaveResponseDto commentPost(CommentSaveDto commentSaveDto, Member member, long forumId) {
        //댓글 내용
        String content = commentSaveDto.getContent();

        //게시글이 존재하는지 확인
        Forum forum = forumRepository.findById(forumId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        //부모 ID값이 존재하는지 확인. 0은 부모가 없다는 것을 의미한다.
        Long parentCommentId = commentSaveDto.getParentCommentId();

        //parentCommentId에서 0이 들어오면 오류가 발생한다.
        if(parentCommentId == null){
            Comment comment = new Comment(content, member, forum);
            commentRepository.save(comment);
            return new CommentSaveResponseDto(comment);
        } else {
            Comment comment2 = commentRepository.findById(parentCommentId).get();
            Comment comment = new Comment(content,comment2, member, forum);
            commentRepository.save(comment);
            return new CommentSaveResponseDto(comment);
        }
    }

    //댓글 수정
    @Transactional
    public CommentUpdateResponseDto commentupdate(CommentUpdateDto commentUpdateDto, Member member, long commentId) {
        //댓글 내용
        String content = commentUpdateDto.getContent();

        //댓글 존재 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        //댓글 작성자와 사용자가 같은 사람인지 확인.
        if(!comment.getMember().getId().equals(member.getId())){
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        }

        //게시글 수정
        comment.update(content);

        //게시글 좋아요 조회 - 미구현.
        return new CommentUpdateResponseDto(comment);
    }

    //댓글 삭제
    public void commentDelete(long commentId, Member member) {
        //댓글 존재 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        //댓글 작성자와 사용자가 같은 사람인지 확인.
        if(!comment.getMember().getId().equals(member.getId())){
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        }
        commentRepository.deleteById(commentId);
    }

    //좋아요 추가.
    public void addCommentLike(Long commentId, Member member) {
        //Comment가 존재하는지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        //사용자 확인
        Member memberinfo = memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자 정보가 없습니다. ")
        );

        //게시글을 작성한 적 있는지 확인하기.
        boolean liked = commentLikeRepository.existsByComment_CommentIdAndMember_Id(commentId, member.getId());

        if(liked) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        CommentLike commentLike = new CommentLike(comment, memberinfo);
        commentLikeRepository.save(commentLike);

    }

    //좋아요 삭제
    public void cancleCommentLike(Long commentId, Member member) {
        //Comment가 존재하는지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        //사용자 확인
        Member memberinfo = memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자 정보가 없습니다. ")
        );

        CommentLike commentLike = commentLikeRepository.findByComment_CommentIdAndMember_Id(commentId,memberinfo.getId()).orElseThrow(
                () -> new IllegalArgumentException("좋아요를 누른적이 없습니다.")
        );

        commentLikeRepository.delete(commentLike);
    }
}
