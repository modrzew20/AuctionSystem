package project.auctionsystem.mapper;

import project.auctionsystem.dto.CreateAccountDto;
import project.auctionsystem.dto.GetAccountDto;
import project.auctionsystem.entity.Account;

public interface AccountMapper {

    Account createAccountDtoToAccount(CreateAccountDto createAccountDto);

    GetAccountDto accountToGetAccountDto(Account account);
}
