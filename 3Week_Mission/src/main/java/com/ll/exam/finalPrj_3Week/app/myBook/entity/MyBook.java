package com.ll.exam.finalPrj_3Week.app.myBook.entity;


import com.ll.exam.finalPrj_3Week.app.base.entity.BaseEntity;
import com.ll.exam.finalPrj_3Week.app.member.entity.Member;
import com.ll.exam.finalPrj_3Week.app.order.entity.OrderItem;
import com.ll.exam.finalPrj_3Week.app.product.entity.Product;
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
    @ToString.Exclude
    private Member owner;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Product product;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private OrderItem orderItem;
}
