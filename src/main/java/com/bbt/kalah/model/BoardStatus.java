package com.bbt.kalah.model;

import lombok.Value;

import java.util.Map;

@Value
public class BoardStatus {
    Map<String, String> status;
}
