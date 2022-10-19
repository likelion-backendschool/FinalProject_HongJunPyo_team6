package com.ll.exam.ebook.app.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    MEMBER(3),
    ADMIN(7);

    private final Integer auth_level;

}
