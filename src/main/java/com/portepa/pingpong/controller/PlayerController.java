package com.portepa.pingpong.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
class PlayerController {

    PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    private final PlayerRepository repository;

    @GetMapping(value="player")
    public Iterable<Player> getPlayers() {
        return repository.findAll();
    }

    @PostMapping(value="player")
    public Player createPlayer(@RequestBody Player player) {
        System.out.println("HELLO");
        System.out.println(player);
        return repository.save(player);
    }

}