package com.ll.exam.finalPrj_3Week.app.email.repository;

import com.ll.exam.finalPrj_3Week.app.email.entity.SendEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendEmailLogRepository extends JpaRepository<SendEmailLog, Long> {
}
