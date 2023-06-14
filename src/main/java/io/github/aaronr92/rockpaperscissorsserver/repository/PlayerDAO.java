package io.github.aaronr92.rockpaperscissorsserver.repository;

import io.github.aaronr92.rockpaperscissorsserver.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerDAO extends CrudRepository<Player, Long> {

    Optional<Player> findPlayerByLoginAndPassword(String login, String password);

}
