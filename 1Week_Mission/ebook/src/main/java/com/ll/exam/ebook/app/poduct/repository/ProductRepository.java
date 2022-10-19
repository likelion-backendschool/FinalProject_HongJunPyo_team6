package com.ll.exam.ebook.app.poduct.repository;

import com.ll.exam.ebook.app.poduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
