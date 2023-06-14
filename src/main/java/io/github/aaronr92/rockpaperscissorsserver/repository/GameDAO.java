package io.github.aaronr92.rockpaperscissorsserver.repository;

import io.github.aaronr92.rockpaperscissorsserver.entity.Game;
import io.github.aaronr92.rockpaperscissorsserver.entity.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameDAO extends CrudRepository<Game, Long> {

    /**
     * Finds last game by player
     * @param player to lookup by
     * @return last game of player
     */
    Optional<Game> findFirstByPlayerOrderByIdDesc(Player player);

}
