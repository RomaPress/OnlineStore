package com.pres.database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface SUID<M> {
    List<M> select(Connection connection) throws SQLException;

    boolean update(Connection connection, M m, int id) throws SQLException;

    M insert(Connection connection, M m) throws SQLException;

    boolean delete(Connection connection, M m) throws SQLException;
}
