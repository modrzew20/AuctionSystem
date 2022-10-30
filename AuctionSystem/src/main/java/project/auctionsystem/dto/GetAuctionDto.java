package project.auctionsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GetAuctionDto {

    UUID id;
    String title;
    String winnerUsername;
    Double price;
    LocalDateTime endDate;

}
