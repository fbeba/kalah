package com.bbt.kalah.model;

import com.bbt.kalah.exception.IllegalMoveException;

import java.util.List;
import java.util.stream.Stream;

public class OngoingGame extends Game {

    private OngoingGame(Player playerA, Player playerB, List<Pit> board) {
        super(playerA, playerB, board);
    }

    @Override
    public Game makeMove(int startingPitNumber) {
        final var startingPit = getPitByNumber(startingPitNumber);
        validatePitChoice(startingPit);
        final var finishingPit = distributeStones(startingPit);
        collectWonStones(finishingPit);
        if (isFinished()) {
            return new FinishedGame(this);
        }
        handleTurnPassing(finishingPit);
        return this;
    }

    @Override
    public String getScore() {
        return ("Score undetermined, game still in progress.");
    }

    private Pit distributeStones(Pit startingPit) {
        var hand = startingPit.takeStones();
        var pitIndex = startingPit.getIndex();
        do {
            pitIndex = (pitIndex + 1) % 14;
            var pit = board.get(pitIndex);
            if (pit.isStorableForPlayer(turn)) {
                pit.putStones(1);
                hand -= 1;
            }
        }
        while (hasStones(hand));
        return board.get(pitIndex);
    }

    private void collectWonStones(Pit finishingPit) {
        if (finishingPit.belongsTo(turn)
                && finishingPit.hasOneStone()
                && finishingPit.isNonHomePit()) {
            final var oppositePit = board.get(finishingPit.findOppositePitIndex());
            final var reward = finishingPit.takeStones() + oppositePit.takeStones();
            collect(reward);
        }
    }

    private void collect(int reward) {
        board.get(turn.getHomePitIndex()).putStones(reward);
    }

    private void handleTurnPassing(Pit finishingPit) {
        if (finishingPit.isNonHomePit() || !finishingPit.belongsTo(turn)) {
            turn = turn.equals(playerOne) ? playerTwo : playerOne;
        }
    }

    private boolean hasStones(int hand) {
        return hand != 0;
    }

    private void validatePitChoice(Pit selectedPit) {
        if (!selectedPit.belongsTo(turn)) {
            throw new IllegalMoveException(String.format("Pit %d belongs to other player.", selectedPit.getNumber()));
        }
        if (!selectedPit.isNonHomePit()) {
            throw new IllegalMoveException("Can't start the move from Home pit.");
        }
        if (selectedPit.hasNoStones()) {
            throw new IllegalMoveException("Can't start the move from an empty pit.");
        }
    }

    private Pit getPitByNumber(int pitNumber) {
        return board.stream().filter(pit ->  pit.getNumber() == pitNumber)
                .findFirst()
                .orElseThrow();
    }

    private boolean isFinished() {
        final var stonesRemainingOne = board.stream()
                .filter(pit -> pit.belongsTo(playerOne))
                .filter(Pit::isNonHomePit)
                .map(Pit::getStones)
                .reduce(0, Integer::sum);
        final var stonesRemainingTwo = board.stream()
                .filter(pit -> pit.belongsTo(playerTwo))
                .filter(Pit::isNonHomePit)
                .map(Pit::getStones)
                .reduce(0, Integer::sum);
        final var result = Stream.of(stonesRemainingOne, stonesRemainingTwo)
                .anyMatch(quantity -> quantity == 0);
        if (result) {
            summarizeScore(stonesRemainingOne, stonesRemainingTwo);
        }
        return result;
    }

    private void summarizeScore(Integer stonesRemainingOne, Integer stonesRemainingTwo) {
        final var homeOne = board.get(playerOne.getHomePitIndex());
        homeOne.putStones(stonesRemainingOne);

        final var homeTwo = board.get(playerTwo.getHomePitIndex());
        homeTwo.putStones(stonesRemainingTwo);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Player playerA;
        private Player playerB;
        private List<Pit> board;

        public Builder forPlayers(Player playerA, Player playerB) {
            this.playerA = playerA;
            this.playerB = playerB;
            return this;
        }

        public Builder onBoard(List<Pit> board) {
            this.board = board;
            return this;
        }

        public Game build() {
            return new OngoingGame(playerA, playerB, board);
        }
    }
}