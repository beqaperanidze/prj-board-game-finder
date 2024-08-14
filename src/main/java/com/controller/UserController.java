package com.controller;

import com.entity.Game;
import com.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j

public class UserController {

    private final GameService gameService;

    public UserController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("greeting")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/allGames")
    public String allGames(Model model) {
        List<Game> games = gameService.getAllGames();
        model.addAttribute("games", games);
        return "allOfTheGames";
    }

    @GetMapping("/search")
    public String search(Model model) {
        return "search";
    }


    @GetMapping("/find_your_game")
    public String filteredGames(@RequestParam(required = false) List<String> categories,
                                @RequestParam(required = false) int min,
                                @RequestParam(required = false) int max,
                                Model model) {
        List<Game> games = gameService.getGamesByCategoriesAndCount(categories, min, max);
        model.addAttribute("games", games);
        return "allOfTheGames";
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
}
