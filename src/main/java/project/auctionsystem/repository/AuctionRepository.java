package project.auctionsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.auctionsystem.entity.Auction;

import java.util.List;
import java.util.UUID;

public interface AuctionRepository extends JpaRepository<Auction, UUID> {

    List<Auction> findByWinner_Username(String username);
}
