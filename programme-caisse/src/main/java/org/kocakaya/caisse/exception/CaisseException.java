package org.kocakaya.caisse.exception;

public class CaisseException extends Exception {

    private static final long serialVersionUID = 1L;

    public CaisseException() {
	super();
    }

    public CaisseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public CaisseException(String message, Throwable cause) {
	super(message, cause);
    }

    public CaisseException(String message) {
	super(message);
    }

    public CaisseException(Throwable cause) {
	super(cause);
    }
}
