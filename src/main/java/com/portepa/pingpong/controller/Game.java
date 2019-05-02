package com.portepa.pingpong.controller;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

import javax.persistence.*;

@Data
@Entity
class Game {
    protected Game() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "winner_id")
    private Player winner;

    @ManyToOne()
    @JoinColumn(name = "loser_id")
    private Player loser;

    private LocalDateTime dateTime;

    Game(Player winner, Player loser, int scoreWinner, int scoreLoser) {
        this.winner = winner;
        this.loser = loser;
        this.scoreWinner = scoreWinner;
        this.scoreLoser= scoreLoser;
        this.dateTime = LocalDateTime.now();
    }

    private int scoreWinner;
    private int scoreLoser;
    public Player getWinner() { return winner; }

    public Player getLoser() { return loser; }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setLoser(Player loser) { this.loser = loser; }
}
