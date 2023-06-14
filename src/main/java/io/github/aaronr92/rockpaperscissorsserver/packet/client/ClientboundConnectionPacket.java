package io.github.aaronr92.rockpaperscissorsserver.packet.client;

import lombok.*;

public record ClientboundConnectionPacket(String login, String password) {}
