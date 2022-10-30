package project.auctionsystem.mapper.impl;

import org.springframework.stereotype.Component;
import project.auctionsystem.dto.CreateAuctionDto;
import project.auctionsystem.dto.GetAuctionDto;
import project.auctionsystem.entity.Auction;
import project.auctionsystem.mapper.AuctionMapper;

@Component
public class AuctionMapperImpl implements AuctionMapper {
    @Override
    public GetAuctionDto auctionToGetAuctionDto(Auction auction) {
        GetAuctionDto dto = new GetAuctionDto();
        dto.setId(auction.getId());
        dto.setPrice(auction.getPrice());
        dto.setWinnerUsername(auction.getWinner().getUsername());
        dto.setPrice(auction.getPrice());
        dto.setEndDate(auction.getEndDate());

        return dto;
    }

    @Override
    public Auction createAuctionDtoToAuction(CreateAuctionDto dto) {
        Auction auction = new Auction();
        auction.setPrice(dto.getPrice());
        auction.setTitle(dto.getTitle());
        auction.setEndDate(dto.getEndDate());

        return auction;
    }
}
