package com.pres.database.repository.impl;

import com.pres.database.dao.impl.ProductDAO;
import com.pres.database.repository.Process;
import com.pres.database.repository.Repository;
import com.pres.exception.DBException;
import com.pres.constants.ErrorMessage;
import com.pres.model.Product;
import com.pres.util.file.Photo;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
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

    public boolean updateProduct(Product product) throws DBException{
        boolean isTrue;
        try (Connection connection = getConnection()) {
            isTrue = new ProductDAO().update(connection, product, product.getId());
        } catch (SQLException | DBException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_UPDATE_PRODUCT, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_UPDATE_PRODUCT, e);
        }
        return isTrue;
    }

    public Product createProduct(Product product, Part part) throws DBException {
        Product newProduct;
        Connection connection = null;
        try {
            connection = getConnection();
            Process.setTransaction(connection);
            newProduct = new ProductDAO().insert(connection, product);
            Photo.saveImage(part, newProduct.getId());
            Process.commit(connection);
        } catch (SQLException | IOException e) {
            Process.tryRollback(connection);
            LOG.error(ErrorMessage.ERR_CANNOT_INSERT_PRODUCT, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_INSERT_PRODUCT, e);
        } finally {
            Process.tryClose(connection);
        }
        return newProduct;
    }

    public void deleteProduct(Product product) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            Process.setTransaction(connection);
            new ProductDAO().delete(connection, product);
            Photo.deleteImage(product.getId());
            Process.commit(connection);
        } catch (SQLException e) {
            Process.tryRollback(connection);
            LOG.error(ErrorMessage.ERR_CANNOT_DELETE_PRODUCT, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_DELETE_PRODUCT, e);
        } finally {
            Process.tryClose(connection);
        }
    }

    public List<Product> findAllProduct() throws DBException {
        List<Product> products;
        try (Connection connection = getConnection()) {
            products = new ProductDAO().select(connection);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCTS, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCTS, e);
        }
        return products;
    }

    public Product findProductById(int id) throws DBException {
        Product product;
        try (Connection connection = getConnection()) {
            product = new ProductDAO().selectById(connection, id);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCT_BY_ID, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCT_BY_ID, e);
        }
        return product;
    }

    public Product findProductByIdWithNewAmount(int id, int amount) throws DBException {
        Product product;
        try (Connection connection = getConnection()) {
            product = new ProductDAO().selectByIdWithCurrentAmount(connection, id, amount);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCT_BY_ID_WITH_NEW_AMOUNT, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_PRODUCT_BY_ID_WITH_NEW_AMOUNT, e);
        }
        return product;
    }
}
