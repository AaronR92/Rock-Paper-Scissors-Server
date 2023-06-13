package io.github.aaronr92.rockpaperscissorsserver.repository;

import io.github.aaronr92.rockpaperscissorsserver.entity.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameDAO extends CrudRepository<Game, Long> {
}
