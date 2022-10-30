package project.auctionsystem.exception;

public class AccountNotFoundException extends Exception {

    private static final String REASON = "Account not found: %s";

    public AccountNotFoundException(String message) {
        super(String.format(REASON, message));
    }
}
