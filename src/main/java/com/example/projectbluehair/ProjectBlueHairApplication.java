package com.example.projectbluehair;

//import com.example.projectbluehair.comment.dto.CommentDto;
import com.example.projectbluehair.comment.dto.CommentSaveRequestDto;
import com.example.projectbluehair.comment.dto.CommentSaveResponseDto;
import com.example.projectbluehair.comment.entity.Comment;
import com.example.projectbluehair.comment.repository.CommentRepository;
import com.example.projectbluehair.forum.dto.ForumSaveRequestDto;
import com.example.projectbluehair.forum.entity.Forum;
import com.example.projectbluehair.forum.exception.CustomForumErrorCode;
import com.example.projectbluehair.forum.exception.CustomForumException;
import com.example.projectbluehair.forum.repository.ForumRepository;
import com.example.projectbluehair.member.dto.SignUpDto;
import com.example.projectbluehair.member.dto.SignUpRequestDto;
import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.entity.MemberMapper;
import com.example.projectbluehair.member.entity.MemberRole;
import com.example.projectbluehair.member.repository.MemberRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableJpaAuditing
@SpringBootApplication
public class ProjectBlueHairApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectBlueHairApplication.class, args);
    }

    @Resource
    private MemberRepository memberRepository;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ForumRepository forumRepository;

    @Resource
    private CommentRepository commentRepository;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {

            //0. json파일 준비
            InputStream jsonMember = this.getClass().getClassLoader().getResourceAsStream("json/MemberData.json");
            InputStream jsonForum = this.getClass().getClassLoader().getResourceAsStream("json/ForumData.json");
            InputStream jsonComment = this.getClass().getClassLoader().getResourceAsStream("json/CommentData.json");

            //1. 회원저장 >>>> 회원정보 10개 저장, user1은 비밀번호 111, user2는 비밀번호 222
            List<SignUpRequestDto> signUpRequestDtoList = new ObjectMapper().readValue(jsonMember, new TypeReference<>() {
            });
            String password = "";
            for (SignUpRequestDto signUpRequestDto : signUpRequestDtoList) {
                password = passwordEncoder.encode(signUpRequestDto.getPassword());

                Member member = memberMapper.toEntity(signUpRequestDto.getMemberName(), password, MemberRole.USER);
                memberRepository.save(member);
            }

            //2. 게시글 저장 >>>> user1 ~ 5까지만 게시글 2개씩 저장
            List<ForumSaveRequestDto> forumSaveRequestDtoList = new ObjectMapper().readValue(jsonForum, new TypeReference<>() {
            });
            Long i = 0L;
            for (ForumSaveRequestDto forumSaveRequestDto : forumSaveRequestDtoList) {
                i++;
                if (i == 6) {
                    i = 1L;
                }
                Member member = memberRepository.findById(i).orElseThrow( //에러나진 않겠지만
                        () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
                );

                Forum forum = new Forum(forumSaveRequestDto, member);
                forumRepository.save(forum);
            }

            //3. 댓글 저장 >>>> user1, 1번 게시글에 구현
            List<CommentSaveRequestDto> commentSaveReqDtoList = new ObjectMapper().readValue(jsonComment, new TypeReference<>() {
            });

            Forum forum = forumRepository.findById(1L).get();
            String content ="";
            Long j = 0L;
            for (CommentSaveRequestDto commentSaveReqtDto : commentSaveReqDtoList) {
                j++;

                content = commentSaveReqtDto.getContent();

                if (j == 6) {
                    j = 1L;
                } else if(j == 9) {
                    j = 1L;
                }
                Member member = memberRepository.findById(j).orElseThrow( //에러나진 않겠지만
                        () -> new CustomForumException(CustomForumErrorCode.MEMBER_NOT_FOUND)
                );

                Comment comment = new Comment(content, member, forum);
                commentRepository.save(comment);
            }
        };
    }

}
