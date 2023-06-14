package io.github.aaronr92.rockpaperscissorsserver;

import io.github.aaronr92.rockpaperscissorsserver.entity.Game;
import io.github.aaronr92.rockpaperscissorsserver.entity.Player;
import io.github.aaronr92.rockpaperscissorsserver.repository.GameDAO;
import io.github.aaronr92.rockpaperscissorsserver.repository.PlayerDAO;
import io.github.aaronr92.rockpaperscissorsserver.util.FinishState;
import io.github.aaronr92.rockpaperscissorsserver.util.GameStepAction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class RockPaperScissorsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RockPaperScissorsServerApplication.class, args);
    }

}
