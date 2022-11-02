package project.auctionsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.auctionsystem.entity.AccessLevel;
import project.auctionsystem.entity.Account;
import project.auctionsystem.entity.Auction;
import project.auctionsystem.exception.AccessLevelNotFoundException;
import project.auctionsystem.exception.AccountNotFoundException;
import project.auctionsystem.repository.AccessLevelRepository;
import project.auctionsystem.repository.AccountRepository;
import project.auctionsystem.repository.AuctionRepository;
import project.auctionsystem.service.AccountService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AuctionRepository auctionRepository;
    private final AccessLevelRepository accessLevelRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account get(String username) throws AccountNotFoundException {
        return accountRepository.findByUsername(username).orElseThrow(() -> new AccountNotFoundException(username));
    }

    @Override
    public Account create(Account account) throws AccessLevelNotFoundException {
        AccessLevel accessLevel = accessLevelRepository
                .findById(account.getAccessLevel().getName())
                .orElseThrow(() -> new AccessLevelNotFoundException(account.getAccessLevel().getName()));

        account.setAccessLevel(accessLevel);
        return accountRepository.save(account);
    }

    @Override
    public Double getBalance(String username, String currency) {
        List<Auction> auctionList = auctionRepository.findByWinnerUsernameAndEndDateBefore(username, LocalDateTime.now());
        double balance = 0.0;
        for (Auction auction : auctionList) {
            balance += auction.getPrice();
        }
        return balance;
    }
}
