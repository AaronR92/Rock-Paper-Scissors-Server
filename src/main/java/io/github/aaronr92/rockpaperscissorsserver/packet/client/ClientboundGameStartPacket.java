package io.github.aaronr92.rockpaperscissorsserver.packet.client;

public record ClientboundGameStartPacket(String login, String password) {
}
