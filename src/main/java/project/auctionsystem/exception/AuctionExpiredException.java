package project.auctionsystem.exception;

import java.util.UUID;

public class AuctionExpiredException extends Exception {

    private static final String REASON = "Auction ended: %s";

    public AuctionExpiredException(UUID message) {
        super(String.format(REASON, message));
    }
}
