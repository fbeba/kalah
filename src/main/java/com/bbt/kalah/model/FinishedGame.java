package com.bbt.kalah.model;

import com.bbt.kalah.exception.GameAlreadyFinishedException;

import static java.lang.String.format;

public class FinishedGame extends Game {

    public FinishedGame(Game game) {
        super(game);
    }

    @Override
    public Game makeMove(int startingPitNumber) {
        throw new GameAlreadyFinishedException(format("Game %d is already finished. Final score is %s.",
                id,
                getScore()));
    }

    @Override
    public String getScore() {
        return board.get(playerOne.getHomePitIndex()).getStones() +
                "-" +
                board.get(playerTwo.getHomePitIndex()).getStones();
    }
}