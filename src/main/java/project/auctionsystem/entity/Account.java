package project.auctionsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private AccessLevel accessLevel;

    @OneToMany
    @Setter(lombok.AccessLevel.NONE)
    private List<Auction> auctions;

}
