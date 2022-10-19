package com.ll.exam.ebook.app.hashTag.entity;

import com.ll.exam.ebook.app.base.entity.BaseEntity;
import com.ll.exam.ebook.app.keyword.entity.Keyword;
import com.ll.exam.ebook.app.post.entity.Post;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class HashTag extends BaseEntity {
    @ManyToOne
    private Post post;
    @ManyToOne
    private Keyword keyword;
}
