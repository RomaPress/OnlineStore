package com.pres.database.repository;

import com.pres.database.ConnectionPool;
import com.pres.exeption.DBException;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public interface Repository {

    default Connection getConnection() throws DBException {
        return ConnectionPool.getInstance().getConnection();
    }
}
