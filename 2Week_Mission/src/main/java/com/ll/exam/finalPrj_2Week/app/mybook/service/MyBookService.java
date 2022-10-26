package com.ll.exam.finalPrj_2Week.app.mybook.service;

import com.ll.exam.finalPrj_2Week.app.member.entity.Member;
import com.ll.exam.finalPrj_2Week.app.mybook.entity.MyBook;
import com.ll.exam.finalPrj_2Week.app.mybook.repository.MyBookRepository;
import com.ll.exam.finalPrj_2Week.app.order.entity.OrderItem;
import com.ll.exam.finalPrj_2Week.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyBookService {
    private final MyBookRepository myBookRepository;

    @Transactional
    public void createMyBook(Member member, Product product){
        MyBook myBook = MyBook.builder()
                .product(product)
                .member(member)
                .build();
        myBookRepository.save(myBook);
    }

    public void createMyBookList(Member member, List<OrderItem> orderItems){
        for(OrderItem orderItem: orderItems){
            createMyBook(member,orderItem.getProduct());
        }
    }
}
