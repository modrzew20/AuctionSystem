package project.auctionsystem.service;

import project.auctionsystem.entity.Auction;
import project.auctionsystem.exception.*;

import java.util.List;
import java.util.UUID;

public interface AuctionService {

    List<Auction> getAll();

    Auction get(UUID id) throws AuctionNotFoundException;

    Auction create(Auction auction) throws InvalidEndDateProvidedException;

    Auction updatePrice(UUID id, Double price) throws AuctionNotFoundException, AuctionExpiredException, InvalidPriceProvidedException, EtagException;


}
