package com.portepa.pingpong.controller;

// import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {

    // List<Author> findByLastName(String lastName);
}