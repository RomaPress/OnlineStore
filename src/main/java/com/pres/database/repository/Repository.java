package com.pres.database.repository;

import com.pres.database.ConnectionPool;
import com.pres.exception.DBException;

import java.sql.Connection;

/**
 * Implementing this interface allows an object to have connection
 * from Connection Pool
 *
 * @see ConnectionPool
 *
 * @author Pres Roman
 */
public interface Repository {

    /**
     * @return Connection
     * @throws DBException if any problem occurs with connection
     */
    default Connection getConnection() throws DBException {
        return ConnectionPool.getInstance().getConnection();
    }
}
