package com.portepa.pingpong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

class SortByScore implements Comparator<ClassementRow>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(ClassementRow a, ClassementRow b)
    {
        return b.getTotalScore() - a.getTotalScore();
    }
}



@RestController
public class ClassementController {

    ClassementController(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository= playerRepository;
    }

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @GetMapping(value="classement")
    public Map<String, String> getClassement() {

        Iterable<Game> gamesIterable = gameRepository.findAll();
        List<Game> games = new ArrayList<>();
        gamesIterable.iterator().forEachRemaining(games::add);



        Iterable<Player> players = playerRepository.findAll();

        System.out.println("Players:");
        players.forEach(player -> {
            System.out.println(player.getName());
        });


        ArrayList<ClassementRow> allClassementRow = new ArrayList<>();
        for (Player p: players) {
            allClassementRow.add(new ClassementRow(p, games));
        }
        allClassementRow.sort(new SortByScore());


        String classementText = "🏓 **Classement** 🏓 \n";
        for (ClassementRow cr: allClassementRow) {
            int pourcentage_victoire = cr.getNbLose() == 0 ? 100 : cr.getNbWin()/(cr.getNbLose()+cr.getNbWin()*100);
            System.out.println(cr.getPlayer().getName() + ": " + cr.getTotalScore() + "(" + pourcentage_victoire + "%)");
            classementText += "🏓 " + cr.getPlayer().getName() + ": " + cr.getTotalScore() + "pts (" + pourcentage_victoire + "%)\n";
        }

        HashMap<String, String> map = new HashMap<>();

        map.put("text", classementText);
        return map;
    }
}
