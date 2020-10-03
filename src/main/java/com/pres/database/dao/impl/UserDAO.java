package com.pres.database.dao.impl;

import com.pres.constants.ConstantDB;
import com.pres.database.dao.SUID;
import com.pres.model.User;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements SUID<User> {
    @Override
    public List<User> select(Connection connection) {
        return null;
    }

    @Override
    public boolean update(Connection connection, User user, int id) {
        return false;
    }

    @Override
    public User insert(Connection connection, User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_INSERT_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhoneNumber());
            statement.execute();
        }
        return user;
    }

    @Override
    public boolean delete(Connection connection, User user) {
        return false;
    }

    public User selectByLogin(Connection connection, String login) throws SQLException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                user = extractUser(rs);
            }
        }
        return user;
    }

    public boolean isAuthorized(Connection connection, String login, String password) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_IF_USER_AUTHORIZATION)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        return new User.Builder()
                .setId(rs.getInt(ConstantDB.ID))
                .setFirstName(rs.getString(ConstantDB.FIRST_NAME))
                .setLastName(rs.getString(ConstantDB.LAST_NAME))
                .setLogin(rs.getString(ConstantDB.LOGIN))
                .setPassword(rs.getString(ConstantDB.PASSWORD))
                .setPhoneNumber(rs.getString(ConstantDB.PHONE_NUMBER))
                .setRole(rs.getString(ConstantDB.NAME))
                .build();
    }
}
