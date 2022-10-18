package com.ll.exam.ebook.app.member.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ModifyForm {
    @NotEmpty
    @Length(min = 2,max = 15,message = "닉네임은 2자 이상 15자 이하로 입력해주세요")
    private String nickname;

    @NotEmpty
    @Email
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식을 다시확인해주세요")
    private String email;
}