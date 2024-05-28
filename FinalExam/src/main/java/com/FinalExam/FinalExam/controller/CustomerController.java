package com.FinalExam.FinalExam.controller;

import com.FinalExam.FinalExam.exception.CustomerNotFoundException;
import com.FinalExam.FinalExam.model.Customer;
import com.FinalExam.FinalExam.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/customer")
    Customer newCustomer(@RequestBody Customer newCustomer){
        return customerRepository.save(newCustomer);
    }

    @GetMapping("/customer")
    List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{id}")
    Customer getCustomerById(@PathVariable Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }


    @PutMapping("/customer/{id}")
    Customer updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id){
        return customerRepository.findById(id).map(customer -> {
            customer.setFirstname(newCustomer.getFirstname());
            customer.setLastname(newCustomer.getLastname());
            customer.setEmail(newCustomer.getEmail());
            customer.setPhone(newCustomer.getPhone());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @DeleteMapping("/customer/{id}")
    String deleteCustomer(@PathVariable Long id){
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }
}
