package project.auctionsystem.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.auctionsystem.entity.Account;
import project.auctionsystem.repository.AccountRepository;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    private static final String REASON = "User with username %s not found";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(REASON, username)));

        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(account.getAccessLevel().getName()));

        return new UserDetailsImpl(
                account.getUsername(),
                account.getPassword(),
                authorities
        );

    }
}
