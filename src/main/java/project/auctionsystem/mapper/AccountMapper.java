package project.auctionsystem.mapper;

import project.auctionsystem.dto.CreateAccountDto;
import project.auctionsystem.dto.GetAccountDto;
import project.auctionsystem.entity.Account;

import javax.validation.constraints.NotNull;

public interface AccountMapper {

    Account createAccountDtoToAccount(@NotNull CreateAccountDto createAccountDto);

    GetAccountDto accountToGetAccountDto(@NotNull Account account);
}
