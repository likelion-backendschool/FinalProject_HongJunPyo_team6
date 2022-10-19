package com.ll.exam.ebook.app.poduct.form;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {

    private Long id;
    private String subject;
    private int price;
    private String writer;
    private LocalDateTime createDate;
}
