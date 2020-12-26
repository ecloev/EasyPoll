package Exceptions;

public class PollNotFoundException extends Exception {

    public PollNotFoundException() {
        super();
    }

    public PollNotFoundException(String message) {
        super(message);
    }
}
