package com.example.projectbluehair.forum.repository;

import com.example.projectbluehair.forum.entity.ForumLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ForumLikeRepository extends JpaRepository<ForumLike, Long> {
    Long countByForum_ForumId(Long forumId);
    /**
     1. 게시글 좋아요 개수 sql
     - forum_like 테이블 안에 forum_id(게시글ID)를 조건으로 검색함 => WHERE forum1_.forum_id=게시글ID
     - count()를 사용하여 해당 게시글ID의 좋아요 갯수 구함 => COUNT(forumlike0_.forum_like_id) AS 별명

     2. JPA SQL
     SELECT
     COUNT(forumlike0_.forum_like_id) AS col_0_0_
     FORM
     forum_like forumlike0_
     left outer join
     forum forum1_
     on forumlike0_.forum_id=forum1_.forum_id
     WHERE
     forum1_.forum_id=?

     3. (직접 h2에서 확인해보세요) GROUP BY 활용 예)
     SELECT member_id, count(member_id) 좋아요개수
     FROM forum
     GROUP BY member_id;
     */

    boolean existsByForum_ForumIdAndMember_Id(Long forumId, Long id);
    /**
     1. 게시글 좋아요 이력 조회 sql

     2. JPA SQL
     select
     forumlike0_.forum_like_id as col_0_0_
     from
     forum_like forumlike0_
     left outer join
     forum forum1_
     on forumlike0_.forum_id=forum1_.forum_id
     left outer join
     member member2_
     on forumlike0_.member_id=member2_.id
     where
     forum1_.forum_id=?
     and member2_.id=? limit ?


     */



    Optional<ForumLike> findByForum_ForumIdAndMember_Id(Long forumId, Long id);
     //원리 + sql정리하자[[[[[[


    //@Query
    //void deleteByForum_ForumIdAndMember_Id(Long forumId, Long id);
}
