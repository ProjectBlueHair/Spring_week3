package com.example.projectbluehair.forum.repository;

import com.example.projectbluehair.forum.entity.ForumLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForumLikeRepository extends JpaRepository<ForumLike, Long> {
    Long countByForum_ForumId(Long forumId);

    boolean existsByForum_ForumIdAndMember_Id(Long forumId, Long id);

    Long findByForum_ForumIdAndMember_Id(Long forumId, Long id);

}
