package com.ll.exam.finalPrj_3Week.app.order.repository;

import com.ll.exam.finalPrj_3Week.app.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByBuyerIdOrderByIdDesc(long buyerId);
}
