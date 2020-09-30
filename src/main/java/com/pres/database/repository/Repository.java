package com.pres.database.repository;

import com.pres.database.ConnectionPool;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public interface Repository {

    default Connection getConnection() throws SQLException, NamingException {
        return ConnectionPool.getInstance().getConnection();
    }
}
