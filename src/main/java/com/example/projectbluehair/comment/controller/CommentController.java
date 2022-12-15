package com.example.projectbluehair.comment.controller;

import com.example.projectbluehair.comment.dto.CommentSaveRequestDto;
import com.example.projectbluehair.comment.dto.CommentSaveResponseDto;
import com.example.projectbluehair.comment.dto.CommentUpdateRequestDto;
import com.example.projectbluehair.comment.dto.CommentUpdateResponseDto;
import com.example.projectbluehair.comment.service.CommentService;
import com.example.projectbluehair.common.response.ResponseDto;
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
    private final SuccessResponse successResponse;

    //댓글 작성하기
    @PostMapping("/forum/comment/{forumId}")
    public ResponseEntity<ResponseDto> commentPost(@PathVariable long forumId, @RequestBody CommentSaveRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        //System.out.println("Controller parentCommentId = " + requestDto.getParentCommentId());
        CommentSaveResponseDto data = commentService.commentPost(requestDto.tocommentDto(), userDetails.getMember(), forumId);
        return successResponse.respondDataOnly(HttpStatus.CREATED, data);   //데이터만 전송.
    }

    //댓글 수정하기.
    @PatchMapping("/forum/comment/{commentId}")
    public ResponseEntity<ResponseDto> commentUpdate(@PathVariable long commentId, @RequestBody CommentUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentUpdateResponseDto data = commentService.commentupdate(requestDto.tocommentDto(), userDetails.getMember(), commentId);
        return successResponse.respondDataOnly(HttpStatus.OK, data); //데이터만 전송.
    }

    //댓글 삭제하기
    @DeleteMapping("/forum/comment/{commentId}")
    public ResponseEntity<ResponseDto> commentDelete(@PathVariable long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.commentDelete(commentId, userDetails.getMember());
        return successResponse.respond(HttpStatus.OK, "게시글을 삭제하였습니다.", null);
    }

    //좋아요 누르기.
    @PostMapping("/comment/like/{commentId}")
    public ResponseEntity<ResponseDto> addCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.addCommentLike(commentId, userDetails.getMember());
        return successResponse.respond(HttpStatus.OK, "좋아요 누르기 완료", null);
    }

    //좋아요 취소하기.
    @DeleteMapping("/comment/like/{commentId}")
    public ResponseEntity<ResponseDto> cancleCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.cancleCommentLike(commentId, userDetails.getMember());
        return successResponse.respond(HttpStatus.OK, "좋아요 취소 완료", null);
    }

}
