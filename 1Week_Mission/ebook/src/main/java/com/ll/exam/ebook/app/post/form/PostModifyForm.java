package com.ll.exam.ebook.app.post.form;

import com.ll.exam.ebook.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter @Getter
public class PostModifyForm extends BaseEntity {
    @NotEmpty
    private String subject;

    @NotEmpty
    private String content;

    private String hashTagContents;
}