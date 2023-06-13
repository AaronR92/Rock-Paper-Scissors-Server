package io.github.aaronr92.rockpaperscissorsserver.entity;

import io.github.aaronr92.rockpaperscissorsserver.util.FinishState;
import io.github.aaronr92.rockpaperscissorsserver.util.GameStepAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Game {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Enumerated(EnumType.STRING)
    private GameStepAction gameStep1;

    @Enumerated(EnumType.STRING)
    private GameStepAction gameStep2;

    @Enumerated(EnumType.STRING)
    private GameStepAction gameStep3;

    @Enumerated(EnumType.STRING)
    private FinishState finishState;

}
