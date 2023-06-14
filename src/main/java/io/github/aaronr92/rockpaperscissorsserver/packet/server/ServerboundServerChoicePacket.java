package io.github.aaronr92.rockpaperscissorsserver.packet.server;

import io.github.aaronr92.rockpaperscissorsserver.util.GameStepAction;

public record ServerboundServerChoicePacket(GameStepAction action) {
}
