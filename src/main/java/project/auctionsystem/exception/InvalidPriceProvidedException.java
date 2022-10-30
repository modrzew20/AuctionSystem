package project.auctionsystem.exception;

public class InvalidPriceProvidedException extends Exception {

    private static final String REASON = "Invalid price provided: %s";

    public InvalidPriceProvidedException(Double message) {
        super(String.format(REASON, message));
    }
}
