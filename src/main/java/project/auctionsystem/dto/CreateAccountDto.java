package project.auctionsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.auctionsystem.validation.Username;

@Getter
@Setter
@NoArgsConstructor
public class CreateAccountDto {

    @Username
    private String username;
    private String password;
    private AccessLevelDto accessLevel;

}
