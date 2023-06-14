package io.github.aaronr92.rockpaperscissorsserver.component;

import com.esotericsoftware.kryonet.Connection;
import io.github.aaronr92.rockpaperscissorsserver.event.*;
import io.github.aaronr92.rockpaperscissorsserver.util.GameStepAction;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishConnectionEvent(
            final String login,
            final String password,
            boolean needsRegistration,
            final Connection connection
            ) {
        var event = new ConnectionEvent(login, password, needsRegistration, connection);
        eventPublisher.publishEvent(event);
    }

    public void publishDisconnectionEvent(
            final String playerRemoteAddress
    ) {
        var event = new DisconnectionEvent(playerRemoteAddress);
        eventPublisher.publishEvent(event);
    }

    public void publishGameStartEvent(
            final String login,
            final String password,
            final Connection connection,
            final String playerRemoteAddress
    ) {
        var event = new GameStartEvent(
                login,
                password,
                connection,
                playerRemoteAddress
        );
        eventPublisher.publishEvent(event);
    }

    public void publishPlayerGameActionEvent(
            long playerId,
            GameStepAction gameStepAction,
            Connection connection
    ) {
        var event = new PlayerGameStepActionEvent(
                playerId,
                gameStepAction,
                connection
        );
        eventPublisher.publishEvent(event);
    }

    public void publishTimeExpiredEvent(
            long playerId,
            Connection connection
    ) {
        var event = new TimeExpiredEvent(playerId, connection);
        eventPublisher.publishEvent(event);
    }

}
