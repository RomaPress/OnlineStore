package com.pres.database.repository;

import com.pres.database.ConnectionPool;
import com.pres.exception.DBException;

import java.sql.Connection;

public interface Repository {

    default Connection getConnection() throws DBException {
        return ConnectionPool.getInstance().getConnection();
    }
}
