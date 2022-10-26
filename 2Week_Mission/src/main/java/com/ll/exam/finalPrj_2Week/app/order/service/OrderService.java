package com.ll.exam.finalPrj_2Week.app.order.service;

import com.ll.exam.finalPrj_2Week.app.cart.entity.CartItem;
import com.ll.exam.finalPrj_2Week.app.cart.service.CartService;
import com.ll.exam.finalPrj_2Week.app.member.entity.Member;
import com.ll.exam.finalPrj_2Week.app.member.service.MemberService;
import com.ll.exam.finalPrj_2Week.app.order.entity.Order;
import com.ll.exam.finalPrj_2Week.app.order.entity.OrderItem;
import com.ll.exam.finalPrj_2Week.app.order.repository.OrderItemRepository;
import com.ll.exam.finalPrj_2Week.app.order.repository.OrderRepository;
import com.ll.exam.finalPrj_2Week.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Order createFromCart(Member buyer,String ids) {

        // 입력된 회원의 장바구니 아이템들을 전부 가져온다.

        // 만약에 특정 장바구니의 상품옵션이 판매불능이면 삭제
        // 만약에 특정 장바구니의 상품옵션이 판매가능이면 주문품목으로 옮긴 후 삭제
        List<Long> collect = Arrays.stream(ids.split(",")).mapToLong(Long::valueOf).boxed().collect(toList());
        List<CartItem> cartItems = cartService.getItemsByBuyer(buyer);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            Long id = cartItem.getId();
            if (product.isOrderable()&&collect.contains(id)) {
                orderItems.add(new OrderItem(product));
            }
        }
        return create(buyer, orderItems);
    }

    //주문품목 리스트와 구매자를 받아 주문을 만듬
    @Transactional
    public Order create(Member buyer, List<OrderItem> orderItems) {
        Order order = Order
                .builder()
                .buyer(buyer)
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        // 주문 품목으로 부터 이름을 만든다.
        order.makeName();

        orderRepository.save(order);

        return order;
    }

    //비용 지불
    @Transactional
    public void payByRestCashOnly(Order order) {
        Member buyer = order.getBuyer();

        long restCash = buyer.getRestCash();

        int payPrice = order.calculatePayPrice();

        if (payPrice > restCash) {
            throw new RuntimeException("예치금이 부족합니다.");
        }

        memberService.addCash(buyer, payPrice * -1, "주문__%d__사용__예치금".formatted(order.getId()));

        order.setPaymentDone();
        removeCart(order, buyer);
        orderRepository.save(order);
    }

    private void removeCart(Order order, Member buyer) {
        order.getOrderItems().forEach(i->
                cartService.removeItem(buyer,i.getProduct().getId())
        );
    }

    @Transactional
    public void refund(Order order) {
        int payPrice = order.getPayPrice();
        memberService.addCash(order.getBuyer(), payPrice, "주문__%d__환불__예치금".formatted(order.getId()));

        order.setRefundDone();
        orderRepository.save(order);
    }

    public Optional<Order> findForPrintById(long id) {
        return findById(id);
    }
    private Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    public boolean actorCanSee(Member actor, Order order) {
        return actor.getId().equals(order.getBuyer().getId());
    }

    public Order getOrderByBuyer(Member buyer) {
        return orderRepository.findAllByBuyerId(buyer.getId()).orElse(null);
    }

    public void cancelOrder(Order order) {
        orderRepository.delete(order);
    }

    @Transactional
    public void payByTossPayments(Order order, long useRestCash) {
        Member buyer = order.getBuyer();
        int payPrice = order.calculatePayPrice();

        long pgPayPrice = payPrice - useRestCash;
        memberService.addCash(buyer, pgPayPrice, "주문__%d__충전__토스페이먼츠".formatted(order.getId()));
        memberService.addCash(buyer, pgPayPrice * -1, "주문__%d__사용__토스페이먼츠".formatted(order.getId()));

        if ( useRestCash > 0 ) {
            memberService.addCash(buyer, useRestCash * -1, "주문__%d__사용__예치금".formatted(order.getId()));
        }
        order.setPaymentDone();

        removeCart(order, buyer);

        orderRepository.save(order);
    }

    public boolean actorCanPayment(Member actor, Order order) {
        return actorCanSee(actor, order);
    }

    public List<OrderItem> findAllByPayDateBetweenOrderByIdAsc(LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findAllByPayDateBetween(fromDate, toDate);
    }
}
