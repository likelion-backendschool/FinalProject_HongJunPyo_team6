package com.ll.exam.ebook.app.member.service;

import com.ll.exam.ebook.app.member.entity.Member;
import com.ll.exam.ebook.app.member.exception.AlreadyJoinException;
import com.ll.exam.ebook.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(String username, String password, String email, String nickname) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new AlreadyJoinException();
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .build();

        memberRepository.save(member);

        return member;
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public void update(Member member, String name) {
        Member findMember = memberRepository.findByUsername(name).orElseThrow(() -> new AccessDeniedException("user does not exist"));
        findMember.setNickname(member.getNickname());
        findMember.setEmail(member.getEmail());
    }

    public String findByEmail(String email) {
        return memberRepository.findUsernameByEmail(email).orElse(null);
    }
}
