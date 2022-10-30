package project.auctionsystem.entity;

import lombok.Getter;
import lombok.Setter;
import project.auctionsystem.utils.Taggable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class AbstractEntity implements Taggable {

    @Id
    @GeneratedValue
    private UUID id;

    @Version
    private Long version;

    @Setter
    @Column(nullable = false)
    private Long etagVersion = 0L;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @Override
    public void updateTag() {
        etagVersion++;
    }

    @Override
    public String generateETagMessage() {
        return String.format("%s-%s-%s", getEtagVersion(), getClass().getSimpleName(), getId());
    }
}