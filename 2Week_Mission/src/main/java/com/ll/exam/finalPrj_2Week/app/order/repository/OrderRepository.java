package com.ll.exam.finalPrj_2Week.app.order.repository;

import com.ll.exam.finalPrj_2Week.app.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
