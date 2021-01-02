package Exceptions;

/**
 * PollNotFoundException.java
 *
 * Exception thrown when a poll is not found
 *
 */

public class PollNotFoundException extends Exception {

    public PollNotFoundException() {
        super();
    }

    public PollNotFoundException(String message) {
        super(message);
    }
}
