package io.github.aaronr92.rockpaperscissorsserver.service;

import io.github.aaronr92.rockpaperscissorsserver.entity.Player;
import io.github.aaronr92.rockpaperscissorsserver.repository.PlayerDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public String signIn(String login, String password) {
        var player = playerRepo.findPlayerByLoginAndPassword(login, password);

        return player.isPresent() ? "authenticated" : "bad_credentials";
    }

    public Optional<Player> getPlayerByLoginAndPassword(String login, String password) {
        return playerRepo.findPlayerByLoginAndPassword(login, password);
    }

    public Player getPlayerById(long playerId) {
        return playerRepo.findById(playerId).get();
    }

}
