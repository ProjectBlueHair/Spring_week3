package com.example.projectbluehair.member.repository;

import com.example.projectbluehair.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
