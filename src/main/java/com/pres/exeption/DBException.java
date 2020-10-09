package com.pres.exeption;


public class DBException extends Exception {
    public DBException(){super();}

    public DBException(String message){super(message);}

    public DBException(String message, Throwable cause){super(message, cause);}

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
