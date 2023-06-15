package io.github.aaronr92.rockpaperscissorsserver.listener;

import io.github.aaronr92.rockpaperscissorsserver.event.*;
import io.github.aaronr92.rockpaperscissorsserver.packet.server.ServerboundConnectionPacket;
import io.github.aaronr92.rockpaperscissorsserver.service.GameService;
import io.github.aaronr92.rockpaperscissorsserver.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppEventListener {

    private final PlayerService playerService;
    private final GameService gameService;

    @EventListener
    public void onConnection(ConnectionEvent event) {
        var result = playerService.signIn(
                event.getLogin(),
                event.getPassword(),
                event.isNeedsRegistration()
        );

        var conn = event.getConnection();
        conn.sendTCP(new ServerboundConnectionPacket(result));
        conn.close();
    }

    @EventListener
    public void onDisconnection(DisconnectionEvent event) {
        gameService.disconnect(event.connectionId());
    }

    @EventListener
    public void onGameStart(GameStartEvent event) {
        gameService.startGame(
                event.login(),
                event.password(),
                event.connectionId(),
                event.connection()
        );
    }

    @EventListener
    public void onPlayerGameAction(PlayerGameStepActionEvent event) {
        gameService.handlePlayerChoice(
                event.playerId(),
                event.gameStepAction(),
                event.connection()
        );
    }

    @EventListener
    public void onTimeExpired(TimeExpiredEvent event) {
        gameService.skipRound(event.playerId(), event.connection());
    }

}
