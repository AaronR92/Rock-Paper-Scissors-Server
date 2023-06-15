package io.github.aaronr92.rockpaperscissorsserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Player {

    @Id
    @GeneratedValue(generator = "playerSeq")
    @SequenceGenerator(name = "playerSeq", sequenceName = "PLAYER_SEQ", allocationSize = 1)
    private long id;

    private String login;

    private String password;

    private LocalDateTime lastLogin;

    private LocalDate registrationDate;

}
