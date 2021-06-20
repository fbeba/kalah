package com.bbt.kalah.model;

public class Player {

    private final int homePitIndex;

    public Player(PlayerOrder order) {
        this.homePitIndex = determineHomePitIndex(order);
    }

    private int determineHomePitIndex(PlayerOrder order) {
        return order.equals(PlayerOrder.FIRST) ? 6 : 13;
    }

    public int getHomePitIndex() {
        return homePitIndex;
    }
}
