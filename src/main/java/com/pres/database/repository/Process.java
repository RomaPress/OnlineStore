package com.pres.database.repository;

import com.pres.constants.ErrorMessage;
import com.pres.exception.DBException;
import com.pres.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class realizes utils methods for interaction with DB.
 *
 * @author Pres Roman
 */
public class Process {
    private static final Logger LOG = Logger.getLogger(Process.class);

    /**
     * @param connection Connection
     */
    public static void tryRollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOG.error(ErrorMessage.ERR_CANNOT_ROLLBACK_TRANSACTION, e);
            }
        }
    }

    /**
     * @param connection Connection
     */
    public static void tryClose(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(ErrorMessage.ERR_CANNOT_CLOSE_CONNECTION, e);
            }
        }
    }

    /**
     * @param connection Connection
     * @throws DBException if something went wrong with setting transaction
     */
    public static void setTransaction(Connection connection) throws DBException {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            LOG.info("Set transaction!");
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_SET_TRANSACTION, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_SET_TRANSACTION, e);
        }
    }

    /**
     * @param connection Connection
     * @throws DBException if something went wrong with committing
     */
    public static void commit(Connection connection) throws DBException {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_EXECUTE_COMMIT, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_EXECUTE_COMMIT, e);
        }
    }
}
