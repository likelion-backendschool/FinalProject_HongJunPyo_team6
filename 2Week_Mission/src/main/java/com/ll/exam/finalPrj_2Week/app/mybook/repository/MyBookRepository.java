package com.ll.exam.finalPrj_2Week.app.mybook.repository;

import com.ll.exam.finalPrj_2Week.app.mybook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {
    void deleteByProductIdAndOwnerId(long productId, long ownerId);
}
