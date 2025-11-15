package com.quoctruong.projectjavademo.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JdbcTemplate jdbc;

    public UserService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Check if a user with given username exists in users table.
     */
    public boolean existsByUsername(String username) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM users WHERE username = ?",
                Integer.class,
                username);
        return count != null && count > 0;
    }

    /**
     * Check if a user with given username AND password exists in users table.
     */
    public boolean existsByUsernameAndPassword(String username, String password) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?",
                Integer.class,
                username, password);
        return count != null && count > 0;
    }

    /**
     * Create a new user with the given username and password.
     * Returns number of rows affected (1 if created).
     */
    public int createUser(String username, String password) {
        return jdbc.update(
                "INSERT INTO users (username, password) VALUES (?, ?)",
                username, password);
    }
}
