package com.example.projectbluehair.comment.controller;

import com.example.projectbluehair.comment.dto.CommentRequestDto;
import com.example.projectbluehair.comment.dto.CommentResponseDto;
import com.example.projectbluehair.comment.service.CommentService;
import com.example.projectbluehair.common.security.UserDetailsImpl;
import com.example.projectbluehair.member.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/forum/comment/{forum_id}/{parent_comment_id}")
    public ResponseEntity<CommentResponseDto> commentPost(@PathVariable long forum_id, @PathVariable long parent_comment_id, @RequestBody CommentRequestDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        // 1. commentPost 서비스 실행 (CommentResponseDto 반환)
        // 2. ResponseDto에 CommentResponseDto 넣기 -> 민호님 객체 만드시면 수정
        // 3. Response Entity에 담아서 반환
        return new ResponseEntity<>(commentService.commentPost(requestDto.tocommentDto(), userDetails.getMember().getId(), forum_id, parent_comment_id), HttpStatus.OK);
    }

    /*@PatchMapping*/
}
