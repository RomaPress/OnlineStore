package com.pres.database.dao.impl;

import com.pres.constants.ConstantDB;
import com.pres.database.dao.SUID;
import com.pres.model.Order;
import com.pres.model.Product;
import com.pres.model.User;

import java.sql.*;
import java.util.*;

public class OrderDAO implements SUID<Order> {

    @Override
    public List<Order> select(Connection connection) throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(ConstantDB.SQL_FIND_ALL_ORDER)) {
            orders = extractOrders(rs);
        }
        return orders;
    }

    @Override
    public boolean update(Connection connection, Order order, int id) {
        return false;
    }

    @Override
    public Order insert(Connection connection, Order order) throws SQLException {
        Order newOrder = new Order();
        try (PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getUser().getId());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                int key = rs.next() ? rs.getInt(1) : 0;
                if (key != 0) {
                    newOrder.setId(key);
                }
            }
        }
        return newOrder;
    }

    @Override
    public boolean delete(Connection connection, Order order) {
        return false;
    }

    public void insertProduct(Connection connection, Product product, Order order) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_INSERT_ORDER_PRODUCT)) {
            statement.setInt(1, order.getId());
            statement.setInt(2, product.getId());
            statement.setInt(3, product.getAmount());
            statement.execute();
        }
    }

    public Order selectById(Connection connection, int id) throws SQLException {
        Order order = null;
        try (PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_FIND_ORDER_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                order = extractOrders(rs).get(0);
            }
        }
        return order;
    }

    public void insertOrderInfo(Connection connection, Map<Integer, Product> products, User user) throws SQLException {
        Order order = new Order();
        order.setUser(user);

        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        order = insert(connection, order);
        for (Map.Entry<Integer, Product> i : products.entrySet()) {
            insertProduct(connection, i.getValue(), order);
        }
        connection.commit();

    }

    private List<Order> extractOrders(ResultSet rs) throws SQLException {
        List<Order> orders = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        int id = 0;
        Order order = null;
        List<Product> products = null;
        while (rs.next()) {
            id = rs.getInt(ConstantDB.ID);

            if (!set.contains(id)) {
                set.add(id);

                if (order != null) {
                    order.setProducts(products);
                    orders.add(order);
                }

                products = new ArrayList<>();
                order = new Order();

                order.setId(id);
                order.setDateTime(rs.getString(ConstantDB.DATE_TIME));
                order.setTotal(rs.getDouble(ConstantDB.TOTAL));
                order.setStatus(rs.getString(ConstantDB.STATUS));
                order.setUser(new User.Builder()
                        .setFirstName(rs.getString(ConstantDB.FIRST_NAME))
                        .setLastName(rs.getString(ConstantDB.LAST_NAME))
                        .setPhoneNumber(rs.getString(ConstantDB.PHONE_NUMBER))
                        .build());
            }
            products.add(new Product.Builder()
                    .setName(rs.getString(ConstantDB.PRODUCT))
                    .setAmount(rs.getInt(ConstantDB.AMOUNT))
                    .setPrice(rs.getDouble(ConstantDB.PRICE))
                    .build());
        }
        if (order != null) {
            order.setProducts(products);
            orders.add(order);
        }
        return orders;
    }
}
