package project.auctionsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.auctionsystem.entity.AccessLevel;

public interface AccessLevelRepository extends JpaRepository<AccessLevel, String> {
}
