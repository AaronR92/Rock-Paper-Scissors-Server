package io.github.aaronr92.rockpaperscissorsserver.service;

import com.esotericsoftware.kryonet.Connection;
import io.github.aaronr92.rockpaperscissorsserver.entity.Game;
import io.github.aaronr92.rockpaperscissorsserver.packet.server.ServerboundConnectionPacket;
import io.github.aaronr92.rockpaperscissorsserver.packet.server.ServerboundGameEndPacket;
import io.github.aaronr92.rockpaperscissorsserver.packet.server.ServerboundGameStartPacket;
import io.github.aaronr92.rockpaperscissorsserver.repository.GameDAO;
import io.github.aaronr92.rockpaperscissorsserver.util.FinishState;
import io.github.aaronr92.rockpaperscissorsserver.util.GameStepAction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class GameService {

    private final GameDAO gameRepo;
    private final PlayerService playerService;
    private final GameTimerTaskService timerTaskService;

    private final Random random = new Random();
    private final GameStepAction[] actions = GameStepAction.values();
    private final Map<String, Long> playersMap = new HashMap<>();

    public void startGame(
            String login,
            String password,
            String playerRemoteAddress,
            Connection connection
    ) {
        var playerOptional = playerService.getPlayerByLoginAndPassword(login, password);

        if (playerOptional.isEmpty()) {
            connection.sendTCP(new ServerboundConnectionPacket("bad_credentials"));
        }

        var player = playerOptional.get();
        var currentGameOptional = gameRepo.findFirstByPlayerOrderByIdDesc(player);

        if (currentGameOptional.isPresent()) {
            var currentGame = currentGameOptional.get();
            if (currentGame.getFinishState() == null) {
                continueGame(currentGame, player.getId(), connection);

                playersMap.put(playerRemoteAddress, player.getId());
                return;
            }
        }

        Game game = Game.builder()
                .player(player)
                .remainingTime(30)
                .build();

        connection.sendTCP(new ServerboundGameStartPacket(player.getId()));
        timerTaskService.createTimerTask(player.getId(), connection);

        playersMap.put(playerRemoteAddress, player.getId());

        gameRepo.save(game);
    }

    private void continueGame(Game game, long playerId, Connection connection) {
        timerTaskService.createTimerTask(game.getRemainingTime(), playerId, connection);
    }

    public void handlePlayerChoice(long playerId, GameStepAction action, Connection connection) {
        var game = gameRepo
                .findFirstByPlayerOrderByIdDesc(playerService.getPlayerById(playerId))
                .get();

        if (game.getGameStep1Player() == null) {
            game.setGameStep1Player(action);
            var serverAction = getServerAction();
            System.out.println("Server action is "+ serverAction);
            game.setGameStep1Server(serverAction);
            timerTaskService.updateTimerTask(playerId, connection);
        } else if (game.getGameStep2Player() == null) {
            game.setGameStep2Player(action);
            var serverAction = getServerAction();
            System.out.println("Server action is "+ serverAction);
            game.setGameStep2Server(serverAction);
            timerTaskService.updateTimerTask(playerId, connection);
        } else if (game.getGameStep3Player() == null) {
            game.setGameStep3Player(action);
            var serverAction = getServerAction();
            System.out.println("Server action is "+ serverAction);
            game.setGameStep3Server(serverAction);
            timerTaskService.removeTimerTask(playerId);

            var result = calculateWinner(game);
            game.setFinishState(result);
            connection.sendTCP(new ServerboundGameEndPacket(result));
            timerTaskService.closeConnectionAfter(connection, 5);
        }

        gameRepo.save(game);
    }

    public void disconnect(String playerRemoteAddress) {
        long playerId = playersMap.get(playerRemoteAddress);
        var player = playerService.getPlayerById(playerId);

        if (!timerTaskService.isPlayerInGame(playerId))
            return;

        var task = timerTaskService.removeTimerTask(playerId);

        Game game = gameRepo.findFirstByPlayerOrderByIdDesc(player).get();
        game.setRemainingTime(task.getRemainingTime());

        gameRepo.save(game);
    }

    private GameStepAction getServerAction() {
        return actions[random.nextInt(actions.length)];
    }

    private FinishState calculateWinner(Game game) {
        var wR1 = calculateWinnerForRound(game.getGameStep1Player(), game.getGameStep1Server());
        var wR2 = calculateWinnerForRound(game.getGameStep2Player(), game.getGameStep2Server());
        var wR3 = calculateWinnerForRound(game.getGameStep3Player(), game.getGameStep3Server());

        int wR1O = wR1.ordinal() - 1;
        int wR2O = wR2.ordinal() - 1;
        int wR3O = wR3.ordinal() - 1;

        int sum = wR1O + wR2O + wR3O;

        if (sum == 0)
            return FinishState.DRAW;

        return sum < 0 ? FinishState.DEFEAT : FinishState.WIN;
    }

    private FinishState calculateWinnerForRound(
            GameStepAction playerAction,
            GameStepAction serverAction
    ) {
        if (playerAction.equals(serverAction))
            return FinishState.DRAW;
        if (playerAction.equals(GameStepAction.ROCK) &&
                serverAction.equals(GameStepAction.SCISSORS))
            return FinishState.WIN;

        if (playerAction.ordinal() - 1 == serverAction.ordinal()) return FinishState.WIN;

        return FinishState.DEFEAT;
    }
}
