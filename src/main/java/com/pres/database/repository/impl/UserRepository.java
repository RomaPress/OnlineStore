package com.pres.database.repository.impl;

import com.pres.database.dao.impl.UserDAO;
import com.pres.database.repository.Repository;
import com.pres.model.User;

import javax.naming.NamingException;
import java.sql.Connection;
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

    public boolean updateUserInfo(User user, int id){
        try (Connection connection = getConnection()){
            return new UserDAO().update(connection, user, id);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean isUserAuthorized(final String login, final String password) {
        try (Connection connection = getConnection()) {
            return new UserDAO().isAuthorized(connection, login, password);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean createUser(User user) {
        try (Connection connection = getConnection()) {
            new UserDAO().insert(connection, user);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public User getUserByLogin(String login) {
        User user = null;
        try (Connection connection = getConnection()) {
            user = new UserDAO().selectByLogin(connection, login);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
