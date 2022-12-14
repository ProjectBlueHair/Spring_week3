package com.example.projectbluehair.forum.controller;

import com.example.projectbluehair.common.response.ResponseDto;
import com.example.projectbluehair.common.response.SuccessResponse;
import com.example.projectbluehair.forum.dto.ForumResponseDto;
import com.example.projectbluehair.forum.dto.ForumSaveRequestDto;
import com.example.projectbluehair.forum.dto.ForumSaveResponseDto;
import com.example.projectbluehair.forum.dto.ForumUpdateRequestDto;
import com.example.projectbluehair.forum.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("forum")
@RequiredArgsConstructor
public class ForumController {

    private final SuccessResponse successResponse;
    private final ForumService forumService;


    @GetMapping("") //1. 전체게시글 조회///////////
    public ResponseEntity<ResponseDto> getForumList() {
        return successResponse.respondDataOnly(HttpStatus.OK,null); //data만 전송
        //return ResponseEntity.status(HttpStatus.OK).body("전체 조회 요청옴1");
    }

    @PostMapping("") //2. 게시글 작성 - 검토필요>>>>>
    public ResponseEntity<ResponseDto> saveForum(@RequestBody ForumSaveRequestDto forumSaveReqDto, @AuthenticationPrincipal UserDetails userDetails){
        ForumSaveResponseDto data = forumService.saveForum(forumSaveReqDto, userDetails.getUsername());
        return successResponse.respondDataOnly(HttpStatus.CREATED, data); //data만 전송
    }

    @GetMapping("/{forumId}") //3. 게시글 조회 - 검토필요>>>>>
    public ResponseEntity<ResponseDto> getForum(@PathVariable Long forumId,  @AuthenticationPrincipal UserDetails userDetails) {
        ForumResponseDto data = forumService.getForum(forumId, userDetails.getUsername());
        return successResponse.respondDataOnly(HttpStatus.OK, data); //data만 전송
    }

    @PatchMapping("/{forumId}") //4. 게시글 수정 - 검토필요>>>>>
    public ResponseEntity<ResponseDto> updateForum(@PathVariable Long forumId, @RequestBody ForumUpdateRequestDto forumUpdateReqDto, @AuthenticationPrincipal UserDetails userDetails) {
        ForumResponseDto data = forumService.updateForum(forumId, forumUpdateReqDto, userDetails.getUsername());
        return successResponse.respondDataOnly(HttpStatus.OK, data); //data만 전송
    }

    @DeleteMapping("/{forumId}") //5. 게시글 삭제 - 검토필요>>>>>
    public ResponseEntity<ResponseDto> deleteForum(@PathVariable Long forumId, @AuthenticationPrincipal UserDetails userDetails) {
        forumService.deleteForum(forumId, userDetails.getUsername());
        return successResponse.respond(HttpStatus.OK, "게시글을 삭제하였습니다.", null); //data만 전송
    }

    @PostMapping("/like/{forumId}") //6. 게시글 좋아요 추가 - 검토필요>>>>> 6,7과 토글방식으로 진행 가능
    public ResponseEntity<ResponseDto> addForumLike(@PathVariable Long forumId, @AuthenticationPrincipal UserDetails userDetails) {
        forumService.addForumLike(forumId, userDetails.getUsername());
        return successResponse.respond(HttpStatus.OK, "게시글 좋아요가 추가되었습니다.", null); //data만 전송
    }

    @DeleteMapping("/like/{forumId}") //7. 게시글 좋아요 삭제 - 검토필요>>>>>
    public ResponseEntity<ResponseDto> deleteForumLik(@PathVariable Long forumId, @AuthenticationPrincipal UserDetails userDetails) {
        forumService.deleteForumLike(forumId, userDetails.getUsername());
        return successResponse.respond(HttpStatus.OK, "게시글 좋아요가 취소하였습니다.", null); //data만 전송
    }
}
