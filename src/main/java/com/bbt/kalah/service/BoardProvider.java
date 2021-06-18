package com.bbt.kalah.service;

import com.bbt.kalah.model.Pit;
import com.bbt.kalah.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
class BoardProvider {

    private int[] defaultStoneSetup = new int[]{
            6, 6, 6, 6, 6, 6,   0,
            6, 6, 6, 6, 6, 6,   0
    };

    List<Pit> createBoard(Player playerOne, Player playerTwo) {
        return createBoard(playerOne, playerTwo, defaultStoneSetup);
    }

    List<Pit> createBoard(Player playerOne, Player playerTwo, int[] stoneSetup) {
        final var sideOne = IntStream.rangeClosed(0, playerOne.getHomePitIndex())
                .mapToObj(index -> new Pit(index, playerOne, stoneSetup[index]));
        final var sideTwo = IntStream.rangeClosed(7, playerTwo.getHomePitIndex())
                .mapToObj(index -> new Pit(index, playerTwo, stoneSetup[index]));
        return Stream.concat(sideOne, sideTwo).collect(Collectors.toList());
    }
}
