package com.example.projectbluehair.common.security;

import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 인증 서비스
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl.loadUserByUsername : " + memberName);

        // 사용자 조회
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // UserDetailsImpl 반환
        return new UserDetailsImpl(member, member.getMemberName(), member.getPassword());
    }
}
