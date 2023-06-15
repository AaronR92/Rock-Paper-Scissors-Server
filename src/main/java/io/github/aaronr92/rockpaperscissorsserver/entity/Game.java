package io.github.aaronr92.rockpaperscissorsserver.entity;

import io.github.aaronr92.rockpaperscissorsserver.util.FinishState;
import io.github.aaronr92.rockpaperscissorsserver.util.GameStepAction;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Game {

    @Id
    @GeneratedValue(generator = "gameSeq")
    @SequenceGenerator(name = "gameSeq", sequenceName = "GAME_SEQ", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Enumerated(EnumType.STRING)
    private GameStepAction gameStep1Player;

    @Enumerated(EnumType.STRING)
    private GameStepAction gameStep1Server;

    @Enumerated(EnumType.STRING)
    private GameStepAction gameStep2Player;

    @Enumerated(EnumType.STRING)
    private GameStepAction gameStep2Server;

    @Enumerated(EnumType.STRING)
    private GameStepAction gameStep3Player;

    @Enumerated(EnumType.STRING)
    private GameStepAction gameStep3Server;

    private int remainingTime;

    @Enumerated(EnumType.STRING)
    private FinishState finishState;

}
