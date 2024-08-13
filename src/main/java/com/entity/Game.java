package com.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int minPlayers;
    private int maxPlayers;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> categories;
}
