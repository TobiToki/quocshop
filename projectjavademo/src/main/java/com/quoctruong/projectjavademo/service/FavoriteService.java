package com.quoctruong.projectjavademo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.quoctruong.projectjavademo.model.Favorite;
import com.quoctruong.projectjavademo.model.Product;

@Service
public class FavoriteService {
    private final JdbcTemplate jdbc;
    private final ProductService productService;

    private final RowMapper<Favorite> favoriteMapper = (rs, rowNum) -> {
        Favorite favorite = new Favorite();
        favorite.setId(rs.getLong("id"));
        favorite.setUserId(rs.getString("user_id"));
        favorite.setProductId(rs.getLong("product_id"));
        favorite.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return favorite;
    };

    public FavoriteService(JdbcTemplate jdbc, ProductService productService) {
        this.jdbc = jdbc;
        this.productService = productService;
    }

    /**
     * Add a product to favorites for the given user
     */
    public boolean addFavorite(String userId, Long productId) {
        try {
            String sql = "INSERT INTO favorites (user_id, product_id) VALUES (?, ?)";
            int rowsAffected = jdbc.update(sql, userId, productId);
            return rowsAffected > 0;
        } catch (Exception e) {
            // Handle duplicate entry (product already favorited)
            return false;
        }
    }

    /**
     * Remove a product from favorites for the given user
     */
    public boolean removeFavorite(String userId, Long productId) {
        String sql = "DELETE FROM favorites WHERE user_id = ? AND product_id = ?";
        int rowsAffected = jdbc.update(sql, userId, productId);
        return rowsAffected > 0;
    }

    /**
     * Check if a product is favorited by the user
     */
    public boolean isFavorited(String userId, Long productId) {
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND product_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId, productId);
        return count != null && count > 0;
    }

    /**
     * Get all favorited products for a user
     */
    public List<Product> getFavorites(String userId) {
        String sql = "SELECT product_id FROM favorites WHERE user_id = ? ORDER BY created_at DESC";
        
        List<Long> productIds = jdbc.queryForList(sql, Long.class, userId);

        List<Product> products = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productService.getProductById(productId);
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Get count of favorites for a user
     */
    public int getFavoritesCount(String userId) {
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId);
        return count != null ? count : 0;
    }
}
