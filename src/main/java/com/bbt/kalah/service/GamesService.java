package com.bbt.kalah.service;

import com.bbt.kalah.exception.GameDoesNotExistException;
import com.bbt.kalah.model.BoardStatus;
import com.bbt.kalah.model.Game;
import com.bbt.kalah.model.OngoingGame;
import com.bbt.kalah.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GamesService {

    private final Map<Integer, Game> games = new ConcurrentHashMap<>();

    private final BoardProvider boardProvider;

    public Integer setupNew() {
        final var playerOne = Player.FIRST;
        final var playerTwo = Player.SECOND;
        final var game = OngoingGame.builder()
                .forPlayers(playerOne, playerTwo)
                .onBoard(boardProvider.createBoard(playerOne, playerTwo))
                .build();
        return storeGame(game);
    }

    public Game getGame(int id) {
        return games.get(id);
    }

    public BoardStatus makeMove(int gameId, int pitNumber) {
        final var game = getGameById(gameId);
        try {
            storeGame(game.makeMove(pitNumber));
        } catch (final Exception e) {
            storeGame(game);
            throw e;
        }
        return game.getBoardStatus();
    }

    public BoardStatus getGameStatus(Integer id) {
        return getGameById(id).getBoardStatus();
    }

    private Game getGameById(int gameId) {
        return Optional.ofNullable(games.remove(gameId))
                .orElseThrow(() -> new GameDoesNotExistException(format("Game with id %d does not exist.", gameId)));
    }

    private int storeGame(Game game) {
        games.put(game.getId(), game);
        return game.getId();
    }
}
