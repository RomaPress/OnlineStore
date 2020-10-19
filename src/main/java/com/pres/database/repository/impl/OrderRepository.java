package com.pres.database.repository.impl;

import com.pres.database.dao.impl.OrderDAO;
import com.pres.database.repository.Process;
import com.pres.database.repository.Repository;
import com.pres.exception.DBException;
import com.pres.constants.ErrorMessage;
import com.pres.model.Order;
import com.pres.model.Product;
import com.pres.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * This class allows an OrderRepository object to have
 * business level communication
 *
 * @see Order
 * @see OrderDAO
 *
 * @author Pres Roman
 */
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

    /**
     * This method realizes transaction that saves all order details
     *
     * @param products - list of the unique products
     * @param user -  user that makes the order
     * @throws DBException if any problem occurs with connection
     */
    public void doOrder(Map<Integer, Product> products, User user) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            Process.setTransaction(connection);
            new OrderDAO().insertOrderInfo(connection, products,  user);
            Process.commit(connection);
        } catch (SQLException e) {
            Process.tryRollback(connection);
            LOG.error(ErrorMessage.ERR_CANNOT_DO_ORDER, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_DO_ORDER, e);
        } finally {
            Process.tryClose(connection);
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
}
