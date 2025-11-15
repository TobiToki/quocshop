package com.quoctruong.projectjavademo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quoctruong.projectjavademo.service.UserService;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String username, @RequestParam String password, Model model) {
        // simple validation
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "Vui lòng nhập tên người dùng và mật khẩu");
            return "register";
        }

        try {
            if (userService.existsByUsername(username)) {
                model.addAttribute("error", "Tên người dùng đã tồn tại. Vui lòng chọn tên khác.");
                return "register";
            }

            int rows = userService.createUser(username.trim(), password.trim());
            if (rows <= 0) {
                model.addAttribute("error", "Không thể tạo tài khoản. Vui lòng thử lại sau.");
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            return "register";
        }

        model.addAttribute("success", "Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.");
        return "login";
    }
}
