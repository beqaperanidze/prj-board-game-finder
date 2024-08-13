package com.controller;

import com.entity.Game;
import com.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final GameService gameService;

    public UserController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/allGames")
    public String greeting(Model model) {
        List<Game> games = gameService.getAllGames();
        model.addAttribute("games", games);
        return "allOfTheGames";
    }


}
