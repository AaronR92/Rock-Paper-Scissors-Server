package io.github.aaronr92.rockpaperscissorsserver.service;

import io.github.aaronr92.rockpaperscissorsserver.entity.Player;
import io.github.aaronr92.rockpaperscissorsserver.repository.PlayerDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerDAO playerRepo;

    /**
     * Searches player in database and returns result whether player
     * credentials are correct or not
     * @param login login of a player
     * @param password password of a player
     * @return result (authenticated or bad_credentials)
     */
    public String signIn(String login, String password, boolean needsRegistration) {
        var playerOptional = playerRepo.findPlayerByLoginAndPassword(login, password);
        boolean isPresent = playerOptional.isPresent();

        if (isPresent) {
            var player = playerOptional.get();
            player.setLastLogin(LocalDateTime.now());
            playerRepo.save(player);
        } else if (needsRegistration) {
            var now = LocalDateTime.now();
            var player = Player.builder()
                    .login(login)
                    .password(password)
                    .lastLogin(now)
                    .registrationDate(now.toLocalDate())
                    .build();
            playerRepo.save(player);
            isPresent = true;
        }

        return isPresent ? "authenticated" : "bad_credentials";
    }

    public Optional<Player> getPlayerByLoginAndPassword(String login, String password) {
        return playerRepo.findPlayerByLoginAndPassword(login, password);
    }

    public Player getPlayerById(long playerId) {
        return playerRepo.findById(playerId).get();
    }

}
