package com.quoctruong.projectjavademo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quoctruong.projectjavademo.model.Order;
import com.quoctruong.projectjavademo.model.OrderItem;
import com.quoctruong.projectjavademo.model.Product;
import com.quoctruong.projectjavademo.service.CartService;
import com.quoctruong.projectjavademo.service.OrderService;
import com.quoctruong.projectjavademo.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final OrderService orderService;

    public CartController(CartService cartService, ProductService productService, OrderService orderService) {
        this.cartService = cartService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @PostMapping("/cart/add")
    public String addToCart(HttpServletRequest request,
                            HttpSession session,
                            @RequestParam Long productId,
                            @RequestParam(required = false, defaultValue = "1") Integer quantity) {
        cartService.addToCart(session, productId, quantity != null ? quantity : 1);

        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isBlank()) {
            return "redirect:" + referer;
        }
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Map<Long, Integer> snapshot = cartService.getCartSnapshot(session);
        List<Product> items = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        double totalPrice = 0.0;

        for (Map.Entry<Long, Integer> e : snapshot.entrySet()) {
            Product p = productService.getProductById(e.getKey());
            if (p != null) {
                items.add(p);
                quantities.add(e.getValue());
                totalPrice += p.getPrice() * e.getValue();
            }
        }

        model.addAttribute("items", items);
        model.addAttribute("quantities", quantities);
        model.addAttribute("totalCount", cartService.getTotalQuantity(session));
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(HttpServletRequest request, HttpSession session, @RequestParam Long productId) {
        cartService.removeFromCart(session, productId);
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isBlank()) {
            return "redirect:" + referer;
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateQuantity(HttpServletRequest request, HttpSession session,
                                 @RequestParam Long productId,
                                 @RequestParam Integer quantity) {
        cartService.setQuantity(session, productId, quantity != null ? quantity : 1);
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isBlank()) {
            return "redirect:" + referer;
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(HttpSession session) {
        Map<Long, Integer> snapshot = cartService.getCartSnapshot(session);
        
        if (snapshot.isEmpty()) {
            return "redirect:/cart?checkout=success";
        }
        
        // Create Order with current timestamp
        Order order = new Order();
        order.setUserId("guest");
        order.setOrderDate(LocalDateTime.now());
        
        double totalPrice = 0.0;
        
        // Create OrderItems from cart snapshot
        for (Entry<Long, Integer> entry : snapshot.entrySet()) {
            Product product = productService.getProductById(entry.getKey());
            if (product != null) {
                OrderItem item = new OrderItem();
                item.setProductId(product.getId());
                item.setProductName(product.getName());
                item.setUnitPrice(product.getPrice());
                item.setQuantity(entry.getValue());
                item.setSubtotal(product.getPrice() * entry.getValue());
                
                order.addItem(item);
                totalPrice += item.getSubtotal();
            }
        }
        
        order.setTotalPrice(totalPrice);
        
        // Save order to database
        orderService.saveOrder(order);
        
        // Clear cart after successful save
        cartService.clearCart(session);
        return "redirect:/cart?checkout=success";
    }
}
