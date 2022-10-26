package com.ll.exam.finalPrj_2Week.app.mybook.entity;

import com.ll.exam.finalPrj_2Week.app.base.entity.BaseEntity;
import com.ll.exam.finalPrj_2Week.app.member.entity.Member;
import com.ll.exam.finalPrj_2Week.app.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class MyBook extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private Member member;
    @ManyToOne(fetch = LAZY)
    private Product product;

    public MyBook(Member actor, Product product) {
        this.member = actor;
        this.product = product;
    }
}
