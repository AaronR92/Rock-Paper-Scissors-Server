package io.github.aaronr92.rockpaperscissorsserver.listener;

import io.github.aaronr92.rockpaperscissorsserver.event.ConnectionEvent;
import io.github.aaronr92.rockpaperscissorsserver.event.DisconnectionEvent;
import io.github.aaronr92.rockpaperscissorsserver.event.GameStartEvent;
import io.github.aaronr92.rockpaperscissorsserver.event.PlayerGameStepActionEvent;
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
        System.out.println(event.getLogin() + " " + event.getPassword());
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
        gameService.disconnect(event.playerRemoteAddress());
    }

    @EventListener
    public void onGameStart(GameStartEvent event) {
        gameService.startGame(event.login(), event.password(), event.connection());
    }

    @EventListener
    public void onPlayerGameAction(PlayerGameStepActionEvent event) {
        gameService.handlePlayerChoice(
                event.playerId(),
                event.gameStepAction(),
                event.connection()
        );
    }

}
