package com.quoctruong.projectjavademo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.quoctruong.projectjavademo.model.Order;
import com.quoctruong.projectjavademo.model.OrderItem;

@Service
public class OrderService {
    private final JdbcTemplate jdbc;

    public OrderService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Save an order with its items. Returns the order with generated ID.
     */
    public Order saveOrder(Order order) {
        if (order.getItems().isEmpty()) {
            return order;
        }

        // Insert order
        String orderSql = "INSERT INTO orders (user_id, order_date, total_price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(conn -> {
            var ps = conn.prepareStatement(orderSql, new String[]{"id"});
            ps.setString(1, order.getUserId());
            ps.setObject(2, order.getOrderDate());
            ps.setDouble(3, order.getTotalPrice());
            return ps;
        }, keyHolder);

        Long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        // Insert order items
        String itemSql = "INSERT INTO order_items (order_id, product_id, product_name, unit_price, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        for (OrderItem item : order.getItems()) {
            item.setOrderId(orderId);
            jdbc.update(itemSql, orderId, item.getProductId(), item.getProductName(),
                    item.getUnitPrice(), item.getQuantity(), item.getSubtotal());
        }

        return order;
    }

    /**
     * Get all orders for a user (or 'guest' if no user).
     */
    public List<Order> getOrdersByUserId(String userId) {
        String sql = "SELECT id, user_id, order_date, total_price FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        List<Order> orders = new ArrayList<>();

        RowMapper<Order> orderMapper = (rs, rowNum) -> {
            Order o = new Order();
            o.setId(rs.getLong("id"));
            o.setUserId(rs.getString("user_id"));
            o.setOrderDate(rs.getObject("order_date", LocalDateTime.class));
            o.setTotalPrice(rs.getDouble("total_price"));
            return o;
        };

        orders = jdbc.query(sql, orderMapper, userId);

        // Fetch items for each order
        String itemSql = "SELECT id, order_id, product_id, product_name, unit_price, quantity, subtotal FROM order_items WHERE order_id = ?";
        RowMapper<OrderItem> itemMapper = (rs, rowNum) -> {
            OrderItem i = new OrderItem();
            i.setId(rs.getLong("id"));
            i.setOrderId(rs.getLong("order_id"));
            i.setProductId(rs.getLong("product_id"));
            i.setProductName(rs.getString("product_name"));
            i.setUnitPrice(rs.getDouble("unit_price"));
            i.setQuantity(rs.getInt("quantity"));
            i.setSubtotal(rs.getDouble("subtotal"));
            return i;
        };

        for (Order order : orders) {
            List<OrderItem> items = jdbc.query(itemSql, itemMapper, order.getId());
            order.setItems(items);
        }

        return orders;
    }
}
