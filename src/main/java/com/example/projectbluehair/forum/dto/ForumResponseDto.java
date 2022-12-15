package com.example.projectbluehair.forum.dto;

import com.example.projectbluehair.forum.entity.Forum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ForumResponseDto {
    private Long forumId;
    private String title;
    private String memberName;
    private String content;
    private LocalDateTime createdAt;
    private Long liekCount;
    private boolean liked;

    private List<ForumCommentResponseDto> commentList = new ArrayList<>(); //<<수정포인트1>> comment부분 수정 필요

    public ForumResponseDto(Forum forum, Long likeCount) {
        this.forumId = forum.getForumId();
        this.title = forum.getTitle();
        this.memberName = forum.getMember().getMemberName();
        this.content = forum.getContent();
        this.createdAt = forum.getCreatedAt();
        this.liekCount = 0L; //최초 게시글 좋아요 0
        this.liked = false;
        this.commentList = null; // forum.getCommentList(); //<<수정포인트1>>
    }

    public ForumResponseDto(Forum forum, Long likeCount, boolean liked, Long memberId) {
        this.forumId = forum.getForumId();
        this.title = forum.getTitle();
        this.memberName = forum.getMember().getMemberName();
        this.content = forum.getContent();
        this.createdAt = forum.getCreatedAt();
        this.liekCount = likeCount;
        this.liked = liked;

        if (forum.getCommentList().size() > 0) {
            for (int i = 0; i < forum.getCommentList().size(); i++) {
                if (forum.getCommentList().get(i).getParentCommentId() == null) {

                    //1. 댓글 좋아요 개수 체크
                    Long commentLikeCount = forum.getCommentList().get(i).getCommentLikeList().stream().count();

                    //2. 댓글 좋아요 이력 체크 - 미완성///////////////
                    boolean commentLiked = false;

                    System.out.println("게시글 : "+forum);
                    System.out.println("게시글 > 코멘트리스트1 : "+forum.getCommentList().get(i).toString());
                    System.out.println("게시글 > 코멘트리스트2: "+forum.getCommentList().toString());
                    System.out.println("게시글 > 코멘트 리스트 > 좋아요 " + forum.getCommentList().get(i).getCommentLikeList().toString());

                    for(int x = 0; x < forum.getCommentList().get(i).getCommentLikeList().size(); i++){
                        System.out.println("test11 : " + forum.getCommentList().get(i).getCommentLikeList().get(x).getMember().getMemberName());

                        if (forum.getCommentList().get(i).getCommentLikeList().get(x).getMember().getId().equals(memberId)){
                            System.out.println("test22 : " + forum.getCommentList().get(i).getCommentLikeList().get(x).getMember().getMemberName());

                            commentLiked = true;
                            break;
                        }
                    }

                    //3. 하위 댓글 작업 진행
                    commentList.add(new ForumCommentResponseDto(forum.getCommentList().get(i), commentLikeCount, commentLiked, memberId));
                }
            }
        } else {
            this.commentList = null;
        } //<<수정포인트1>>


    }


}
