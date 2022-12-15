package com.example.projectbluehair.forum.dto;

import com.example.projectbluehair.comment.entity.Comment;

import com.example.projectbluehair.comment.entity.CommentLike;
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

        //대댓글 작업 시작
        if (comment.getCommentList().size() > 0) {
            for (int i = 0; i < comment.getCommentList().size(); i++) {

                //**공통적으로 사용되는 장문 변수로 담아 축약(도움 범준)
                List<CommentLike> commentLikeList = comment.getCommentList().get(i).getCommentLikeList();

                //1. 댓글 좋아요 개수 체크
                Long childCommentLikeCount = commentLikeList.stream().count(); //범준
                //Long childCommentLikeCount = comment.getCommentList().get(i).getCommentLikeList().stream().count(); //호진


                //2. 댓글 좋아요 이력 체크(기본값 false)
                boolean childCommentLiked = false;

                if (commentLikeList.stream().filter(CommentLiked -> CommentLiked.getMember().getId().equals(memberId)).findAny().orElse(null) != null) {
                    childCommentLiked = true;
                } //범준
                /**
                 for(int x = 0; x < comment.getCommentList().get(i).getCommentLikeList().size(); x++){
                 if (comment.getCommentList().get(i).getCommentLikeList().get(x).getMember().getId().equals(memberId)){
                 childCommentLiked = true;
                 }
                 } //호진
                 */

                //3. 대댓글 하위 댓글 작업 진행
                childCommentList.add(new ForumCommentResponseDto(comment.getCommentList().get(i), childCommentLikeCount, childCommentLiked, memberId));
            }
        } else {
            this.childCommentList = null;
        }
    }
}
