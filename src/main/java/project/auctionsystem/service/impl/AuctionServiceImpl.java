package project.auctionsystem.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.auctionsystem.entity.Account;
import project.auctionsystem.entity.Auction;
import project.auctionsystem.exception.*;
import project.auctionsystem.repository.AccountRepository;
import project.auctionsystem.repository.AuctionRepository;
import project.auctionsystem.service.AuctionService;
import project.auctionsystem.utils.EtagGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AuctionServiceImpl implements AuctionService {

    private final AccountRepository accountRepository;
    private final AuctionRepository auctionRepository;
    private final EtagGenerator etagGenerator;

    @Getter
    @Value("${MIN_END_AUCTION}")
    private int minEndAuction;

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
        if (auction.getEndDate().isBefore(LocalDateTime.now().plusDays(getMinEndAuction()))) {
            throw new InvalidEndDateProvidedException(auction.getEndDate());
        }
        return auctionRepository.save(auction);
    }

    @Override
    public Auction updatePrice(String username, UUID id, Double price) throws AuctionNotFoundException, AuctionExpiredException, InvalidPriceProvidedException, EtagException, AccountNotFoundException {
        Auction auctionDB = get(id);
        if (auctionDB.getPrice() > price) throw new InvalidPriceProvidedException(price);
        if (auctionDB.getEndDate().isBefore(LocalDateTime.now())) throw new AuctionExpiredException(id);
        Account winner = accountRepository.findByUsername(username).orElseThrow(() -> new AccountNotFoundException(username));

        auctionDB.setWinner(winner);
        auctionDB.setPrice(price);

        etagGenerator.verifyAndUpdateEtag(auctionDB);
        return auctionRepository.save(auctionDB);
    }
}
