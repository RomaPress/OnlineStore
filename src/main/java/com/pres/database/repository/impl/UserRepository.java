package com.pres.database.repository.impl;

import com.pres.constants.ConstantDB;
import com.pres.database.repository.Repository;
import com.pres.model.User;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements Repository {

    private static UserRepository userRepository;

    private UserRepository() {
    }

    public static synchronized UserRepository getInstance() {
        if (userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }

    public User.Role getRoleByLoginAndPassword(final String login, final String password) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_FIND_ROLE_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                if (rs.getString(ConstantDB.NAME).equals(User.Role.ADMIN.value())) {
                    return User.Role.ADMIN;
                } else if (rs.getString(ConstantDB.NAME).equals(User.Role.USER.value())) {
                    return User.Role.USER;
                }
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return User.Role.UNKNOWN;
    }

    public boolean isUserAuthorization(final String login, final String password) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_IF_USER_AUTHORIZATION)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createUser(User user) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_INSERT_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhoneNumber());
            statement.execute();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByLogin(String login) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantDB.SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                user = new User.Builder()
                        .setId(rs.getInt(ConstantDB.ID))
                        .setFirstName(rs.getString(ConstantDB.FIRST_NAME))
                        .setLastName(rs.getString(ConstantDB.LAST_NAME))
                        .setLogin(rs.getString(ConstantDB.LOGIN))
                        .setPassword(rs.getString(ConstantDB.PASSWORD))
                        .setPhoneNumber(rs.getString(ConstantDB.PHONE_NUMBER))
                        .setRole(rs.getString(ConstantDB.NAME))
                        .build();
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return user;
    }
}
