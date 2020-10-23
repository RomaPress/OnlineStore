package com.pres.database.dao.impl;

import com.pres.constants.ConstantSQL;
import com.pres.database.dao.SUID;
import com.pres.model.Product;
import com.pres.model.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class allows an ProductDAO object to have low-level
 * Data Base communication. It realizes manipulations wits Product.
 *
 * @see Product
 *
 * @author Pres Roman
 */
public class ProductDAO implements SUID<Product> {

    /**
     * @param connection - connection to DB
     * @return list of all products
     * @throws SQLException if something went wrong on DB level
     */
    @Override
    public List<Product> select(Connection connection) throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(ConstantSQL.SQL_FIND_ALL_PRODUCT)) {
            while (rs.next()) {
                Product product = new Product.Builder()
                        .setId(rs.getInt(ConstantSQL.ID))
                        .setName(rs.getString(ConstantSQL.NAME))
                        .setPrice(rs.getDouble(ConstantSQL.PRICE))
                        .setAmount(rs.getInt(ConstantSQL.AMOUNT))
                        .setDescription(rs.getString(ConstantSQL.DESCRIPTION))
                        .setType(new Type.Builder()
                                .setId(rs.getInt(ConstantSQL.TYPE_ID))
                                .setName(rs.getString(ConstantSQL.TYPE_NAME))
                                .build())
                        .build();
                products.add(product);
            }
        }
        return products;
    }

    /**
     * @param connection - connection to DB
     * @param product new product
     * @param id - id of object that is being updated
     * @return true if success; else false
     * @throws SQLException if something went wrong on DB level
     */
    @Override
    public boolean update(Connection connection, Product product, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_UPDATE_PRODUCT)) {
            statement.setInt(1, product.getAmount());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3,id);
            if (1 == statement.executeUpdate()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param connection - connection to DB
     * @param product Product object that must be inserted into DB
     * @return created product
     * @throws SQLException if something went wrong on DB level
     */
    @Override
    public Product insert(Connection connection, Product product) throws SQLException {
        Product newProduct = new Product.Builder().build();
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_INSERT_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getAmount());
            statement.setString(4, product.getDescription());
            statement.setString(5, product.getType().getName());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                int key = rs.next() ? rs.getInt(1) : 0;
                if (key != 0) {
                    newProduct = new Product.Builder()
                            .setId(key)
                            .setName(product.getName())
                            .setPrice(product.getPrice())
                            .setAmount(product.getAmount())
                            .setDescription(product.getDescription())
                            .setType(product.getType())
                            .build();
                }
            }
        }
        return newProduct;
    }

    /**
     * @param connection - connection to DB
     * @param product product that must be deleted
     * @return true if success; else false
     * @throws SQLException if something went wrong on DB level
     */
    @Override
    public boolean delete(Connection connection, Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_DELETE_PRODUCT)) {
            statement.setInt(1, product.getId());
            if (1 == statement.executeUpdate()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method selects product by id from DB.
     *
     * @param connection - connection to DB
     * @param id identifies product
     * @return product with this id
     * @throws SQLException if something went wrong on DB level
     */
    public Product selectById(Connection connection, int id) throws SQLException {
        Product product;
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_FIND_PRODUCT_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                product = new Product.Builder()
                        .setId(id)
                        .setName(rs.getString(ConstantSQL.NAME))
                        .setPrice(rs.getDouble(ConstantSQL.PRICE))
                        .setAmount(rs.getInt(ConstantSQL.AMOUNT))
                        .setDescription(rs.getString(ConstantSQL.DESCRIPTION))
                        .setType(new Type.Builder()
                                .setId(rs.getInt(ConstantSQL.TYPE_ID))
                                .setName(rs.getString(ConstantSQL.TYPE_NAME))
                                .build())
                        .build();
            }
        }
        return product;
    }

    /**
     * This method selects product by id from DB and sets amount
     * to amount that the user wants to buy.
     *
     * @param connection - connection to DB
     * @param id identifies product
     * @param amount new product amount
     * @return product that have new amount
     * @throws SQLException if something went wrong on DB level
     */
    public Product selectByIdWithCurrentAmount(Connection connection, int id, int amount) throws SQLException {
        Product product;
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_FIND_PRODUCT_BY_ID_WITH_CURRENT_AMOUNT)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                product = new Product.Builder()
                        .setId(id)
                        .setName(rs.getString(ConstantSQL.NAME))
                        .setPrice(rs.getDouble(ConstantSQL.PRICE))
                        .setAmount(amount)
                        .setDescription(rs.getString(ConstantSQL.DESCRIPTION))
                        .setType(new Type.Builder()
                                .setId(rs.getInt(ConstantSQL.TYPE_ID))
                                .setName(rs.getString(ConstantSQL.TYPE_NAME))
                                .build())
                        .build();
            }
        }
        return product;
    }
}
