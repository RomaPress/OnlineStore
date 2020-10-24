package com.pres.exception;

/**
 * An exception that provides information on a database access error.
 *
 * @author Pres Roman
 *
 */
public class DBException extends Exception {
    public DBException(){super();}

    public DBException(String message){super(message);}

    public DBException(String message, Throwable cause){super(message, cause);}

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
