package com.pres.database.dao.impl;

import com.pres.constants.ConstantDB;
import com.pres.database.dao.SUID;
import com.pres.model.Product;
import com.pres.model.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements SUID<Product> {
    @Override
    public List<Product> select(Connection connection) throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(ConstantDB.SQL_FIND_ALL_PRODUCT)) {
            while (rs.next()) {
                Product product = new Product.Builder()
                        .setId(rs.getInt(ConstantDB.ID))
                        .setName(rs.getString(ConstantDB.NAME))
                        .setPrice(rs.getDouble(ConstantDB.PRICE))
                        .setAmount(rs.getInt(ConstantDB.AMOUNT))
                        .setDescription(rs.getString(ConstantDB.DESCRIPTION))
                        .setType(new Type.Builder()
                                .setId(rs.getInt(ConstantDB.TYPE_ID))
                                .setName(rs.getString(ConstantDB.TYPE_NAME))
                                .build())
                        .build();
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public boolean update(Connection connection, Product product, int id) {
        return false;
    }

    @Override
    public Product insert(Connection connection, Product product) {
        return null;
    }

    @Override
    public boolean delete(Connection connection, Product product) {
        return false;
    }

    public Product selectByIdWithCurrentAmount(Connection connection, int id, int amount) throws SQLException {
        Product product = null;
        try (PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_FIND_PRODUCT_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                product = new Product.Builder()
                        .setId(id)
                        .setName(rs.getString(ConstantDB.NAME))
                        .setPrice(rs.getDouble(ConstantDB.PRICE))
                        .setAmount(amount)
                        .setDescription(rs.getString(ConstantDB.DESCRIPTION))
                        .setType(new Type.Builder()
                                .setId(rs.getInt(ConstantDB.TYPE_ID))
                                .setName(rs.getString(ConstantDB.TYPE_NAME))
                                .build())
                        .build();
            }
        }
        return product;
    }
}
