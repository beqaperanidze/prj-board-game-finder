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


    public List<String> getCategories(Long id) {
        if (gameRepo.findById(id).isPresent())
            return gameRepo.findById(id).get().getCategories();
        return new ArrayList<>();
    }

    public List<Game> getGamesByCategories(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }
        List<Game> result = new ArrayList<>();
        List<Game> allGames;
        allGames = gameRepo.findAll();
        for (Game game : allGames) {
            if (new HashSet<>(game.getCategories()).containsAll(categories))
                result.add(game);
        }
        return result;
    }

    public List<Game> getGamesByCount(int min, int max) {
        return gameRepo.findByMinPlayersLessThanAndMaxPlayersGreaterThan(min + 1, max - 1);
    }

    public List<Game> getGamesByCategoriesAndCount(List<String> categories, int min, int max) {
        List<Game> gamesByCat = getGamesByCategories(categories);
        System.out.println(gamesByCat);
        List<Game> result = new ArrayList<>();
        for (Game game : gamesByCat) {
            if (game.getMinPlayers() <= min && game.getMaxPlayers() >= max) {

                result.add(game);
            }
        }

        return result;
    }


    public Game addGame(Game game) {
        return gameRepo.save(game);
    }

    public List<Game> addMultipleGames(List<Game> games) {
        return gameRepo.saveAll(games);
    }


    public boolean updateGame(long id, Game game) {
        if (gameRepo.findById(id).isPresent()) {
            Game currentGame = gameRepo.findById(id).get();
            currentGame.setName(game.getName());
            currentGame.setDescription(game.getDescription());
            currentGame.setCategories(game.getCategories());
            currentGame.setMinPlayers(game.getMinPlayers());
            currentGame.setMaxPlayers(game.getMaxPlayers());
            gameRepo.save(currentGame);
            return true;
        }
        return false;
    }

    public boolean deleteGame(long id) {
        if (gameRepo.findById(id).isPresent()) {
            gameRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAll() {
        gameRepo.deleteAll();
    }
}
