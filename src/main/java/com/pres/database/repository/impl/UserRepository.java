package com.pres.database.repository.impl;

import com.pres.constants.ErrorMessage;
import com.pres.database.dao.impl.UserDAO;
import com.pres.database.repository.Repository;
import com.pres.exception.DBException;
import com.pres.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * This class allows an UserRepository object to have
 * business level communication.
 *
 * @author Pres Roman
 * @see User
 * @see UserDAO
 */
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

    /**
     * This method uses a DAO for selecting all user.
     *
     * @return list of all user.
     * @throws DBException if any problem occurs with connection.
     */
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

    /**
     * This method uses a DAO for updating user status.
     *
     * @param id      identifies an user which status role to be changed.
     * @param newRole new role.
     * @return true if success; else false.
     * @throws DBException if any problem occurs with connection.
     */
    public boolean updateUserRole(int id, String newRole) throws DBException {
        try (Connection connection = getConnection()) {
            return new UserDAO().updateRole(connection, id, newRole);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_UPDATE_USER_ROLE, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_UPDATE_USER_ROLE, e);
        }
    }

    /**
     * This method uses a DAO for updating user.
     *
     * @param user new user
     * @param id   id of object that is being updated.
     * @return true if success; else false.
     * @throws DBException if any problem occurs with connection.
     */
    public boolean updateUserInfo(User user, int id) throws DBException {
        try (Connection connection = getConnection()) {
            return new UserDAO().update(connection, user, id);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_UPDATE_USER, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_UPDATE_USER, e);
        }
    }

    /**
     * This method uses a DAO for checking if user exists.
     *
     * @param login    entered login to authentication.
     * @param password entered password to authentication.
     * @return true if authenticated ; else false.
     * @throws DBException if any problem occurs with connection.
     */
    public boolean isUserAuthorized(final String login, final String password) throws DBException {
        try (Connection connection = getConnection()) {
            return new UserDAO().isAuthentication(connection, login, password);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_CHECK_IF_USER_AUTHORIZED, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_CHECK_IF_USER_AUTHORIZED, e);
        }
    }

    /**
     * This method uses a DAO for checking if this login exists.
     *
     * @param login entered login to be checked.
     * @return true if existed ; else false.
     * @throws DBException if any problem occurs with connection.
     */
    public boolean isLoginExist(final String login) throws DBException {
        try (Connection connection = getConnection()) {
            return new UserDAO().isLoginExist(connection, login);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_CHECK_IF_LOIN_EXIST, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_CHECK_IF_LOIN_EXIST, e);
        }
    }

    /**
     * This method uses a DAO for creating user.
     *
     * @param user User object that must be created.
     * @return created user.
     * @throws DBException if any problem occurs with connection.
     */
    public boolean createUser(User user) throws DBException {
        try (Connection connection = getConnection()) {
            new UserDAO().insert(connection, user);
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_CREATE_USER, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_CREATE_USER, e);
        }
        return true;
    }

    /**
     * This method uses a DAO for selecting user by login.
     *
     * @param login identifies an user which to be returned.
     * @return user by this login.
     * @throws DBException if any problem occurs with connection.
     */
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
