package project.auctionsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.auctionsystem.entity.Account;
import project.auctionsystem.entity.Auction;
import project.auctionsystem.exception.*;
import project.auctionsystem.repository.AuctionRepository;
import project.auctionsystem.service.impl.AuctionServiceImpl;
import project.auctionsystem.utils.impl.EtagGeneratorImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class AuctionServiceTest {

    @Mock
    AuctionRepository auctionRepository;
    @Mock
    EtagGeneratorImpl etagGenerator;
    @InjectMocks
    AuctionServiceImpl auctionService;

    private final static String TITLE = "title";
    private final static Double PRICE = 100.0;
    private final static LocalDateTime END_DATE = LocalDateTime.now().plusDays(2);

    private Auction auction;

    @BeforeEach
    void init() {
        Account account = new Account();

        auction = new Auction();
        auction.setTitle(TITLE);
        auction.setWinner(account);
        auction.setPrice(PRICE);
        auction.setEndDate(END_DATE);
    }

    @Test
    void getAllAuctionTest() {
        List<Auction> list = new ArrayList<>();
        list.add(auction);
        when(auctionRepository.findAll()).thenReturn(list);

        List<Auction> result = auctionService.getAll();
        assertEquals(result.size(), list.size());
    }

    @Test
    void getAuctionTest() throws AuctionNotFoundException {
        when(auctionRepository.findById(auction.getId())).thenReturn(java.util.Optional.of(auction));

        Auction result = auctionService.get(auction.getId());
        assertEquals(result.getTitle(), auction.getTitle());
    }

    @Test
    void getAuctionThrowNotFoundExceptionTest() {
        when(auctionRepository.findById(auction.getId())).thenReturn(Optional.empty());
        assertThrows(AuctionNotFoundException.class, () -> auctionService.get(auction.getId()));
    }

    @Test
    void createAuctionTest() throws InvalidEndDateProvidedException {
        when(auctionRepository.save(auction)).thenReturn(auction);

        Auction result = auctionService.create(auction);
        assertEquals(result.getTitle(), auction.getTitle());
        verify(auctionRepository, times(1)).save(auction);
    }

    @Test
    void createAuctionInvalidEndDateProvidedExceptionText() {
        auction.setEndDate(LocalDateTime.now().minusDays(1));
        assertThrows(InvalidEndDateProvidedException.class, () -> auctionService.create(auction));
    }

    @Test
    void updatePriceTest() throws AuctionExpiredException, AuctionNotFoundException, InvalidPriceProvidedException, EtagException {
        when(auctionRepository.findById(auction.getId())).thenReturn(Optional.of(auction));
        when(auctionRepository.save(auction)).thenReturn(auction);
        doNothing().when(etagGenerator).verifyEtag(auction);

        Auction result = auctionService.updatePrice(auction.getId(), 200.0);
        assertEquals(200.0, result.getPrice());
        verify(auctionRepository, times(1)).save(auction);
    }

    @Test
    void updatePriceAuctionExpiredExceptionTest() {
        when(auctionRepository.findById(auction.getId())).thenReturn(Optional.of(auction));
        auction.setEndDate(LocalDateTime.now().minusDays(1));
        assertThrows(AuctionExpiredException.class, () -> auctionService.updatePrice(auction.getId(), 200.0));
    }

    @Test
    void updatePriceAuctionNotFoundExceptionTest() {
        when(auctionRepository.findById(auction.getId())).thenReturn(Optional.empty());
        assertThrows(AuctionNotFoundException.class, () -> auctionService.updatePrice(auction.getId(), 200.0));
    }

    @Test
    void updatePriceInvalidPriceProvidedExceptionTest() {
        when(auctionRepository.findById(auction.getId())).thenReturn(Optional.of(auction));
        assertThrows(InvalidPriceProvidedException.class, () -> auctionService.updatePrice(auction.getId(), 50.0));
    }
}
