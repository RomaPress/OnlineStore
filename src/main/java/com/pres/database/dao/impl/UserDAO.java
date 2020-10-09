package com.pres.database.dao.impl;

import com.pres.constants.ConstantSQL;
import com.pres.database.dao.SUID;
import com.pres.model.User;

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
    public boolean update(Connection connection, User user, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_UPDATE_USER_INFO)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getCity());
            statement.setInt(5, user.getPostOffice());
            statement.setInt(6, id);
            if (1 == statement.executeUpdate()){
                return true;
            }
        }
        return false;
    }

    @Override
    public User insert(Connection connection, User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_INSERT_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getCity());
            statement.setInt(7, user.getPostOffice());
            statement.execute();
        }
        return user;
    }

    @Override
    public boolean delete(Connection connection, User user) {
        return false;
    }

    public User selectByLogin(Connection connection, String login) throws SQLException {
        User user;
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                user = extractUser(rs);
            }
        }
        return user;
    }

    public boolean isAuthorized(Connection connection, String login, String password) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_IF_USER_AUTHORIZATION)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        return new User.Builder()
                .setId(rs.getInt(ConstantSQL.ID))
                .setFirstName(rs.getString(ConstantSQL.FIRST_NAME))
                .setLastName(rs.getString(ConstantSQL.LAST_NAME))
                .setLogin(rs.getString(ConstantSQL.LOGIN))
                .setPassword(rs.getString(ConstantSQL.PASSWORD))
                .setPhoneNumber(rs.getString(ConstantSQL.PHONE_NUMBER))
                .setRole(rs.getString(ConstantSQL.NAME))
                .setCity(rs.getString(ConstantSQL.CITY))
                .setPostOffice(rs.getInt(ConstantSQL.POST_OFFICE))
                .build();
    }
}
