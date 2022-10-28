package com.ll.exam.finalPrj_3Week.app.postTag.entity;

import com.ll.exam.finalPrj_3Week.app.base.entity.BaseEntity;
import com.ll.exam.finalPrj_3Week.app.member.entity.Member;
import com.ll.exam.finalPrj_3Week.app.post.entity.Post;
import com.ll.exam.finalPrj_3Week.app.postkeyword.entity.PostKeyword;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PostTag extends BaseEntity {
    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @ToString.Exclude
    private PostKeyword postKeyword;
}
