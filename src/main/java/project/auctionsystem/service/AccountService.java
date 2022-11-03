package project.auctionsystem.service;


import project.auctionsystem.dto.GetBalanceDto;
import project.auctionsystem.entity.Account;
import project.auctionsystem.exception.AccessLevelNotFoundException;
import project.auctionsystem.exception.AccountNotFoundException;
import project.auctionsystem.exception.EtagException;

import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account get(String username) throws AccountNotFoundException;

    Account create(Account account) throws AccessLevelNotFoundException;

    GetBalanceDto getBalance(String username, String currency) throws AccountNotFoundException;

    Account updatePassword(String username, String password) throws AccountNotFoundException, EtagException;
}
