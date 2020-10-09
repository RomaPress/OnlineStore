package com.pres.database.repository.impl;

import com.pres.database.dao.impl.ProductDAO;
import com.pres.database.repository.Repository;
import com.pres.exeption.DBException;
import com.pres.constants.ErrorMessage;
import com.pres.model.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductRepository implements Repository {
    private static final Logger LOG = Logger.getLogger(ProductRepository.class);
    private static ProductRepository productRepository;

    private ProductRepository() {
    }

    public static synchronized ProductRepository getInstance() {
        if (productRepository == null)
            productRepository = new ProductRepository();
        return productRepository;
    }

    public List<Product> findAllProduct() throws DBException {
        List<Product> products;
        try(Connection connection = getConnection()){
            products = new ProductDAO().select(connection);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCTS, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCTS, e);
        }
        return products;
    }


    public Product findProductByIdWithNewAmount(int id, int amount) throws DBException {
        Product product;
        try (Connection connection = getConnection()){
            product = new ProductDAO().selectByIdWithCurrentAmount(connection, id, amount);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCT_BY_ID_WITH_NEW_AMOUNT, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCT_BY_ID_WITH_NEW_AMOUNT, e);
        }
        return product;
    }
}
