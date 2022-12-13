package com.example.projectbluehair.forum.controller;

import com.example.projectbluehair.common.response.ResponseDto;
import com.example.projectbluehair.common.response.SuccessResponse;
import com.example.projectbluehair.forum.dto.ForumSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("forum")
@RequiredArgsConstructor
public class ForumController {

    private final SuccessResponse successResponse;

    @GetMapping("") //1. 전체게시글 조회
    public ResponseEntity<ResponseDto> getForumList() {
        return successResponse.respondDataOnly(HttpStatus.OK,null); //data만 전송
        //return ResponseEntity.status(HttpStatus.OK).body("전체 조회 요청옴1");
    }

    @PostMapping("") //2. 게시글 작성
    public ResponseEntity<ResponseDto> saveForum() { //@AuthenticationPrincipal UserDetails){
        return successResponse.respondDataOnly(HttpStatus.CREATED, null); //data만 전송
        //return ResponseEntity.status(HttpStatus.OK).body("작성 요청옴2");
    }

    @GetMapping("/{forumId}") //3. 게시글 조회
    public ResponseEntity<ResponseDto> getForum(@PathVariable String forumId) {
        return successResponse.respondDataOnly(HttpStatus.OK, null); //data만 전송
        //return ResponseEntity.status(HttpStatus.OK).body("조회 요청옴3" + forumId);
    }

    @PatchMapping("/{forumId}") //4. 게시글 수정
    public ResponseEntity<ResponseDto> updateForum(@PathVariable String forumId) {//, @AuthenticationPrincipal UserDetails){
        return successResponse.respondDataOnly(HttpStatus.OK, null); //data만 전송
        //return ResponseEntity.status(HttpStatus.OK).body("수정 요청옴4");
    }

    @DeleteMapping("/{forumId}") //5. 게시글 탈퇴
    public ResponseEntity<ResponseDto> deleteForum() {
        return successResponse.respond(HttpStatus.OK, "게시글을 삭제하였습니다.", null); //data만 전송
        //return ResponseEntity.status(HttpStatus.OK).body("삭제 요청옴5");
    }

    @PostMapping("/like/{forumId}") //6. 게시글 좋아요 추가
    public ResponseEntity<ResponseDto> addForumLike() {
        return successResponse.respond(HttpStatus.OK, "게시글 좋아요 추가되었습니다.", null); //data만 전송
        //return ResponseEntity.status(HttpStatus.OK).body("좋아요 추가 요청옴6");
    }

    @DeleteMapping("/like/{forumId}") //7. 게시글 좋아요 삭제
    public ResponseEntity<ResponseDto> deleteForumLike() {
        return successResponse.respond(HttpStatus.OK, "게시글 좋아요 취소하였습니다.", null); //data만 전송
        //return ResponseEntity.status(HttpStatus.OK).body("좋아요 삭제 요청옴7");
    }
}
