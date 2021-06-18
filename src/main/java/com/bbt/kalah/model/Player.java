package com.bbt.kalah.model;

public class Player {

    private final int homePitIndex;
    private int score;

    public Player(PlayerOrder order) {
        this.homePitIndex = determineHomePitIndex(order);
        this.score = 0;
    }

    private int determineHomePitIndex(PlayerOrder order) {
        return order.equals(PlayerOrder.FIRST) ? 6 : 13;
    }

    public int getHomePitIndex() {
        return homePitIndex;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int stones) {
        if (score == 0) {
            score = stones;
        }
    }
}
