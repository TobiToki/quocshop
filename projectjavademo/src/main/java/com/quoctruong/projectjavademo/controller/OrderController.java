package com.quoctruong.projectjavademo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.quoctruong.projectjavademo.model.Order;
import com.quoctruong.projectjavademo.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        // For demo: fetch orders for "guest" user
        List<Order> orders = orderService.getOrdersByUserId("guest");
        model.addAttribute("orders", orders);
        return "orders";
    }
}
