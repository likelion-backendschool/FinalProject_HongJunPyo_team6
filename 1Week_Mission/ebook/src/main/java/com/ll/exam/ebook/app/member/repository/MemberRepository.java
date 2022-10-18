package com.ll.exam.ebook.app.member.repository;

import com.ll.exam.ebook.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByNickname(String nickname);

    @Query("select m.username from Member m where m.email = ?1")
    Optional<String> findUsernameByEmail(String email);
}