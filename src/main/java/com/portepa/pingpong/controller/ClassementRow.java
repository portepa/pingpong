package com.portepa.pingpong.controller;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.previous;

@Data
public class ClassementRow {

    public ClassementRow(Player p, List<Game> games) {
        // where games is all games
        this.games = new ArrayList<Game>();
        this.player = p;
        this.nbWin = 0;
        this.nbLose = 0;
        this.totalScore = 0;
        for (Game g: games) {
            // we're adding the game only if it's in the current week
            if (g.getDateTime() != null &&  g.getDateTime().isAfter(LocalDateTime.now().with(previous(SUNDAY)))) {
                if (g.getWinner().equals(p)) {
                    nbWin += 1;
                    totalScore += 3;
                    this.games.add(g);
                } else if (g.getLoser().equals(p)) {
                    nbLose += 1;
                    this.games.add(g);
                }
            }
        }
    }

    Player player;
    List<Game> games;
    int nbWin;
    int nbLose;
    int totalScore;
}
