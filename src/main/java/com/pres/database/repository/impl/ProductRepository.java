package com.pres.database.repository.impl;

import com.pres.database.dao.impl.ProductDAO;
import com.pres.database.repository.Repository;
import com.pres.model.Product;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
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

    public List<Product> findAllProduct(){
        List<Product> products = null;
        try(Connection connection = getConnection()){
            products = new ProductDAO().select(connection);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }


    public Product findProductByIdWithCurrentAmount(int id, int amount) {
        Product product = null;
        try (Connection connection = getConnection()){
            product = new ProductDAO().selectByIdWithCurrentAmount(connection, id, amount);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }
}
