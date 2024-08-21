package com.service;

import com.exception.GameNotFoundException;
import com.entity.Game;
import com.repository.GameRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepo gameRepo;

    public GameService(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

    public List<Game> getAllGames() {
        return gameRepo.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepo.findById(id);
    }

    public List<String> getCategories(Long id) {
        return gameRepo.findById(id).map(Game::getCategories).orElseGet(ArrayList::new);
    }

    public List<Game> getGamesByCategories(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }
        List<Game> result = new ArrayList<>();
        List<Game> allGames = gameRepo.findAll();

        for (Game game : allGames) {
            if (new HashSet<>(game.getCategories()).containsAll(categories)) {
                result.add(game);
            }
        }
        return result;
    }

    public List<Game> getGamesByCount(int min, int max) {
        return gameRepo.findByMinPlayersLessThanAndMaxPlayersGreaterThan(min + 1, max - 1);
    }

    public List<Game> getGamesByCategoriesAndCount(List<String> categories, int min, int max) {
        List<Game> gamesByCat = getGamesByCategories(categories);
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

    public void updateGame(long id, Game game) throws GameNotFoundException {
        Optional<Game> optionalGame = gameRepo.findById(id);
        if (optionalGame.isPresent()) {
            Game currentGame = optionalGame.get();
            currentGame.setName(game.getName());
            currentGame.setDescription(game.getDescription());
            currentGame.setCategories(game.getCategories());
            currentGame.setMinPlayers(game.getMinPlayers());
            currentGame.setMaxPlayers(game.getMaxPlayers());
            gameRepo.save(currentGame);
            return;
        }
        throw new GameNotFoundException("Game with ID: " + id + "not found.");
    }

    public void deleteGame(long id) throws GameNotFoundException {
        Optional<Game> currGame = gameRepo.findById(id);
        if (currGame.isEmpty()) {
            throw new GameNotFoundException("Game with ID: " + id + "not found.");
        } else {
            gameRepo.deleteById(id);
        }
    }

    public void deleteAll() {
        gameRepo.deleteAll();
    }
}
