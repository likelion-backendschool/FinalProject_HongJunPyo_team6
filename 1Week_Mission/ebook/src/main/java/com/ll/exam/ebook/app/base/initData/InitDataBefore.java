package com.ll.exam.ebook.app.base.initData;

import com.ll.exam.ebook.app.member.entity.Member;
import com.ll.exam.ebook.app.member.service.MemberService;
import com.ll.exam.ebook.app.post.entity.Post;
import com.ll.exam.ebook.app.post.service.PostService;

public interface InitDataBefore {
    default void before(MemberService memberService) {
        Member member1 = memberService.join("user1", "1234", "user1@test.com", "테스트 닉네임");
        Member member2 = memberService.join("user2", "1234", "user2@test.com", "테스트 닉네임");

    }
}