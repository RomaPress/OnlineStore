package com.pres.database.repository;

import com.pres.constants.ErrorMessage;
import com.pres.exeption.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Process {
    private static final Logger LOG = Logger.getLogger(Process.class);

    public static void tryRollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOG.error(ErrorMessage.ERR_CANNOT_ROLLBACK_TRANSACTION, e);
            }
        }
    }

    public static void tryClose(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(ErrorMessage.ERR_CANNOT_CLOSE_CONNECTION, e);
            }
        }
    }

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

    public static void commit(Connection connection) throws DBException {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_EXECUTE_COMMIT, e);
            throw new DBException(ErrorMessage.ERR_CANNOT_EXECUTE_COMMIT, e);
        }
    }
}
