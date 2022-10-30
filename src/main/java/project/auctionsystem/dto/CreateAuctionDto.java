package project.auctionsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateAuctionDto {

    private String title;
    private Double price;
    private LocalDateTime endDate;

}
