package com.example.projectbluehair.comment.service;

import com.example.projectbluehair.comment.dto.CommentSaveDto;
import com.example.projectbluehair.comment.dto.CommentSaveResponseDto;
import com.example.projectbluehair.comment.dto.CommentUpdateDto;
import com.example.projectbluehair.comment.dto.CommentUpdateResponseDto;
import com.example.projectbluehair.comment.entity.Comment;
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

    /*@Transactional
    public CommentSaveResponseDto commentPost(CommentDto commentDto, Member member, long forumId, Integer parentCommentId) {
        String content = commentDto.getContent();
        //forum repo에서 id로 게시글 찾아서 객체 만들고 밑에 넣어주기.
        //여기 excpetion 처리 해줘야됨.
        //Optional 형식으로 바꿔야 한다. or findById.get()
        //Forum forum = forumRepository.findById(forumId).get();
        Forum forum = forumRepository.findById(forumId);


        //paretCommentId를 사용해서 Comment 객체 만들기 -> 자기 참조..
        //paretCommentId는 null일수도 있다. 근데 null이면 pathVariable 형식이 가능한가?
        //null이면 Optional인가?
        //parentCommentId
        //Comment Comment1 =
        Long parentId = Long.parseLong(String.valueOf(parentCommentId));

        //findById가 null이 된다. -> comment2 = null? 불가능.
        //Optional 은 null 가능
        //Comment comment2 = commentRepository.findById(parentId).get();
        Optional<Comment> comment2 = commentRepository.findById(parentId);
        Comment comment3 = comment2.orElseThrow(
                () -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다.")
        );


        Comment comment = new Comment(content, member, forum, comment3);
        commentRepository.save(comment);

        return new CommentSaveResponseDto(comment);
    }*/
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
        //System.out.println("ServiceparentCommentId = " + parentCommentId);


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
        System.out.println("service_commentID = " + commentId);
        System.out.println("service_content = " + commentUpdateDto.getContent());
        System.out.println("service_member = " + member.getId());

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
        if(comment.getMember().getId().equals(member.getId()) == false){
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        }

        commentRepository.deleteById(commentId);


    }

    public void addCommentLike(long commentId, Member member) {
    }
    //댓글 삭제

}
