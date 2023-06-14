package io.github.aaronr92.rockpaperscissorsserver.packet.client;

import io.github.aaronr92.rockpaperscissorsserver.util.GameStepAction;

public record ClientboundPlayerChoicePacket(GameStepAction action) {
}
