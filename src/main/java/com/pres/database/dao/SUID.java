package com.pres.database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementing this interface allows an object to have the SUID methods
 * Select Update Insert Delete
 *
 * @author Pres Roman
 *
 * @param <M> - the type of objects that may be used SUID methods
 */
public interface SUID<M> {

    /**
     * Selects all objects from table
     * @param connection - connection to DB
     * @return list of requested objects
     * @throws SQLException if something went wrong on DB level
     */
    List<M> select(Connection connection) throws SQLException;

    /**
     * Updated an existing object in the table
     * @param connection - connection to DB
     * @param id - id of object that is being updated
     * @param m - object that is being updated
     * @return true if object was successfully updated, false if object update was unsuccessful
     * @throws SQLException if something went wrong on DB level
     */
    boolean update(Connection connection, M m, int id) throws SQLException;

    /**
     * Inserts a new object in the table
     * @param connection - connection to DB
     * @param m - object that is being updated
     * @return inserted object with updated id
     * @throws SQLException if something went wrong on DB level
     */
    M insert(Connection connection, M m) throws SQLException;

    /**
     *  Deletes an object from the table
     * @param connection - connection to DB
     * @param m - object that is being deleted
     * @return true if object was successfully deleted, false if object deleted was unsuccessful
     * @throws SQLException if something went wrong on DB level
     */
    boolean delete(Connection connection, M m) throws SQLException;
}
