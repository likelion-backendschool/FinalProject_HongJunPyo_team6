package com.ll.exam.finalPrj_2Week.app.order.repository;

import com.ll.exam.finalPrj_2Week.app.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findAllByBuyerId(Long id);
}
