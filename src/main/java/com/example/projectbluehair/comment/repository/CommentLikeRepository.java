package com.example.projectbluehair.comment.repository;

import com.example.projectbluehair.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    //Comment테이블의 CommentId와 Member테이블의 memberID를 사용한다
    //By뒤는 조건문.
    boolean existsByComment_CommentidAndMember_Id(Long commentId, Long id);

    Optional<CommentLike> findByComment_CommentidAndMember_Id(Long commentId, Long id);
}
