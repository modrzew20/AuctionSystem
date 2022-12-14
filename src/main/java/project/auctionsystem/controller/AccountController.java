package project.auctionsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.auctionsystem.dto.CreateAccountDto;
import project.auctionsystem.dto.GetAccountDto;
import project.auctionsystem.dto.GetBalanceDto;

import java.util.List;

@RequestMapping("/accounts")
public interface AccountController {

    @GetMapping
    ResponseEntity<List<GetAccountDto>> getAll();

    @GetMapping("/{username}")
    ResponseEntity<GetAccountDto> get(@PathVariable String username);

    @PostMapping
    ResponseEntity<GetAccountDto> create(@RequestBody CreateAccountDto dto);

    @GetMapping("/{username}/balance/{currency}")
    ResponseEntity<GetBalanceDto> getBalance(@PathVariable String username, @PathVariable String currency);

    @PutMapping("/{username}")
    ResponseEntity<GetAccountDto> updatePassword(@PathVariable String username, @RequestParam String password);

}
