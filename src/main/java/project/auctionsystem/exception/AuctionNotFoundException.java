package project.auctionsystem.exception;

import java.util.UUID;

public class AuctionNotFoundException extends Exception {

    private static final String REASON = "Auction not found: %s";

    public AuctionNotFoundException(UUID id) {
        super(String.format(REASON, id));
    }
}
