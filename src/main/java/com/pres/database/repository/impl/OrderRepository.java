package com.pres.database.repository.impl;

import com.pres.database.dao.impl.OrderDAO;
import com.pres.database.repository.Repository;
import com.pres.exeption.DBException;
import com.pres.constants.ErrorMessage;
import com.pres.model.Order;
import com.pres.model.Product;
import com.pres.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderRepository implements Repository {
    private static final Logger LOG = Logger.getLogger(OrderRepository.class);
    private static OrderRepository orderRepository;

    private OrderRepository() {
    }

    public static synchronized OrderRepository getInstance() {
        if (orderRepository == null)
            orderRepository = new OrderRepository();
        return orderRepository;
    }

    public void doOrder(Map<Integer, Product> products, User user) throws DBException {
        Connection connection = null;
        try {
            connection = setTransaction();
            new OrderDAO().insertOrderInfo(connection, products,  user);
            connection.commit();
        } catch (SQLException e) {
            tryRollback(connection);
            LOG.error(ErrorMessage.ERR_CANNOT_DO_ORDER, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_DO_ORDER, e);
        } finally {
            tryClose(connection);
        }
    }

    public List<Order> findAllOrders() throws DBException {
        List<Order> orders;
        try(Connection connection = getConnection()) {
            orders = new OrderDAO().select(connection);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_ORDERS, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_ORDERS, e);
        }
        return orders;
    }

    public List<Order> findOrderByUser(User user) throws DBException {
        List<Order> orders;
        try(Connection connection = getConnection()) {
            orders = new OrderDAO().selectByUser(connection, user);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_USER_ORDERS, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_USER_ORDERS, e);
        }
        return orders;
    }

    public Order findOrderById(int id) throws DBException {
        Order order;
        try(Connection connection = getConnection()) {
            order = new OrderDAO().selectById(connection, id);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_ORDER_BY_ID, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_ORDER_BY_ID, e);
        }
        return order;
    }

    public List<String> selectAllStatus() throws DBException {
        List<String> orders;
        try(Connection connection = getConnection()){
            orders = new OrderDAO().selectStatus(connection);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_STATUS, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_STATUS, e);
        }
        return orders;
    }

    public void deleteProductFromOrder(Order order, int productId) throws DBException {
        try(Connection connection = getConnection()){
            new OrderDAO().deleteProductFromOrder(connection, order, productId);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_DELETE_PRODUCT_FROM_ORDER, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_DELETE_PRODUCT_FROM_ORDER, e);
        }
    }

    public boolean updateStatus(Order order) throws DBException {
        try(Connection connection = getConnection()){
            return new OrderDAO().updateStatus(connection, order);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_UPDATE_ORDER_STATUS, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_UPDATE_ORDER_STATUS, e);
        }
    }

    public boolean updateInvoiceNumber(Order order) throws DBException {
        try(Connection connection = getConnection()){
            return new OrderDAO().updateInvoiceNumber(connection, order);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_UPDATE_ORDER_INVOICE_NUMBER, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_UPDATE_ORDER_INVOICE_NUMBER, e);
        }
    }

    private void tryRollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOG.error(ErrorMessage.ERR_CANNOT_ROLLBACK_TRANSACTION, e);
            }
        }
    }

    private void tryClose(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(ErrorMessage.ERR_CANNOT_CLOSE_CONNECTION, e);
            }
        }
    }

    private Connection setTransaction() throws DBException {
        Connection connection;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            LOG.info("Set transaction!");
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_SET_TRANSACTION, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_SET_TRANSACTION, e);
        }
        return connection;
    }
}
