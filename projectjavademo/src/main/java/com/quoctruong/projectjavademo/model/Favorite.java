package com.quoctruong.projectjavademo.model;

import java.time.LocalDateTime;

public class Favorite {
    private Long id;
    private String userId;
    private Long productId;
    private LocalDateTime createdAt;

    // Constructors
    public Favorite() {}

    public Favorite(String userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Favorite(Long id, String userId, Long productId, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
