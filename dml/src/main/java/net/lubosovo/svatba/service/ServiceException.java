package net.lubosovo.svatba.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = -2007500366475005463L;

    public ServiceException() {
            super();
    }

    public ServiceException(final String message) {
            super(message);
    }

    public ServiceException(final String message, final Throwable cause) {
            super(message, cause);
    }

    public ServiceException(final Throwable cause) {
            super(cause);
    }
}
