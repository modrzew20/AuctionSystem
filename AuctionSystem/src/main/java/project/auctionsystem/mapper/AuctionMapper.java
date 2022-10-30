package project.auctionsystem.mapper;

import project.auctionsystem.dto.CreateAuctionDto;
import project.auctionsystem.dto.GetAuctionDto;
import project.auctionsystem.entity.Auction;

public interface AuctionMapper {

    GetAuctionDto auctionToGetAuctionDto(Auction auction);

    Auction createAuctionDtoToAuction(CreateAuctionDto dto);

}
