package com.quoctruong.projectjavademo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.quoctruong.projectjavademo.service.CartService;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final CartService cartService;

    public GlobalControllerAdvice(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute("cartCount")
    public int cartCount(HttpSession session) {
        return cartService.getTotalQuantity(session);
    }
}
