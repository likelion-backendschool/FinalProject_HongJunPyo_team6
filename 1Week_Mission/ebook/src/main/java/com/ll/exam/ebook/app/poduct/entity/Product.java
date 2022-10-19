package com.ll.exam.ebook.app.poduct.entity;

import com.ll.exam.ebook.app.base.entity.BaseEntity;
import com.ll.exam.ebook.app.member.entity.Member;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class Product extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Member Author;

    private String subject;
    private int price;
}
