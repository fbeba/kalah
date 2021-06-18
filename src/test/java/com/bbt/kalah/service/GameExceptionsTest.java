package com.bbt.kalah.service;

import com.bbt.kalah.exception.GameAlreadyFinishedException;
import com.bbt.kalah.exception.GameDoesNotExistException;
import com.bbt.kalah.exception.IllegalMoveException;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameExceptionsTest extends TestHelper {

    @Test
    public void whenPlayerTriesToPlayNonExistentGame_exceptionIsThrown() {
        // given
        final var id = gamesService.setupNew();

        // when
        final var exception = assertThrows(GameDoesNotExistException.class, () -> gamesService.makeMove(id + 1, 7));

        // then
        assertThat(exception.getMessage()).isEqualTo(format("Game with id %d does not exist.", id + 1));
    }

    @Test
    public void givenNewGame_whenFirstPlayerTriesToStartFromOpponentsPit_exceptionIsThrown() {
        // given
        final var selectedPit = 8;
        final var id = gamesService.setupNew();

        // when
        final var exception = assertThrows(IllegalMoveException.class, () -> gamesService.makeMove(id, selectedPit));

        //then
        assertThat(exception.getMessage()).isEqualTo(format("Pit %d belongs to other player.", selectedPit));
    }

    @Test
    public void givenCustomBoard_whenPlayerTriesToStartFromEmptyPit_exceptionIsThrown() {
        // given
        final var stoneQuantities = new int[]{
                0, 1, 0, 0, 0, 8,   9,
                1, 1, 1, 1, 1, 5,   0
        };
        final var id = setupGameWithCustomBoard(stoneQuantities);

        // when
        final var exception = assertThrows(IllegalMoveException.class, () -> gamesService.makeMove(id, 1));

        // then
        assertThat(exception.getMessage()).isEqualTo("Can't start the move from an empty pit.");
    }

    @Test
    public void givenCustomBoard_whenPlayerTriesToStartFromHomePit_exceptionIsThrown() {
        // given
        final var homePit = 7;
        final var id = gamesService.setupNew();

        // when
        final var exception = assertThrows(IllegalMoveException.class, () -> gamesService.makeMove(id, homePit));

        // then
        assertThat(exception.getMessage()).isEqualTo("Can't start the move from Home pit.");
    }

    @Test
    public void givenGame_whenPlayerTriesToStartFromOpponentsPit_exceptionIsThrown() {
        // given
        final var selectedPit = 8;
        final var id = gamesService.setupNew();

        // when
        final var exception = assertThrows(IllegalMoveException.class, () -> gamesService.makeMove(id, selectedPit));

        //then
        assertThat(exception.getMessage()).isEqualTo(format("Pit %d belongs to other player.", selectedPit));
    }

    @Test
    public void givenFinishedGame_whenPlayerMakesMove_exceptionIsThrown() {
        // given
        final var stoneQuantities = new int[]{
                0, 1, 0, 0, 0, 2,   9,
                0, 0, 0, 5, 0, 0,  20
        };
        final var id = setupGameWithCustomBoard(stoneQuantities);
        gamesService.makeMove(id, 2);

        // when
        final var exception = assertThrows(GameAlreadyFinishedException.class, () -> gamesService.makeMove(id, 8));

        //then
        assertThat(exception.getMessage()).isEqualTo(format("Game %d is already finished. Final score is 17-20.", id));
    }

}
