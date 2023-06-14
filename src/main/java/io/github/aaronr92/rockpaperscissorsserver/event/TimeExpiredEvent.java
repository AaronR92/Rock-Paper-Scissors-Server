package io.github.aaronr92.rockpaperscissorsserver.event;

import com.esotericsoftware.kryonet.Connection;

public record TimeExpiredEvent(long playerId, Connection connection) {}
