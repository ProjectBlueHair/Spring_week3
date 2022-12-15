package com.example.projectbluehair.forum.dto;

import com.example.projectbluehair.forum.entity.ForumLike;
import com.example.projectbluehair.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ForumDto {
    private Long forumId;
    private String title;
    private Member member;
    private String content;
    private LocalDateTime createdAt;
    private boolean liked;
    private int liekCount;
//    private List<CommentDto> commentDtoList = new ArrayList<>();
}
