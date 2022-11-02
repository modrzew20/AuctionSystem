package project.auctionsystem.exception;

import java.time.LocalDateTime;

public class InvalidEndDateProvidedException extends Exception {

    private static final String REASON = "Invalid end date provided: %s";

    public InvalidEndDateProvidedException(LocalDateTime message) {
        super(String.format(REASON, message));
    }
}
