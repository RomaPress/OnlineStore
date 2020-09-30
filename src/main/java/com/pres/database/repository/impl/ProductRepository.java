package com.pres.database.repository.impl;

import com.pres.constants.ConstantDB;
import com.pres.database.repository.Repository;
import com.pres.model.Product;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository {

    private static ProductRepository productRepository;

    private ProductRepository() {
    }

    public static synchronized ProductRepository getInstance() {
        if (productRepository == null)
            productRepository = new ProductRepository();
        return productRepository;
    }

    public List<Product> findAllProduct() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(ConstantDB.SQL_FIND_ALL_PRODUCT)) {
            while (rs.next()) {
                Product product = new Product.Builder()
                        .setId(rs.getInt(ConstantDB.ID))
                        .setName(rs.getString(ConstantDB.NAME))
                        .setPrice(rs.getDouble(ConstantDB.PRICE))
                        .setAmount(rs.getInt(ConstantDB.AMOUNT))
                        .setDescription(rs.getString(ConstantDB.DESCRIPTION))
                        .build();
                products.add(product);
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product findProductByIdWithOwnAmount(int id, int amount) {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_FIND_PRODUCT_BY_ID)) {
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
                        .build();
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return product;
    }
}
