package com.repository;

import com.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends JpaRepository<Game, Long> {

    List<Game> findByMinPlayersLessThanAndMaxPlayersGreaterThan(int min, int max);
}
