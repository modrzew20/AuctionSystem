package project.auctionsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.auctionsystem.dto.CreateAuctionDto;
import project.auctionsystem.dto.GetAuctionDto;

import java.util.List;
import java.util.UUID;

@RequestMapping("/auctions")
public interface AuctionController {

    @GetMapping
    ResponseEntity<List<GetAuctionDto>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<GetAuctionDto> get(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<GetAuctionDto> create(@RequestBody CreateAuctionDto dto);

    @PatchMapping("/{id}")
    ResponseEntity<GetAuctionDto> updatePrice(
            @PathVariable UUID id,
            @RequestParam Double price);

}
