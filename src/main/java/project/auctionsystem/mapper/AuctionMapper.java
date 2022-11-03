package project.auctionsystem.mapper;

import project.auctionsystem.dto.CreateAuctionDto;
import project.auctionsystem.dto.GetAuctionDto;
import project.auctionsystem.entity.Auction;

import javax.validation.constraints.NotNull;

public interface AuctionMapper {

    GetAuctionDto auctionToGetAuctionDto(@NotNull Auction auction);

    Auction createAuctionDtoToAuction(@NotNull CreateAuctionDto dto);

}
