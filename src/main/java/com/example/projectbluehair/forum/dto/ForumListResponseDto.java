package com.example.projectbluehair.forum.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ForumListResponseDto {
    List<ForumResponseDto> forumResDtoList = new ArrayList<>();

    public void addForum(ForumResponseDto forumResDto) {
        forumResDtoList.add(forumResDto);
    }
}




