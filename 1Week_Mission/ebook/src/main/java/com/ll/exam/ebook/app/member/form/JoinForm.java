package com.ll.exam.ebook.app.member.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class JoinForm {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @NotEmpty
    @Length(min = 2,max = 15,message = "닉네임은 2자 이상 15자 이하로 입력해주세요")
    private String nickname;

}