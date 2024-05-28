package com.FinalExam.FinalExam.controller;

import com.FinalExam.FinalExam.dto.OrderDTO;
import com.FinalExam.FinalExam.model.Customer;
import com.FinalExam.FinalExam.model.Orders;
import com.FinalExam.FinalExam.repository.CustomerRepository;
import com.FinalExam.FinalExam.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders() {
        return ordersRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/orders/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        Orders order = ordersRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));
        return convertToDTO(order);
    }

    @PostMapping("/orders")
    public OrderDTO newOrder(@RequestBody OrderDTO orderDTO) {
        Long customerId = orderDTO.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer not found with id: " + customerId));

        Orders newOrder = new Orders();
        newOrder.setOrderDate(orderDTO.getOrderDate());
        newOrder.setTotalPrice(orderDTO.getTotalPrice());
        newOrder.setCustomer(customer);

        Orders savedOrder = ordersRepository.save(newOrder);
        return convertToDTO(savedOrder);
    }

    @PutMapping("/orders/{id}")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id) {
        return ordersRepository.findById(id).map(order -> {
            order.setOrderDate(orderDTO.getOrderDate());
            order.setTotalPrice(orderDTO.getTotalPrice());

            Long customerId = orderDTO.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer not found with id: " + customerId));
            order.setCustomer(customer);

            Orders updatedOrder = ordersRepository.save(order);
            return convertToDTO(updatedOrder);
        }).orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));
    }

    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable Long id) {
        if (!ordersRepository.existsById(id)) {
            throw new NoSuchElementException("Order not found with id: " + id);
        }
        ordersRepository.deleteById(id);
        return "Order deleted successfully";
    }

    private OrderDTO convertToDTO(Orders order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalPrice(order.getTotalPrice());

        if (order.getCustomer() != null) {
            orderDTO.setCustomerId(order.getCustomer().getId());
        }

        return orderDTO;
    }
}
