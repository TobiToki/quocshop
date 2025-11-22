package com.quoctruong.projectjavademo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quoctruong.projectjavademo.service.UserService;

@Controller
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String accountForm() {
        return "account";
    }

    @PostMapping("/account")
    public String updateAccount(@RequestParam String currentUsername,
                                @RequestParam String currentPassword,
                                @RequestParam String newUsername,
                                @RequestParam String newPassword,
                                @RequestParam(required = false) String confirmPassword,
                                Model model) {
        // Basic validation
        if (newUsername == null || newUsername.isBlank() || newPassword == null || newPassword.isBlank()) {
            model.addAttribute("error", "Tên đăng nhập và mật khẩu mới không được để trống.");
            return "account";
        }

        if (confirmPassword != null && !newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            return "account";
        }

        boolean ok = false;
        try {
            ok = userService.updateUsernameAndPassword(currentUsername, currentPassword, newUsername, newPassword);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi cập nhật: " + e.getMessage());
            return "account";
        }

        if (!ok) {
            model.addAttribute("error", "Thông tin hiện tại không đúng hoặc cập nhật thất bại.");
            return "account";
        }

        model.addAttribute("success", "Cập nhật tài khoản thành công. Vui lòng đăng nhập lại.");
        // After changing username/password, suggest re-login
        return "account";
    }
}
