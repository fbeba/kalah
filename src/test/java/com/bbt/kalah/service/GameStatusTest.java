package com.bbt.kalah.service;

import com.bbt.kalah.model.Game;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GameStatusTest extends TestHelper {

    @Test
    public void newBoardCanBeCreated() {
        // when
        final var id = gamesService.setupNew();

        //then
        assertThat(gamesService.getGame(id).getState()).isEqualTo(Game.GameState.ONGOING);
    }

    @Test
    public void givenGame_whenAskedForState_currentBoardStateIsReturned() {
        // given
        final var stoneQuantities = new int[]{
                0, 1, 0, 0, 0, 2, 9,
                0, 0, 1, 5, 0, 0, 20
        };
        final var id = setupGameWithCustomBoard(stoneQuantities);
        gamesService.makeMove(id, 2);

        // when
        final var boardStatus = gamesService.getGameStatus(id);

        // then
        assertThat(boardStatus.getStatus())
                .containsAllEntriesOf(Stream.of(new String[][]{
                        {"1", "0"},
                        {"2", "0"},
                        {"3", "0"},
                        {"4", "0"},
                        {"5", "0"},
                        {"6", "2"},
                        {"7", "15"},
                        {"8", "0"},
                        {"9", "0"},
                        {"10", "1"},
                        {"11", "0"},
                        {"12", "0"},
                        {"13", "0"},
                        {"14", "20"}})
                        .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1])));
    }

    @Test
    public void ongoingGameDoesntReportScore() {
        // when
        final var id = gamesService.setupNew();

        //then
        assertThat(gamesService.getGame(id).getScore()).isEqualTo("Score undetermined, game still in progress.");
    }

    @Test
    public void givenFinishedGame_whenAskedForScore_scoreIsAvailable() {
        // given
        final var stoneQuantities = new int[]{
                0, 1, 0, 0, 0, 2, 9,
                0, 0, 0, 5, 0, 0, 20
        };
        final var id = setupGameWithCustomBoard(stoneQuantities);
        gamesService.makeMove(id, 2);
        assertThat(gamesService.getGame(id).getState()).isEqualTo(Game.GameState.FINISHED);

        // when
        final var score = gamesService.getGame(id).getScore();

        // then
        assertThat(score).isEqualTo("17-20");
    }


}
