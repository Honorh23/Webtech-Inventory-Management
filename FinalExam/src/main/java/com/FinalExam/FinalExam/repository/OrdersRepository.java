package com.FinalExam.FinalExam.repository;

import com.FinalExam.FinalExam.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
