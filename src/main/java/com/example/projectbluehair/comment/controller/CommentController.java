package com.example.projectbluehair.comment.controller;

import com.example.projectbluehair.comment.dto.CommentSaveRequestDto;
import com.example.projectbluehair.comment.dto.CommentSaveResponseDto;
import com.example.projectbluehair.comment.dto.CommentUpdateRequestDto;
import com.example.projectbluehair.comment.dto.CommentUpdateResponseDto;
import com.example.projectbluehair.comment.service.CommentService;
import com.example.projectbluehair.common.response.SuccessResponse;
import com.example.projectbluehair.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    //private final SuccessResponse successResponse;

    //댓글 작성하기.
    /*@PostMapping(value ={"/forum/comment/{forumId}/{parentCommentId, /forum/comment/{forumId}}"})
    public ResponseEntity<CommentSaveResponseDto> commentPost(@PathVariable long forumId, @PathVariable(required = false) Integer parentCommentId, @RequestBody CommentSaveRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(parentCommentId == null){
            parentCommentId = 0;
        }
        // 1. commentPost 서비스 실행 (CommentResponseDto 반환)
        // 2. ResponseDto에 CommentResponseDto 넣기 -> 민호님 객체 만드시면 수정
        // 3. Response Entity에 담아서 반환
        return new ResponseEntity<>(commentService.commentPost(requestDto.tocommentDto(), userDetails.getMember(), forum_id, parentCommentId), HttpStatus.OK);

    }*/
    //댓글 작성하기
    @PostMapping("/forum/comment/{forumId}")
    public ResponseEntity<CommentSaveResponseDto> commentPost(@PathVariable long forumId, @RequestBody CommentSaveRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.commentPost(requestDto.tocommentDto(), userDetails.getMember(), forumId), HttpStatus.OK);
    }

    //댓글 수정하기.
    /*@PatchMapping("/forum/comment/{comment_id}")
    public ResponseEntity<CommentUpdateResponseDto> commentUpdate(@PathVariable long comment_id, @RequestBody CommentUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.commentUpdate(requestDto.tocommentDto(), userDetails.getMember(), comment_id), HttpStatus.OK);
    }*/
}
