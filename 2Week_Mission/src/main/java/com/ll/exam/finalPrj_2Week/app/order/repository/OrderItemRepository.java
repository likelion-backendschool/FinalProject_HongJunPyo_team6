package com.ll.exam.finalPrj_2Week.app.order.repository;

import com.ll.exam.finalPrj_2Week.app.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByPayDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
