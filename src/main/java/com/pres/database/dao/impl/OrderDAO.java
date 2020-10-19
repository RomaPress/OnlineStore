package com.pres.database.dao.impl;

import com.pres.constants.ConstantSQL;
import com.pres.database.dao.SUID;
import com.pres.model.Order;
import com.pres.model.Product;
import com.pres.model.User;

import java.sql.*;
import java.util.*;

/**
 * This class allows an OrderDAO object to have low-level
 * Data Base communication. It realizes manipulations wits Order
 *
 * @see Order
 *
 * @author Pres Roman
 */
public class OrderDAO implements SUID<Order> {

    /**
     * @param connection - connection to DB
     * @return list of all orders
     * @throws SQLException if something went wrong on DB level
     */
    @Override
    public List<Order> select(Connection connection) throws SQLException {
        List<Order> orders;
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(ConstantSQL.SQL_FIND_ALL_ORDER)) {
            orders = extractOrders(rs);
        }
        return orders;
    }

    @Override
    public boolean update(Connection connection, Order order, int id) {
        return false;
    }

    /**
     * @param connection - connection to DB
     * @param order Order object that must be inserted into DB
     * @return created order
     * @throws SQLException if something went wrong on DB level
     */
    @Override
    public Order insert(Connection connection, Order order) throws SQLException {
        Order newOrder = new Order();
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getUser().getId());
            statement.setString(2, order.getCity());
            statement.setInt(3, order.getPostOffice());
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

    /**
     * @param connection - connection to DB
     * @param order Order object that must be deleted from DB
     * @param productId identifies an order
     * @throws SQLException if something went wrong on DB level
     */
    public void deleteProductFromOrder(Connection connection, Order order, int productId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_DELETE_PRODUCT_FROM_ORDER)) {
            statement.setInt(1, productId);
            statement.setInt(2, order.getId());
            statement.execute();
        }
    }

    /**
     * This method adds product in existed order into DB
     *
     * @param connection - connection to DB
     * @param product Product object that must be inserted into order
     * @param order identifies an order
     * @throws SQLException if something went wrong on DB level
     */
    public void insertProduct(Connection connection, Product product, Order order) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_INSERT_ORDER_PRODUCT)) {
            statement.setInt(1, order.getId());
            statement.setInt(2, product.getId());
            statement.setInt(3, product.getAmount());
            statement.execute();
        }
    }

    /**
     * This method looks for orders that belong to the current user in DB
     *
     * @param connection - connection to DB
     * @param user identifies orders
     * @return list of the current user`s orders
     * @throws SQLException if something went wrong on DB level
     */
    public List<Order> selectByUser(Connection connection, User user) throws SQLException {
        List<Order> orders;
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_FIND_ORDER_BY_USER)) {
            statement.setInt(1, user.getId());
            try (ResultSet rs = statement.executeQuery()) {
                orders = extractOrders(rs);
            }
        }
        return orders;
    }

    /**
     * This method looks for order in DB that have current id.
     *
     * @param connection - connection to DB
     * @param id identifies orders
     * @return order with this id
     * @throws SQLException if something went wrong on DB level
     */
    public Order selectById(Connection connection, int id) throws SQLException {
        Order order;
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_FIND_ORDER_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                order = extractOrders(rs).get(0);
            }
        }
        return order;
    }

    /**
     * This method inserts all order details into DB.
     *
     * @param connection - connection to DB
     * @param products - list of the unique products
     * @param user - user that makes the order
     * @throws SQLException if something went wrong on DB level
     */
    public void insertOrderInfo(Connection connection, Map<Integer, Product> products, User user) throws SQLException {
        Order order = new Order();
        order.setUser(user);
        order.setCity(user.getCity());
        order.setPostOffice(user.getPostOffice());

        order = insert(connection, order);
        for (Map.Entry<Integer, Product> i : products.entrySet()) {
            insertProduct(connection, i.getValue(), order);
        }
    }

    /**
     * This method selects all unique order statuses from DB
     *
     * @param connection - connection to DB
     * @return list of all unique order statuses
     * @throws SQLException if something went wrong on DB level
     */
    public List<String> selectStatus(Connection connection) throws SQLException {
        List<String> statuses = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(ConstantSQL.SQL_SELECT_ALL_STATUS)) {
            while (rs.next()) {
                statuses.add(rs.getString(ConstantSQL.NAME));
            }
        }
        return statuses;
    }

    /**
     * This method changes order status in DB
     *
     * @param connection - connection to DB
     * @param order identifies an order which status needs to be changed
     * @return true if success; else false
     * @throws SQLException if something went wrong on DB level
     */
    public boolean updateStatus(Connection connection, Order order) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_UPDATE_ORDER_STATUS)) {
            statement.setString(1, order.getStatus().value());
            statement.setInt(2, order.getId());
            if (1 == statement.executeUpdate()){
                return true;
            }
        }
        return false;
    }

    /**
     * This method changes order invoice number in DB
     *
     * @param connection - connection to DB
     * @param order object which invoice number needs to be changed
     * @return true if success; else false
     * @throws SQLException if something went wrong on DB level
     */
    public boolean updateInvoiceNumber(Connection connection, Order order) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_UPDATE_ORDER_INVOICE_NUMBER)) {
            statement.setString(1, order.getInvoiceNumber());
            statement.setInt(2, order.getId());
            if (1 == statement.executeUpdate()){
                return true;
            }
        }
        return false;
    }

    private List<Order> extractOrders(ResultSet rs) throws SQLException {
        List<Order> orders = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        int id = 0;
        Order order = null;
        List<Product> products = null;
        while (rs.next()) {
            id = rs.getInt(ConstantSQL.ID);

            if (!set.contains(id)) {
                set.add(id);

                if (order != null) {
                    order.setProducts(products);
                    orders.add(order);
                }

                products = new ArrayList<>();
                order = new Order();

                order.setId(id);
                order.setDateTime(rs.getString(ConstantSQL.DATE_TIME));
                order.setTotal(rs.getDouble(ConstantSQL.TOTAL));
                order.setStatus(rs.getString(ConstantSQL.STATUS));
                order.setCity(rs.getString(ConstantSQL.CITY));
                order.setPostOffice(rs.getInt(ConstantSQL.POST_OFFICE));
                order.setInvoiceNumber(rs.getString(ConstantSQL.INVOICE_NUMBER));
                order.setUser(new User.Builder()
                        .setFirstName(rs.getString(ConstantSQL.FIRST_NAME))
                        .setLastName(rs.getString(ConstantSQL.LAST_NAME))
                        .setPhoneNumber(rs.getString(ConstantSQL.PHONE_NUMBER))
                        .build());
            }
            products.add(new Product.Builder()
                    .setId(rs.getInt(ConstantSQL.PRODUCT_ID))
                    .setName(rs.getString(ConstantSQL.PRODUCT))
                    .setAmount(rs.getInt(ConstantSQL.AMOUNT))
                    .setPrice(rs.getDouble(ConstantSQL.PRICE))
                    .build());
        }
        if (order != null) {
            order.setProducts(products);
            orders.add(order);
        }
        return orders;
    }
}
