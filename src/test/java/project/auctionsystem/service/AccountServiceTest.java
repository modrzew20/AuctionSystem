package project.auctionsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.auctionsystem.dto.GetBalanceDto;
import project.auctionsystem.entity.AccessLevel;
import project.auctionsystem.entity.Account;
import project.auctionsystem.entity.Auction;
import project.auctionsystem.exception.AccessLevelNotFoundException;
import project.auctionsystem.exception.AccountNotFoundException;
import project.auctionsystem.exception.EtagException;
import project.auctionsystem.repository.AccessLevelRepository;
import project.auctionsystem.repository.AccountRepository;
import project.auctionsystem.repository.AuctionRepository;
import project.auctionsystem.service.impl.AccountServiceImpl;
import project.auctionsystem.utils.EtagGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class AccountServiceTest {


    @Mock
    AccountRepository accountRepository;

    @Mock
    AccessLevelRepository accessLevelRepository;

    @Mock
    AuctionRepository auctionRepository;

    @Mock
    EtagGenerator etagGenerator;

    @InjectMocks
    AccountServiceImpl accountService;

    private Account account;
    private AccessLevel accessLevel;
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";

    @BeforeEach
    void init() {
        accessLevel = new AccessLevel();
        accessLevel.setName(AccessLevel.CLIENT);

        account = new Account();
        account.setUsername(USERNAME);
        account.setPassword(PASSWORD);
        account.setAccessLevel(accessLevel);
    }

    @Test
    void getAllAccountsTest() {
        List<Account> list = new ArrayList<>();
        list.add(account);
        when(accountRepository.findAll()).thenReturn(list);

        List<Account> result = accountService.getAll();
        assertEquals(list.size(), result.size());
    }

    @Test
    void getAccountTest() throws AccountNotFoundException {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(Optional.of(account));

        Account result = accountService.get(USERNAME);
        assertEquals(account, result);
    }

    @Test
    void getAccountNotFoundException() {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.get(USERNAME));
    }

    @Test
    void createAccountTest() throws AccessLevelNotFoundException {
        when(accessLevelRepository.findById(AccessLevel.CLIENT)).thenReturn(Optional.of(accessLevel));
        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.create(account);
        assertEquals(account, result);
    }

    @Test
    void createAccountAccessLevelNotFoundException() {
        when(accessLevelRepository.findById(AccessLevel.CLIENT)).thenReturn(Optional.empty());
        assertThrows(AccessLevelNotFoundException.class, () -> accountService.create(account));
    }

    @Test
    void getBalanceTest() {

        List<Auction> auctionList = new ArrayList<>();

        Random random = new Random();
        int randomInt = random.nextInt(30);

        for (int i = 0; i < randomInt; i++) {
            Auction auction = new Auction();
            auction.setPrice(100.0);
            auction.setWinner(account);
            auction.setEndDate(LocalDateTime.now().minusDays(1));
            auctionList.add(auction);
        }
        when(auctionRepository.findByWinner_Username(any(String.class))).thenReturn(auctionList);

        GetBalanceDto result = accountService.getBalance(USERNAME, "usd");
        assertEquals(randomInt * 100.0, result.getBalance());
    }

    @Test
    void updatePasswordTest() throws AccountNotFoundException, EtagException {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        doNothing().when(etagGenerator).verifyAndUpdateEtag(any(Account.class));

        Account result = accountService.updatePassword(USERNAME, PASSWORD);
        assertEquals(account, result);
    }


}
