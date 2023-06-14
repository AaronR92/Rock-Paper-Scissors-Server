package io.github.aaronr92.rockpaperscissorsserver.packet.server;

import io.github.aaronr92.rockpaperscissorsserver.util.FinishState;

public record ServerboundGameEndPacket(FinishState gameResult) {}
