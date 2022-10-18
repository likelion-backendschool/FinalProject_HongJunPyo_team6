package com.ll.exam.ebook.app.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.exam.ebook.app.base.entity.BaseEntity;
import com.ll.exam.ebook.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Post extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member author;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content", nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "delete_yn")
    @JsonIgnore
    private DeleteType deleteYn;
}
