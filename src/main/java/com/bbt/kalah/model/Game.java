package com.bbt.kalah.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Game {

    protected final int id;
    protected final List<Pit> board;
    protected final Player playerOne;
    protected final Player playerTwo;
    protected Player turn;
    protected GameState state;

    public abstract Game makeMove(int startingPitNumber);

    public abstract String getScore();

    public BoardStatus getBoardStatus() {
        final var boardStatus = board.stream()
                .collect(Collectors.toMap(
                        pit -> String.valueOf(pit.getNumber()),
                        pit -> String.valueOf(pit.getStones()),
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        return new BoardStatus(boardStatus);
    }

    public int getId() {
        return id;
    }

    public GameState getState() {
        return state;
    }

    protected Game(Player playerA, Player playerB, List<Pit> board) {
        this.id = (int) (Math.random() * 10000) % 10000;
        this.playerOne = playerA;
        this.playerTwo = playerB;
        this.board = board;
        this.turn = playerOne;
        this.state = GameState.ONGOING;
    }

    protected Game(Game that) {
        this.id = that.id;
        this.playerOne = that.playerOne;
        this.playerTwo = that.playerTwo;
        this.board = that.board;
        this.turn = that.turn;
        this.state = GameState.FINISHED;
    }
}
