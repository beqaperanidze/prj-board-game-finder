package com.service;

import com.entity.Game;
import com.repository.GameRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class GameService {
    private final GameRepo gameRepo;

    public GameService(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }


    public List<Game> getAllGames() {
        return gameRepo.findAll();
    }


    public Object getGameById(Long id) {
        return gameRepo.findById(id);
    }

    public List<Game> getGamesByCategories(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }
        List<Game> result = new ArrayList<>();
        List<Game> allGames = new ArrayList<>();
        allGames = gameRepo.findAll();
        for (Game game : allGames) {
            if (new HashSet<>(game.getCategories()).containsAll(categories))
                result.add(game);
        }
        return result;
    }

    public List<String> getCategories(Long id) {
        if (gameRepo.findById(id).isPresent())
            return gameRepo.findById(id).get().getCategories();
        return new ArrayList<String>();
    }

    public List<Game> getGamesByCount(int min, int max) {
        return gameRepo.findByMinPlayersLessThanAndMaxPlayersGreaterThan(min + 1, max - 1);
    }

    public Game addGame(Game game) {
        return gameRepo.save(game);
    }

    public List<Game> addMultipleGames(List<Game> games) {
        return gameRepo.saveAll(games);
    }


}
