package com.controller;

import com.exception.GameNotFoundException;
import com.entity.Game;
import com.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable("id") Long id) {
        Optional<Game> game = gameService.getGameById(id);
        return game.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/getCategories/{id}")
    public ResponseEntity<List<String>> getCategories(@PathVariable("id") Long id) {
        List<String> categories = gameService.getCategories(id);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Game>> getGamesByCategories(@RequestParam List<String> categories) {
        List<Game> games = gameService.getGamesByCategories(categories);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/count")
    public ResponseEntity<List<Game>> getGamesByCount(@RequestParam int min, @RequestParam int max) {
        List<Game> games = gameService.getGamesByCount(min, max);
        return ResponseEntity.ok(games);
    }

    @PostMapping("/add")
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Game savedGame = gameService.addGame(game);
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }

    @PostMapping("/addMultiple")
    public ResponseEntity<List<Game>> addMultipleGames(@RequestBody List<Game> games) {
        List<Game> savedGames = gameService.addMultipleGames(games);
        return new ResponseEntity<>(savedGames, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateGame(@PathVariable("id") long id, @RequestBody Game game) throws GameNotFoundException {
        gameService.updateGame(id, game);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") long id) throws GameNotFoundException {
        gameService.deleteGame(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        gameService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
