package com.ll.exam.finalPrj_2Week.app.mybook.repository;

import com.ll.exam.finalPrj_2Week.app.mybook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {

    List<MyBook> findAllByMemberId(Long id);

    MyBook findByMemberIdAndProductId(Long id, Long id1);
}
