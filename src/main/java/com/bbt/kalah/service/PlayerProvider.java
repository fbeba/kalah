package com.bbt.kalah.service;

import com.bbt.kalah.model.Player;
import com.bbt.kalah.model.PlayerOrder;
import org.springframework.stereotype.Service;

@Service
public class PlayerProvider {
    public Player createPlayer(PlayerOrder order) {
        return new Player(order);
    }
}
