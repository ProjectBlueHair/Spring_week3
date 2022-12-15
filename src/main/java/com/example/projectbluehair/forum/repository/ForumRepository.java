package com.example.projectbluehair.forum.repository;

import com.example.projectbluehair.forum.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    List<Forum> findAllByOrderByCreatedAtDesc();

    //void deleteAllById(Long forumId);
}
