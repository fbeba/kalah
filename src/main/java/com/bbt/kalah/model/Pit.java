package com.bbt.kalah.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pit {

    private final int index;
    private final Player owner;
    private int stones;

    public int getNumber() {
        return index + 1;
    }

    public int getStones() {
        return stones;
    }

    public boolean belongsTo(Player turn) {
        return owner.equals(turn);
    }

    public boolean isNonHomePit() {
        return index != owner.getHomePitIndex();
    }

    public boolean hasNoStones() {
        return stones == 0;
    }

    public boolean hasOneStone() {
        return stones == 1;
    }

    public int takeStones() {
        var takenStones = stones;
        this.stones = 0;
        return takenStones;
    }

    public int getIndex() {
        return index;
    }

    public void putStones(int quantity) {
        stones += quantity;
    }

    public boolean isStorableForPlayer(Player turn) {
        return this.owner.equals(turn) || isNonHomePit();
    }

    public int findOppositePitIndex() {
        final var pitNumber = getNumber();
        return (pitNumber + 14 - 2 * pitNumber) - 1;
    }
}
