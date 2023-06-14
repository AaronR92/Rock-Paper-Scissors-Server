package io.github.aaronr92.rockpaperscissorsserver.listener;

import com.esotericsoftware.kryonet.Connection;
import io.github.aaronr92.rockpaperscissorsserver.event.ConnectionEvent;
import io.github.aaronr92.rockpaperscissorsserver.event.GameStartEvent;
import io.github.aaronr92.rockpaperscissorsserver.event.PlayerGameStepActionEvent;
import io.github.aaronr92.rockpaperscissorsserver.packet.server.ServerboundConnectionPacket;
import io.github.aaronr92.rockpaperscissorsserver.packet.server.ServerboundRemainingTimePacket;
import io.github.aaronr92.rockpaperscissorsserver.service.GameService;
import io.github.aaronr92.rockpaperscissorsserver.service.GameTimerTaskService;
import io.github.aaronr92.rockpaperscissorsserver.service.PlayerService;
import io.github.aaronr92.rockpaperscissorsserver.util.GameTimerTask;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
@RequiredArgsConstructor
public class AppEventListener {

    private final PlayerService playerService;
    private final GameService gameService;
    private final GameTimerTaskService timerTaskService;

    @EventListener
    public void onConnection(ConnectionEvent event) {
        System.out.println(event.getLogin() + " " + event.getPassword());
        var result = playerService.signIn(event.getLogin(), event.getPassword());

        var conn = event.getConnection();
        conn.sendTCP(new ServerboundConnectionPacket(result));
        conn.close();
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
