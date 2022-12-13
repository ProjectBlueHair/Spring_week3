package com.example.projectbluehair.forum.repository;

import com.example.projectbluehair.forum.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
