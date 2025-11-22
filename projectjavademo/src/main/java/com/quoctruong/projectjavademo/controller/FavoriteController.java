package com.quoctruong.projectjavademo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quoctruong.projectjavademo.model.Product;
import com.quoctruong.projectjavademo.service.FavoriteService;

@Controller
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * Display page with all favorited products
     */
    @GetMapping("/favorites")
    public String viewFavorites(Model model) {
        // Get user ID from session (using 'guest' if not logged in)
        String userId = "guest";
        
        List<Product> favorites = favoriteService.getFavorites(userId);
        int favoriteCount = favoriteService.getFavoritesCount(userId);
        
        model.addAttribute("products", favorites);
        model.addAttribute("totalFavorites", favoriteCount);
        model.addAttribute("pageTitle", "Sản Phẩm Đã Thích");
        
        return "favorites";
    }

    /**
     * Add a product to favorites via AJAX
     */
    @PostMapping("/favorites/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addFavorite(@RequestParam Long productId) {
        String userId = "guest";
        
        boolean success = favoriteService.addFavorite(userId, productId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Added to favorites" : "Already in favorites");
        response.put("isFavorited", true);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Remove a product from favorites via AJAX
     */
    @PostMapping("/favorites/remove")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeFavorite(@RequestParam Long productId) {
        String userId = "guest";
        
        boolean success = favoriteService.removeFavorite(userId, productId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Removed from favorites" : "Not in favorites");
        response.put("isFavorited", false);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Check if a product is favorited via AJAX
     */
    @GetMapping("/favorites/check")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkFavorite(@RequestParam Long productId) {
        String userId = "guest";
        
        boolean isFavorited = favoriteService.isFavorited(userId, productId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("productId", productId);
        response.put("isFavorited", isFavorited);
        
        return ResponseEntity.ok(response);
    }
}
