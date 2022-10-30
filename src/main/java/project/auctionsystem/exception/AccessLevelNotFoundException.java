package project.auctionsystem.exception;

public class AccessLevelNotFoundException extends Exception {

    private static final String REASON = "Access level not found: %s";

    public AccessLevelNotFoundException(String message) {
        super(String.format(REASON, message));
    }
}
