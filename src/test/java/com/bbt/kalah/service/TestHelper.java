package com.bbt.kalah.service;

import com.bbt.kalah.model.Player;
import com.bbt.kalah.model.PlayerOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestHelper {

    protected Player playerOne;
    protected Player playerTwo;

    @Spy
    protected BoardProvider boardProvider;
    @Spy
    protected PlayerProvider playerProvider;

    @InjectMocks
    protected GamesService gamesService;

    @BeforeEach
    public void setup() {
        playerOne = new Player(PlayerOrder.FIRST);
        playerTwo = new Player(PlayerOrder.SECOND);

        when(playerProvider.createPlayer(PlayerOrder.FIRST))
                .thenReturn(playerOne);
        when(playerProvider.createPlayer(PlayerOrder.SECOND))
                .thenReturn(playerTwo);
    }

    protected int setupGameWithCustomBoard(int[] boardLayout) {
        setupCustomBoard(boardLayout);
        return gamesService.setupNew();
    }

    private void setupCustomBoard(int[] stoneQuantities) {
        final var preparedBoard = boardProvider.createBoard(playerOne, playerTwo, stoneQuantities);
        when(boardProvider.createBoard(playerOne, playerTwo))
                .thenReturn(preparedBoard);
    }
}
