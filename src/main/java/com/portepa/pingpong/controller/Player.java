package com.portepa.pingpong.controller;

import javax.persistence.*;

import lombok.Data;

import java.util.List;

@Data
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

 /*   @OneToMany(mappedBy="loser")
    private List<Game> gamesLoser;

    @OneToMany(mappedBy="winner")
    private List<Game> gamesWinner;
*/
    protected Player() {}

    Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}