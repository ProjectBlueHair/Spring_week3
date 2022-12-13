package com.example.projectbluehair.comment.service;

import com.example.projectbluehair.comment.dto.CommentDto;
import com.example.projectbluehair.comment.dto.CommentResponseDto;
import com.example.projectbluehair.comment.entity.Comment;
import com.example.projectbluehair.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:auth.properties")
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto commentPost(CommentDto commentDto, long memberId, long forum_id, long parent_comment_id) {
        Comment comment = new Comment(commentDto, memberId, forum_id, parent_comment_id);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
