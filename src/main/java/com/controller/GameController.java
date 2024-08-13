package com.controller;

import com.entity.Game;
import com.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
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
        Game game = (Game) gameService.getGameById(id);
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Game>> getGamesByCategories(@RequestParam List<String> categories) {
        List<Game> games = gameService.getGamesByCategories(categories);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/getCategories/{id}")
    public ResponseEntity<List<String>> getCategories(@PathVariable("id") Long id) {
        List<String> categories = gameService.getCategories(id);
        return ResponseEntity.ok(categories);
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
    public ResponseEntity<Game> updateGame(@PathVariable("id") long id, @RequestBody Game game) {
        boolean updated = gameService.updateGame(id, game);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable("id") long id) {
        boolean deleted = gameService.deleteGame(id);
        if (deleted) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Game> deleteAll() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
