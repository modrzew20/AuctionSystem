package project.auctionsystem.service;


import project.auctionsystem.entity.Account;
import project.auctionsystem.exception.AccessLevelNotFoundException;
import project.auctionsystem.exception.AccountNotFoundException;

import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account get(String username) throws AccountNotFoundException;

    Account create(Account account) throws AccessLevelNotFoundException;

    Double getBalance(String username, String currency) throws AccountNotFoundException;
}