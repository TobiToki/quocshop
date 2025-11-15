package com.quoctruong.projectjavademo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quoctruong.projectjavademo.service.UserService;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/login"})
    public String loginForm() {
        return "login";
    }

    @GetMapping({"/home"})
    public String home() {
        return "/home";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, Model model) {
        boolean exists = false;
        try {
            exists = userService.existsByUsernameAndPassword(username, password);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi kết nối: " + e.getMessage());
            return "login";
        }

        if (!exists) {
            model.addAttribute("error", "Tên hoặc mật khẩu không chính xác!");
            return "login";
        }

        model.addAttribute("username", username);
        model.addAttribute("password", password);
        return "redirect:/home";
    }
}
