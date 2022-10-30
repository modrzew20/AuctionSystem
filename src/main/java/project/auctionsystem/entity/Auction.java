package project.auctionsystem.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Auction {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, length = 256)
    private String title;

    @ManyToOne
    private Account winner;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private LocalDateTime endDate;
}
