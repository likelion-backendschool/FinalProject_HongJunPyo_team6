package com.ll.exam.finalPrj_2Week.app.base.initData;

import com.ll.exam.finalPrj_2Week.app.cart.entity.CartItem;
import com.ll.exam.finalPrj_2Week.app.cart.service.CartService;
import com.ll.exam.finalPrj_2Week.app.member.entity.Member;
import com.ll.exam.finalPrj_2Week.app.member.service.MemberService;
import com.ll.exam.finalPrj_2Week.app.order.entity.Order;
import com.ll.exam.finalPrj_2Week.app.order.service.OrderService;
import com.ll.exam.finalPrj_2Week.app.post.entity.Post;
import com.ll.exam.finalPrj_2Week.app.post.service.PostService;
import com.ll.exam.finalPrj_2Week.app.product.entity.Product;
import com.ll.exam.finalPrj_2Week.app.product.service.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface InitDataBefore {
    default void before(MemberService memberService, PostService articleService, ProductService productService, CartService cartService, OrderService orderService){
        class Helper {
            public Order order(Member member, List<Product> products) {
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);

                    cartService.addItem(member, product);
                }

                return orderService.createFromCart(member);
            }
        }
        Helper helper = new Helper();

        String password = "1234";
        Member member1 = memberService.join("user1", "1234", "user1@test.com", null);
        Member member2 = memberService.join("user2", "1234", "user2@test.com", "홍길순");

        Post post1 = articleService.write(member1, "제목1", "내용1", "<p>내용1</p>","#자바 #프로그래밍");
        Post post2 = articleService.write(member2, "제목2", "내용2", "<p>내용2</p>","#HTML #프로그래밍");


        List<Post> postList = new ArrayList<>();
        postList.add(post1);
        postList.add(post2);

        Product product1 = productService.create(member1, "제목1", 2_000, "스프링 부트", "#IT");
        Product product2 = productService.create(member2, "제목2", 4_000, "REACT", "#IT");
        Product product3 = productService.create(member1, "제목3", 5_000, "HTML", "#IT");
        Product product4 = productService.create(member2, "제목4", 6_000, "파이썬", "#IT");

        CartItem cartItem1 = cartService.addItem(member1, product1);
        CartItem cartItem2 = cartService.addItem(member1, product2);
        CartItem cartItem3 = cartService.addItem(member2, product3);
        CartItem cartItem4 = cartService.addItem(member2, product4);

        memberService.addCash(member1, 10_000, "충전__무통장입금");
        memberService.addCash(member1, 20_000, "충전__무통장입금");
        memberService.addCash(member1, -5_000, "출금__일반");
        memberService.addCash(member1, 1_000_000, "충전__무통장입금");
        memberService.addCash(member2, 2_000_000, "충전__무통장입금");

        // 1번 주문 : 결제완료
        Order order1 = helper.order(member1, Arrays.asList(
                        product1,
                        product2
                )
        );

        orderService.payByRestCashOnly(order1);

        // 2번 주문 : 결제 후 환불
        Order order2 = helper.order(member2, Arrays.asList(
                        product3,
                        product4
                )
        );

        orderService.payByRestCashOnly(order2);

        orderService.refund(order2);


        // 3번 주문 : 결제 전
        Order order3 = helper.order(member2, Arrays.asList(
                        product1,
                        product2
                )
        );
        cartService.addItem(member1, product1);
        cartService.addItem(member1, product2);
        cartService.addItem(member1, product3);

        cartService.addItem(member2, product4);
    }
}