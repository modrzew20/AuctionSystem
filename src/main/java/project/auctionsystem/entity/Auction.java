package project.auctionsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Auction extends AbstractEntity {

    @Column(nullable = false, length = 256)
    private String title;

    @ManyToOne
    private Account winner;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private LocalDateTime endDate;
}
