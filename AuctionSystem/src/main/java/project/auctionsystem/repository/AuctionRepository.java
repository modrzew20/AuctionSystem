package project.auctionsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.auctionsystem.entity.Auction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AuctionRepository extends JpaRepository<Auction, UUID> {

    @Query("select a from Auction a where a.winner.username = ?1 and a.endDate < ?2")
    List<Auction> findByWinnerUsernameAndEndDateBefore(String username, LocalDateTime endDate);
}
