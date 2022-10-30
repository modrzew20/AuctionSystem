package project.auctionsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetAccountDto {

    private String username;
    private String accessLevel;
    private List<GetAuctionDto> auctions;

}
