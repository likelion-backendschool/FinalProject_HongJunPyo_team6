package com.ll.exam.finalPrj_3Week.app.cash.repository;

import com.ll.exam.finalPrj_3Week.app.cash.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog, Long> {
}
