package com.pres.database;

import com.pres.exception.DBException;
import com.pres.constants.ErrorMessage;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private DataSource ds;

    private ConnectionPool() {
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    public Connection getConnection() throws DBException {
        Context context;
        try {
            if(ds == null){
                context = new InitialContext();
                ds = (DataSource) context.lookup("java:comp/env/jdbc/onlinestore");
            }
        } catch (NamingException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
        }
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_CONNECTION, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_CONNECTION, e);
        }
    }
}
