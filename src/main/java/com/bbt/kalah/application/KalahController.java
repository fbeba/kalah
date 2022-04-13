package com.bbt.kalah.application;

import com.bbt.kalah.service.GamesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class KalahController {

    private final GamesService gamesService;

    @PostMapping("/games")
    public ResponseEntity<GameCreatedDto> createGame(HttpServletRequest request) {
        final var gameId = gamesService.setupNew();
        final var uri = getHostURL(request) + "/games/" + gameId;

        return new ResponseEntity<>(new GameCreatedDto(gameId, uri), HttpStatus.CREATED);
    }

    @PutMapping("/games/{id}/pits/{pitNumber}")
    public ResponseEntity<?> move(@PathVariable Integer id,
                                  @PathVariable Integer pitNumber,
                                  HttpServletRequest request) {
        validateInput(id, pitNumber);
        final var url = String.join("/", getHostURL(request), "games", String.valueOf(id));
        final var boardStatus = gamesService.makeMove(id, pitNumber);

        return new ResponseEntity<>(new GameStatusDto(id, url, boardStatus), HttpStatus.OK);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<GameStatusDto> checkGameState(@PathVariable Integer id,
                                                        HttpServletRequest request) {
        final var gameStatus = gamesService.getGameStatus(id);
        final var uri = getHostURL(request) + "/games/" + id;

        return new ResponseEntity<>(new GameStatusDto(id, uri, gameStatus), HttpStatus.OK);
    }

    private void validateInput(Integer id, Integer pitNumber) {
        if (pitNumber < 1 || pitNumber > 14) {
            throw new IllegalArgumentException("Invalid pit number selected: " + pitNumber);
        }
        if (id < 0 || id > 9999) {
            throw new IllegalArgumentException("Invalid game id: " + id);
        }
    }

    private String getHostURL(HttpServletRequest request) {
        return request.getRequestURL().toString().replace(request.getRequestURI(), "");
    }
}
