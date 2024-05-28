package com.FinalExam.FinalExam.repository;

import com.FinalExam.FinalExam.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
