package project.auctionsystem.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import project.auctionsystem.controller.AccountController;
import project.auctionsystem.dto.CreateAccountDto;
import project.auctionsystem.dto.GetAccountDto;
import project.auctionsystem.dto.GetBalanceDto;
import project.auctionsystem.entity.Account;
import project.auctionsystem.exception.AccessLevelNotFoundException;
import project.auctionsystem.exception.AccountNotFoundException;
import project.auctionsystem.mapper.AccountMapper;
import project.auctionsystem.service.AccountService;
import project.auctionsystem.utils.EtagGenerator;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;
    private final EtagGenerator etagGenerator;

    @Override
    public ResponseEntity<List<GetAccountDto>> getAll() {
        return ResponseEntity
                .ok()
                .body(accountService
                        .getAll().stream()
                        .map(accountMapper::accountToGetAccountDto).toList());
    }

    @Override
    public ResponseEntity<GetAccountDto> get(String username) {
        try {
            Account account = accountService.get(username);
            return ResponseEntity
                    .ok()
                    .eTag(etagGenerator.generateEtag(account))
                    .body(accountMapper
                            .accountToGetAccountDto(account));
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<GetAccountDto> create(CreateAccountDto dto) {
        Account account = accountMapper.createAccountDtoToAccount(dto);
        try {
            return ResponseEntity
                    .status(201)
                    .body(accountMapper
                            .accountToGetAccountDto(accountService.create(account)));
        } catch (AccessLevelNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<GetBalanceDto> getBalance(String username, String currency) {
        try {
            return ResponseEntity
                    .ok()
                    .body(accountService.getBalance(username, currency));
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
