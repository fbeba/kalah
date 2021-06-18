package com.bbt.kalah.application;

import com.bbt.kalah.model.BoardStatus;
import lombok.Value;

import java.util.Map;

@Value
public class GameStatusDto {

    int id;
    String url;
    Map<String, String> status;

    public GameStatusDto(Integer id, String url, BoardStatus boardStatus) {
        this.id = id;
        this.url = url;
        this.status = boardStatus.getStatus();
    }
}
