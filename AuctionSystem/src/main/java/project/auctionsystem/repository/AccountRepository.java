package project.auctionsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.auctionsystem.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}

