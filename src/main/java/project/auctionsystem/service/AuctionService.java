package project.auctionsystem.service;

import project.auctionsystem.entity.Auction;
import project.auctionsystem.exception.*;

import java.util.List;
import java.util.UUID;

public interface AuctionService {

    List<Auction> getAll();

    Auction get(UUID id) throws AuctionNotFoundException;

    Auction create(String sellerUsername, Auction auction) throws InvalidEndDateProvidedException, AccountNotFoundException;

    Auction updatePrice(String username, UUID id, Double price) throws AuctionNotFoundException, AuctionExpiredException, InvalidPriceProvidedException, AccountNotFoundException;

}
