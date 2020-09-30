package com.pres.database.repository.impl;

import com.pres.constants.ConstantDB;
import com.pres.database.repository.Repository;
import com.pres.model.Order;
import com.pres.model.Product;
import com.pres.model.User;

import javax.naming.NamingException;
import java.sql.*;
import java.util.*;

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

    public boolean addProductInOrder(Product product, Order order) {
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
                addProductInOrder(i.getValue(), order);
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

    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(ConstantDB.SQL_FIND_ALL_ORDER)) {
            int id = 0;
            Order order = null;
            List<Product> products = null;
            while (rs.next()) {
                id = rs.getInt(ConstantDB.ID);

                //если заказа с currentId еще не было
                if (!set.contains(id)) {
                    set.add(id);

                    //если заказ был создан и больше его товаров нет --> добавляем его в список
                    if (order != null) {
                        order.setProducts(products);
                        orders.add(order);
                    }

                    //инициализируем ресурсы, для следующего заказа
                    products = new ArrayList<>();
                    order = new Order();

                    //добавляю инф заказа
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
                //добавляю информацию о продукте
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
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
