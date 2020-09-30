package com.pres.database.repository.impl;

import com.pres.constants.ConstantDB;
import com.pres.database.repository.Repository;
import com.pres.model.Order;
import com.pres.model.Product;
import com.pres.model.User;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class OrderRepository implements Repository {
    private static OrderRepository orderRepository;

    private OrderRepository() {
    }

    public static synchronized OrderRepository getInstance() {
        if (orderRepository == null)
            orderRepository = new OrderRepository();
        return orderRepository;
    }

    public Order createOrder(User user) {
        Order order = new Order();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                int key = rs.next() ? rs.getInt(1) : 0;
                if (key != 0) {
                    order.setId(key);
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return order;
    }

    public boolean createProductInOrder(Product product, Order order) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_INSERT_ORDER_PRODUCT)) {
            statement.setInt(1, order.getId());
            statement.setInt(2, product.getId());
            statement.setInt(3, product.getAmount());
            statement.execute();
            return true;
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean doOrder(Map<Integer, Product> products, User user) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            Order order = OrderRepository.getInstance().createOrder(user);
            for (Map.Entry<Integer, Product> i : products.entrySet()) {
                createProductInOrder(i.getValue(), order);
            }
            connection.commit();
        } catch (SQLException | NamingException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return true;
    }
}
