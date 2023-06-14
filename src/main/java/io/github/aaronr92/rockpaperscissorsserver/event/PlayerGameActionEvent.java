package io.github.aaronr92.rockpaperscissorsserver.event;

import com.esotericsoftware.kryonet.Connection;
import io.github.aaronr92.rockpaperscissorsserver.util.GameStepAction;

public record PlayerGameActionEvent(GameStepAction action, Connection connection) {}
