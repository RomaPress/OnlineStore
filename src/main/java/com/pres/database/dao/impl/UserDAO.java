package com.pres.database.dao.impl;

import com.pres.constants.ConstantSQL;
import com.pres.database.dao.SUID;
import com.pres.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class allows an UserDAO object to have low-level
 * Data Base communication. It realizes manipulations wits User
 *
 * @author Pres Roman
 *
 * @see User
 */
public class UserDAO implements SUID<User> {
    /**
     * @param connection - connection to DB
     * @return list of all user
     * @throws SQLException if something went wrong on DB level
     */
    @Override
    public List<User> select(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(ConstantSQL.SQL_FIND_ALL_USER)) {
            User user;
            while (rs.next()) {
                user = extractUser(rs);
                users.add(user);
            }
        }
        return users;
    }

    /**
     * @param connection - connection to DB
     * @param user       new product
     * @param id         - id of object that is being updated
     * @return true if success; else false
     * @throws SQLException if something went wrong on DB level
     */
    @Override
    public boolean update(Connection connection, User user, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_UPDATE_USER_INFO)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getCity());
            statement.setInt(5, user.getPostOffice());
            statement.setInt(6, id);
            if (1 == statement.executeUpdate()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param connection - connection to DB
     * @param user       User object that must be inserted into DB
     * @return created user
     * @throws SQLException if something went wrong on DB level
     */
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

    /**
     * This method changes user role in DB
     *
     * @param connection - connection to DB
     * @param id         identifies an user which status role to be changed
     * @param newRole    new role
     * @return true if success; else false
     * @throws SQLException if something went wrong on DB level
     */
    public boolean updateRole(Connection connection, int id, String newRole) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_UPDATE_USER_ROLE)) {
            statement.setString(1, newRole);
            statement.setInt(2, id);
            if (1 == statement.executeUpdate()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method selects user by this login from DB
     *
     * @param connection - connection to DB
     * @param login      identifies an user which to be returned
     * @return user by this login
     * @throws SQLException if something went wrong on DB level
     */
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

    /**
     * This method checks if user from DB is authenticated
     *
     * @param connection - connection to DB
     * @param login      entered login to authentication
     * @param password   entered password to authentication
     * @return true if authenticated ; else false
     * @throws SQLException if something went wrong on DB level
     */
    public boolean isAuthentication(Connection connection, String login, String password) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_IF_USER_AUTHORIZATION)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * This method checks if login exists in DB
     *
     * @param connection - connection to DB
     * @param login  entered login to be checked
     * @return true if existed ; else false
     * @throws SQLException if something went wrong on DB level
     */
    public boolean isLoginExist(Connection connection, String login) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ConstantSQL.SQL_IF_LOGIN_EXIST)) {
            statement.setString(1, login);
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
