package com.example.projectbluehair.comment.service;

import com.example.projectbluehair.comment.dto.CommentDto;
import com.example.projectbluehair.comment.dto.CommentSaveResponseDto;
import com.example.projectbluehair.comment.entity.Comment;
import com.example.projectbluehair.comment.repository.CommentRepository;
import com.example.projectbluehair.forum.entity.Forum;
import com.example.projectbluehair.forum.repository.ForumRepository;
import com.example.projectbluehair.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:auth.properties")
public class CommentService {

    private final CommentRepository commentRepository;
    private final ForumRepository forumRepository;

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
    public CommentSaveResponseDto commentPost(CommentDto commentDto, Member member, long forumId) {
        String content = commentDto.getContent();

        Forum forum = forumRepository.findById(forumId).get();

        Comment comment = new Comment(content, member, forum);
        commentRepository.save(comment);

        return new CommentSaveResponseDto(comment);
    }
    /*public CommentUpdateResponseDto commentUpdate(CommentDto commentDto, Member member, long comment_id) {
        String content = commentDto.getContent();


    }*/


}
