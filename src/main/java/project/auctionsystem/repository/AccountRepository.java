package project.auctionsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.auctionsystem.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByUsername(String username);

}

