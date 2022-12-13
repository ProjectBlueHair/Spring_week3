package com.example.projectbluehair.common.security;

import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.entity.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 인증 객체
public class UserDetailsImpl implements UserDetails {
    private final Member member;
    private final String memberName;
    private final String password;

    // Constructor
    public UserDetailsImpl(Member member, String memberName, String password) {
        this.member = member;
        this.memberName = memberName;
        this.password = password;
    }

    // Getter
    public Member getMember() {
        return member;
    }

    // Member의 권한을 가져오고, Collection에 넣어서 반환함.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        MemberRole role = member.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.memberName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
