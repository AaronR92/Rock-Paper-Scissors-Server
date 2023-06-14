package io.github.aaronr92.rockpaperscissorsserver.event;

import com.esotericsoftware.kryonet.Connection;

public record GameStartEvent(String login, String password, Connection connection) {}
