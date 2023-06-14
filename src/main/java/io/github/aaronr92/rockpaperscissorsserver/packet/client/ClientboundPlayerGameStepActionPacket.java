package io.github.aaronr92.rockpaperscissorsserver.packet.client;

import io.github.aaronr92.rockpaperscissorsserver.util.GameStepAction;

public record ClientboundPlayerGameStepActionPacket(long playerId, GameStepAction action) {
}
