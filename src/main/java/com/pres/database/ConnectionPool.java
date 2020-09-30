package com.pres.database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance;

    private ConnectionPool() {
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    public Connection getConnection() throws SQLException, NamingException {
        Context context = new InitialContext();
        DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/onlinestore");
        return ds.getConnection();
    }
}
