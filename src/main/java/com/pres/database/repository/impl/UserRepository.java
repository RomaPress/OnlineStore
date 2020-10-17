package com.pres.database.repository.impl;

import com.pres.database.dao.impl.UserDAO;
import com.pres.database.repository.Repository;
import com.pres.exeption.DBException;
import com.pres.constants.ErrorMessage;
import com.pres.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository {
    private static final Logger LOG = Logger.getLogger(UserRepository.class);
    private static UserRepository userRepository;

    private UserRepository() {
    }

    public static synchronized UserRepository getInstance() {
        if (userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }

    public List<User> findAllUser() throws DBException {
        List<User> users;
        try (Connection connection = getConnection()) {
            users = new UserDAO().select(connection);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_USERS, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_USERS, e);
        }
        return users;
    }


    public boolean updateUserRole(int id, String newRole) throws DBException {
        try (Connection connection = getConnection()) {
            return new UserDAO().updateRole(connection, id, newRole);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_UPDATE_USER_ROLE, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_UPDATE_USER_ROLE, e);
        }
    }

    public boolean updateUserInfo(User user, int id) throws DBException {
        try (Connection connection = getConnection()) {
            return new UserDAO().update(connection, user, id);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_UPDATE_USER, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_UPDATE_USER, e);
        }
    }

    public boolean isUserAuthorized(final String login, final String password) throws DBException {
        try (Connection connection = getConnection()) {
            return new UserDAO().isAuthorized(connection, login, password);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_CHECK_IF_USER_AUTHORIZED, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_CHECK_IF_USER_AUTHORIZED, e);
        }
    }

    public boolean isLoginExist(final String login) throws DBException {
        try (Connection connection = getConnection()) {
            return new UserDAO().isLoginExist(connection, login);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_CHECK_IF_LOIN_EXIST, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_CHECK_IF_LOIN_EXIST, e);
        }
    }

    public boolean createUser(User user) throws DBException {
        try (Connection connection = getConnection()) {
            new UserDAO().insert(connection, user);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_CREATE_USER, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_CREATE_USER, e);
        }
        return true;
    }

    public User getUserByLogin(String login) throws DBException {
        User user;
        try (Connection connection = getConnection()) {
            user = new UserDAO().selectByLogin(connection, login);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, e);
        }
        return user;
    }
}
