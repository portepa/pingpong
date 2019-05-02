package com.portepa.pingpong.controller;

// import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

     Player findByName(String name);
}