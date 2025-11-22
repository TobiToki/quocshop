package com.quoctruong.projectjavademo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.quoctruong.projectjavademo.service.FavoriteService;

@Controller
public class HomeController {
    private final FavoriteService favoriteService;

    public HomeController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/")
    public String index(Model model) {
        String userId = "guest";
        int favoriteCount = favoriteService.getFavoritesCount(userId);
        model.addAttribute("favoriteCount", favoriteCount);
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        String userId = "guest";
        int favoriteCount = favoriteService.getFavoritesCount(userId);
        model.addAttribute("favoriteCount", favoriteCount);
        return "home";
    }
}
