package project.auctionsystem.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import project.auctionsystem.controller.AuctionController;
import project.auctionsystem.dto.CreateAuctionDto;
import project.auctionsystem.dto.GetAuctionDto;
import project.auctionsystem.entity.Auction;
import project.auctionsystem.exception.*;
import project.auctionsystem.mapper.AuctionMapper;
import project.auctionsystem.service.AuctionService;
import project.auctionsystem.utils.EtagGenerator;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuctionControllerImpl implements AuctionController {

    private final AuctionService auctionService;
    private final AuctionMapper auctionMapper;
    private final EtagGenerator etagGenerator;

    @Override
    public ResponseEntity<List<GetAuctionDto>> getAll() {
        return ResponseEntity
                .ok()
                .body(auctionService
                        .getAll().stream()
                        .map(auctionMapper::auctionToGetAuctionDto).toList());
    }

    @Override
    public ResponseEntity<GetAuctionDto> get(UUID id) {
        try {
            Auction auction = auctionService.get(id);
            return ResponseEntity
                    .ok()
                    .eTag(etagGenerator.generateEtag(auction))
                    .body(auctionMapper
                            .auctionToGetAuctionDto(auction));
        } catch (AuctionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<GetAuctionDto> create(CreateAuctionDto dto) {
        Auction auction = auctionMapper.createAuctionDtoToAuction(dto);
        try {
            return ResponseEntity
                    .ok()
                    .body(auctionMapper
                            .auctionToGetAuctionDto(auctionService.create(auction)));
        } catch (InvalidEndDateProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<GetAuctionDto> updatePrice(UUID id, Double price) {
        try {
            Auction auction = auctionService.updatePrice(id, price);
            return ResponseEntity
                    .ok()
                    .body(auctionMapper
                            .auctionToGetAuctionDto(auction));
        } catch (AuctionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuctionExpiredException | InvalidPriceProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (EtagException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
