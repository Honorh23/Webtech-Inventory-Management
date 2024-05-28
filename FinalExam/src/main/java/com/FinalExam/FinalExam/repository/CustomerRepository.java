package com.FinalExam.FinalExam.repository;

import com.FinalExam.FinalExam.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
