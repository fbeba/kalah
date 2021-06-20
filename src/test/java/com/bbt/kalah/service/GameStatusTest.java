package com.bbt.kalah.service;

import com.bbt.kalah.model.GameState;
import org.junit.jupiter.api.Test;

import static com.bbt.kalah.model.GameState.FINISHED;
import static com.bbt.kalah.model.GameState.ONGOING;
import static org.assertj.core.api.Assertions.assertThat;

public class GameStatusTest extends TestHelper {

    @Test
    public void newBoardCanBeCreated() {
        // when
        final var id = gamesService.setupNew();

        //then
        assertThat(gamesService.getGame(id).getState()).isEqualTo(ONGOING);
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
                0, 1, 0, 0, 0, 2,   9,
                0, 0, 0, 5, 0, 0,  20
        };
        final var id = setupGameWithCustomBoard(stoneQuantities);
        gamesService.makeMove(id, 2);
        assertThat(gamesService.getGame(id).getState()).isEqualTo(FINISHED);

        // when
        final var score = gamesService.getGame(id).getScore();

        // then
        assertThat(score).isEqualTo("17-20");
    }

}
