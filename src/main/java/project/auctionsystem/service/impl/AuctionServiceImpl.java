package project.auctionsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.auctionsystem.entity.Auction;
import project.auctionsystem.exception.AuctionExpiredException;
import project.auctionsystem.exception.AuctionNotFoundException;
import project.auctionsystem.exception.InvalidEndDateProvidedException;
import project.auctionsystem.exception.InvalidPriceProvidedException;
import project.auctionsystem.repository.AuctionRepository;
import project.auctionsystem.service.AuctionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;

    @Override
    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction get(UUID id) throws AuctionNotFoundException {
        return auctionRepository.findById(id).orElseThrow(() -> new AuctionNotFoundException(id));
    }

    @Override
    public Auction create(Auction auction) throws InvalidEndDateProvidedException {
        if (auction.getEndDate().isBefore(LocalDateTime.now().plusDays(1))) {
            throw new InvalidEndDateProvidedException(auction.getEndDate());
        }
        return auctionRepository.save(auction);
    }

    @Override
    public Auction updatePrice(UUID id, Double price) throws AuctionNotFoundException, AuctionExpiredException, InvalidPriceProvidedException {
        Auction auctionDB = get(id);
        if (auctionDB.getPrice() > price) throw new InvalidPriceProvidedException(price);
        if (auctionDB.getEndDate().isBefore(LocalDateTime.now())) throw new AuctionExpiredException(id);
        auctionDB.setPrice(price);
        return auctionRepository.save(auctionDB);
    }
}
