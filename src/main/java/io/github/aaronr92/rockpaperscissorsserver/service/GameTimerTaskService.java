package io.github.aaronr92.rockpaperscissorsserver.service;

import com.esotericsoftware.kryonet.Connection;
import io.github.aaronr92.rockpaperscissorsserver.component.EventPublisher;
import io.github.aaronr92.rockpaperscissorsserver.packet.server.ServerboundRemainingTimePacket;
import io.github.aaronr92.rockpaperscissorsserver.util.GameTimerTask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameTimerTaskService {

    public static final Map<Long, GameTimerTask> taskMap = new HashMap<>();
    private final ThreadPoolTaskScheduler taskScheduler;
    private final EventPublisher eventPublisher;

    /**
     * Closes connection after specified delay
     * @param connection to be closed
     * @param delay to close connection after
     */
    public void closeConnectionAfter(Connection connection, int delay) {
        taskScheduler.schedule(
                connection::close,
                Instant.now().plusSeconds(delay)
        );
    }

    /**
     * Checks whether player in taskMap or not
     * @param playerId id of player that plays game
     * @return true if still in game
     */
    public boolean isPlayerInGame(long playerId) {
        return taskMap.containsKey(playerId);
    }

    /**
     * Removes and cancels timer
     * @param playerId id of player that plays game
     */
    public GameTimerTask removeTimerTask(final long playerId) {
        var task = taskMap.remove(playerId);
        task.cancel();
        return task;
    }

    /**
     * Updates GameTimerTask, removing and cancelling old timer and creating new
     * @param playerId id of player that plays game
     * @param connection players connection
     */
    public void updateTimerTask(
            final long playerId,
            final Connection connection
    ) {
        taskMap.remove(playerId).cancel();
        createTimerTask(playerId, connection);
    }

    /**
     * Creates, saves in map and launches new GameTimerTask
     * @param playerId id of player that plays game
     * @param connection players connection
     */
    public void createTimerTask(
            final long playerId,
            final Connection connection
    ) {
        createTimerTask(30, playerId, connection);
    }

    /**
     * Creates, saves in map and launches new GameTimerTask
     * @param remainingTime to game to finish
     * @param playerId id of player that plays game
     * @param connection players connection
     * @return created, saved and launched GameTimerTask
     */
    public GameTimerTask createTimerTask(
            final int remainingTime,
            final long playerId,
            final Connection connection
    ) {
        // Looks like an absolute mess
        return switch (remainingTime) {
            case 30 -> {
                var task = new GameTimerTask(remainingTime) {
                    @Override
                    public void run() {
                        connection.sendTCP(new ServerboundRemainingTimePacket(remainingTime));

                        createTimerTask(15, playerId, connection);
                    }
                };
                taskScheduler.schedule(task, Instant.now().plusSeconds(task.getRemainingTime()));
                taskMap.put(playerId, task);

                yield task;
            }
            case 15 -> {
                var task = new GameTimerTask(remainingTime) {
                    @Override
                    public void run() {
                        connection.sendTCP(new ServerboundRemainingTimePacket(remainingTime));

                        createTimerTask(5, playerId, connection);
                    }
                };
                taskScheduler.schedule(task, Instant.now().plusSeconds(task.getRemainingTime()));
                taskMap.put(playerId, task);

                yield task;
            }
            case 5 -> {
                var task = new GameTimerTask(remainingTime) {
                    @Override
                    public void run() {
                        connection.sendTCP(new ServerboundRemainingTimePacket(remainingTime));

                        createTimerTask(3, playerId, connection);
                    }
                };
                taskScheduler.schedule(task, Instant.now().plusSeconds(task.getRemainingTime()));
                taskMap.put(playerId, task);

                yield task;
            }
            case 3 -> {
                var task = new GameTimerTask(remainingTime) {
                    @Override
                    public void run() {
                        connection.sendTCP(new ServerboundRemainingTimePacket(remainingTime));

                        createTimerTask(1, playerId, connection);
                    }
                };
                taskScheduler.schedule(task, Instant.now().plusSeconds(task.getRemainingTime()));
                taskMap.put(playerId, task);

                yield task;
            }
            case 1 -> {
                var task = new GameTimerTask(remainingTime) {
                    @Override
                    public void run() {
                        connection.sendTCP(new ServerboundRemainingTimePacket(remainingTime));

                        var task = new GameTimerTask(1) {
                            @Override
                            public void run() {
                                eventPublisher.publishTimeExpiredEvent(playerId, connection);
                                taskMap.remove(playerId);
                            }
                        };
                        taskScheduler.schedule(task, Instant.now().plusSeconds(task.getRemainingTime()));
                    }
                };
                taskScheduler.schedule(task, Instant.now().plusSeconds(task.getRemainingTime()));
                taskMap.put(playerId, task);

                yield task;
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + remainingTime);
        };
    }

}
