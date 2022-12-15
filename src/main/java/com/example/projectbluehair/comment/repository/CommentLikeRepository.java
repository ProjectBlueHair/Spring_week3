package com.example.projectbluehair.comment.repository;

import com.example.projectbluehair.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByComment_CommentIdAndMember_Id(Long commentid, Long memberId);

    Long countByComment_CommentId(Long commentId);
}
