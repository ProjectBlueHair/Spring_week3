package com.example.projectbluehair.comment.repository;

import com.example.projectbluehair.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
