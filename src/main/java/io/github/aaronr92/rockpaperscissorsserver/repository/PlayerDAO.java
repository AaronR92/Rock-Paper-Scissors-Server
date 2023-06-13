package io.github.aaronr92.rockpaperscissorsserver.repository;

import io.github.aaronr92.rockpaperscissorsserver.entity.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerDAO extends CrudRepository<Player, Long> {
}
