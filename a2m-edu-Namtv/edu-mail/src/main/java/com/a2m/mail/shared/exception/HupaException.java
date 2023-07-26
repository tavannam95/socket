package com.a2m.mail.shared.exception;

import java.io.Serializable;

/**
 * An abstract superclass for exceptions that can be thrown by the Hupa system.
 *
 * @author echo
 */
@SuppressWarnings("serial")
public class HupaException extends Exception implements Serializable {

    private String causeClassname;

    protected HupaException() {
    }

    public HupaException(String message) {
        super(message);
    }

    public HupaException(Throwable cause) {
        super(cause.getMessage());
        this.causeClassname = cause.getClass().getName();
    }

    public HupaException(String message, Throwable cause) {
        super(message + " (" + cause.getMessage() + ")");
        this.causeClassname = cause.getClass().getName();
    }

    public String getCauseClassname() {
        return causeClassname;
    }

    @Override public String toString() {
        return super.toString() + (causeClassname != null ? " [cause: " + causeClassname + "]" : "");
    }

}
