package com.pres.database.repository.impl;

import com.pres.database.dao.impl.OrderDAO;
import com.pres.database.repository.Repository;
import com.pres.model.Order;
import com.pres.model.Product;
import com.pres.model.User;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderRepository implements Repository {
    private static OrderRepository orderRepository;

    private OrderRepository() {
    }

    public static synchronized OrderRepository getInstance() {
        if (orderRepository == null)
            orderRepository = new OrderRepository();
        return orderRepository;
    }

    public void doOrder(Map<Integer, Product> products, User user) {
        Connection connection = null;
        try {
            connection = setTransaction();
            new OrderDAO().insertOrderInfo(connection, products,  user);
            connection.commit();
        } catch (SQLException | NamingException e) {
            tryRollback(connection);
            e.printStackTrace();
        } finally {
            tryClose(connection);
        }
    }

    public List<Order> findAllOrders(){
        List<Order> orders = null;
        try(Connection connection = getConnection()) {
            orders = new OrderDAO().select(connection);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    public List<Order> findOrderByUser(User user){
        List<Order> orders = null;
        try(Connection connection = getConnection()) {
            orders = new OrderDAO().selectByUser(connection, user);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    public Order findOrderById(int id){
        Order order = null;
        try(Connection connection = getConnection()) {
            order = new OrderDAO().selectById(connection, id);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return order;
    }

    public void deleteProductFromOrder(Order order, int productId){
        try(Connection connection = getConnection()){
            new OrderDAO().deleteProductFromOrder(connection, order, productId);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
    }

    private void tryRollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void tryClose(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private Connection setTransaction() throws SQLException, NamingException{
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return connection;
    }
}
