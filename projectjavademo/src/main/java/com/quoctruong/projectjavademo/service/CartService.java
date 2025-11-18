package com.quoctruong.projectjavademo.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {
    public static final String CART_SESSION_KEY = "CART";

    @SuppressWarnings("unchecked")
    public Map<Long, Integer> getCart(HttpSession session) {
        Object obj = session.getAttribute(CART_SESSION_KEY);
        if (obj instanceof Map) {
            return (Map<Long, Integer>) obj;
        }
        Map<Long, Integer> cart = new HashMap<>();
        session.setAttribute(CART_SESSION_KEY, cart);
        return cart;
    }

    public void addToCart(HttpSession session, Long productId, int quantity) {
        if (productId == null || quantity <= 0) return;
        Map<Long, Integer> cart = getCart(session);
        cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
        // session attribute already holds the map reference
    }

    public int getTotalQuantity(HttpSession session) {
        Map<Long, Integer> cart = getCart(session);
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Map<Long, Integer> getCartSnapshot(HttpSession session) {
        Map<Long, Integer> cart = getCart(session);
        return Collections.unmodifiableMap(new HashMap<>(cart));
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    public void removeFromCart(HttpSession session, Long productId) {
        if (productId == null) return;
        Map<Long, Integer> cart = getCart(session);
        cart.remove(productId);
    }

    public void setQuantity(HttpSession session, Long productId, int quantity) {
        if (productId == null) return;
        Map<Long, Integer> cart = getCart(session);
        if (quantity <= 0) {
            cart.remove(productId);
        } else {
            cart.put(productId, quantity);
        }
    }
}
