package com.example.projectbluehair.forum.dto;

import com.example.projectbluehair.comment.entity.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ForumCommentResponseDto {

    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private String memberName;

    private Long liekCount;

    private boolean liked = false;
    private List<ForumCommentResponseDto> childCommentList = new ArrayList<>();

    public ForumCommentResponseDto(Comment comment, Long commentLikeCount, boolean commentLiked, Long memberId) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.memberName = comment.getMember().getMemberName();
        this.liekCount = commentLikeCount;
        this.liked = commentLiked;

        if (comment.getCommentList().size() > 0) {
            for (int i = 0; i < comment.getCommentList().size(); i++) {
                //1. 댓글 좋아요 개수 체크
                Long childCommentLikeCount = comment.getCommentList().get(i).getCommentLikeList().stream().count();

                //2. 댓글 좋아요 이력 체크 - 미완성///////////
                boolean childCommentLiked = false;

                //System.out.println("222222t2est comment.getCommentList().get(i).getMember().getId()  : " + comment.getCommentList().get(i).getMember().getId() );
               // System.out.println("222222test : memberId : " + memberId);


                for(int x = 0; x < comment.getCommentList().get(i).getCommentLikeList().size(); i++){
                    if (comment.getCommentList().get(i).getCommentLikeList().get(x).getMember().getId().equals(memberId)){
                        childCommentLiked = true;
                        break;
                    }
                }

                //3. 하위 댓글 작업 진행
                childCommentList.add(new ForumCommentResponseDto(comment.getCommentList().get(i), childCommentLikeCount, childCommentLiked, memberId));
            }
        } else {
            this.childCommentList = null;
        }
    }
}
