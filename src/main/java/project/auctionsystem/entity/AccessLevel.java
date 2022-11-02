package project.auctionsystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccessLevel {

    public static final String CLIENT = "CLIENT";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";

    @Id
    private String name;

}
