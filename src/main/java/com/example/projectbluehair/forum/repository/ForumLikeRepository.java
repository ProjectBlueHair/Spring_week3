package com.example.projectbluehair.forum.repository;

import com.example.projectbluehair.forum.entity.ForumLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumLikeRepository extends JpaRepository<ForumLike, Long> {
}
