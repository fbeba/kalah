package com.bbt.kalah.service;

import com.bbt.kalah.model.GameState;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.bbt.kalah.model.GameState.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GameMovesTest extends TestHelper {

    @Test
    public void givenGame_whenPlayerTriesToStartFromOwnPit_moveIsAccepted() {
        // given
        final var selectedPit = 5;
        final var id = gamesService.setupNew();

        // when
        final var board = gamesService.makeMove(id, selectedPit);

        //then
        assertThat(board.getStatus()).containsAllEntriesOf(Map.of(
                "4",  "6",
                "5",  "0",
                "6",  "7",
                "7",  "1",
                "8",  "7",
                "9",  "7",
                "10", "7",
                "11", "7",
                "12", "6"
        ));
    }

    @Test
    public void givenFirstPlayerMoved_whenSecondPlayerTriesToMakeValidMove_moveIsAccepted() {
        // given
        final var selectedPitFirst = 5;
        final var selectedPitSecond = 11;
        final var id = gamesService.setupNew();
        gamesService.makeMove(id, selectedPitFirst);

        // when
        final var board = gamesService.makeMove(id, selectedPitSecond);

        //then
        assertThat(board.getStatus()).containsAllEntriesOf(Map.of(
                "1",  "7",
                "2",  "7",
                "3",  "7",
                "4",  "7",
                "5",  "0",
                "10", "7",
                "11", "0",
                "12", "7",
                "13", "7",
                "14", "1"
        ));
    }

    @Test
    public void givenGame_whenPlayersMakeValidMoves_movesAreAccepted() {
        // given
        final var id = gamesService.setupNew();
        gamesService.makeMove(id, 2);
        gamesService.makeMove(id, 8);
        gamesService.makeMove(id, 3);
        gamesService.makeMove(id, 9);

        // when
        final var board = gamesService.makeMove(id, 3);

        // then
        assertThat(board).isNotNull();
    }

    @Test
    public void givenGame_whenMoveEndsInOwnHome_playerGetsAnotherTurn() {
        // given
        final var id = gamesService.setupNew();
        gamesService.makeMove(id, 1);
        gamesService.makeMove(id, 2);
        gamesService.makeMove(id, 9);
        gamesService.makeMove(id, 3);

        // when
        final var board = gamesService.makeMove(id, 8);

        // then
        assertThat(board).isNotNull();
    }

    @Test
    public void givenCustomBoard_whenPlayerFinishesMoveInEmptyPit_collectsRewardFromBothFinishingAndOpposingPits() {
        // given
        final var stoneQuantities = new int[]{
                0, 1, 0, 0, 0, 8,   9,
                1, 1, 1, 1, 1, 5,   0
        };
        final var id = setupGameWithCustomBoard(stoneQuantities);

        // when
        final var board = gamesService.makeMove(id, 6);

        // then
        assertThat(board.getStatus()).containsAllEntriesOf(Map.of(
                "6", "0",
                "7", "17",
                "13", "0"
        ));
    }

    @Test
    public void givenCustomBoard_whenPlayerMakesFinishingMove_gameIsFinished() {
        // given
        final var stoneQuantities = new int[]{
                0, 1, 0, 0, 0, 2,   9,
                0, 0, 0, 5, 0, 0,  20
        };
        final var id = setupGameWithCustomBoard(stoneQuantities);

        // when
        gamesService.makeMove(id, 2);

        // then
        assertThat(gamesService.getGame(id).getState()).isEqualTo(FINISHED);
    }
}
