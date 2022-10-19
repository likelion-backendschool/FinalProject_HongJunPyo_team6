package com.ll.exam.ebook.app.poduct.entity;

import com.ll.exam.ebook.app.base.entity.BaseEntity;
import com.ll.exam.ebook.app.member.entity.Member;

public class Product extends BaseEntity {

    private Member Author;

    private String subject;
    private int price;
}
