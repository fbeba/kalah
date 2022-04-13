package com.bbt.kalah.model;

public enum Player {

    FIRST(6), SECOND(13);

    private final int homePitIndex;

    Player(int homePitIndex) {
        this.homePitIndex = homePitIndex;
    }

    public int getHomePitIndex() {
        return homePitIndex;
    }
}
