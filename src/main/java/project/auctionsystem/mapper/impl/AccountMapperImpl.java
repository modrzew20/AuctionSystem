package project.auctionsystem.mapper.impl;

import org.springframework.stereotype.Component;
import project.auctionsystem.dto.CreateAccountDto;
import project.auctionsystem.dto.GetAccountDto;
import project.auctionsystem.entity.AccessLevel;
import project.auctionsystem.entity.Account;
import project.auctionsystem.mapper.AccountMapper;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account createAccountDtoToAccount(CreateAccountDto createAccountDto) {
        AccessLevel accessLevel = new AccessLevel();
        accessLevel.setName(createAccountDto.getAccessLevel().getName());

        Account account = new Account();
        account.setUsername(createAccountDto.getUsername());
        account.setPassword(createAccountDto.getPassword());
        account.setAccessLevel(accessLevel);

        return account;
    }

    @Override
    public GetAccountDto accountToGetAccountDto(Account account) {
        GetAccountDto getAccountDto = new GetAccountDto();
        getAccountDto.setUsername(account.getUsername());
        getAccountDto.setAccessLevel(account.getAccessLevel().getName());

        return getAccountDto;
    }


}
