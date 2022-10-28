package com.ll.exam.finalPrj_3Week.app.myBook.repository;

import com.ll.exam.finalPrj_3Week.app.myBook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {
    void deleteByProductIdAndOwnerId(long productId, long ownerId);
}
