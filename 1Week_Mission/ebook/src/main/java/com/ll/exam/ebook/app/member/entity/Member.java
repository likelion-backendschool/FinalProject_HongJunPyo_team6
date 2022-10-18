package com.ll.exam.ebook.app.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.exam.ebook.app.base.entity.BaseEntity;
import com.ll.exam.ebook.app.member.form.ModifyForm;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private boolean emailVerified;
    private String nickname;

    public String getName() {
        return username;
    }

    public Member(long id) {
        super(id);
    }

    public Member(ModifyForm form) {
        this.email=form.getEmail();
        this.nickname=form.getNickname();
    }
}