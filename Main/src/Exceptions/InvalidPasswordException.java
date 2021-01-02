package Exceptions;

/**
 * InvalidPasswordException.java
 *
 * Exception thrown when the password is invalid
 *
 */

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}

