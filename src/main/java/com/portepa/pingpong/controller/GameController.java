package com.portepa.pingpong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
class GameController {

    GameController(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository= playerRepository;
    }

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @GetMapping(value="game")
    public Iterable<Game> getGames() {
        return gameRepository.findAll();
    }

    @PostMapping(value="game")
    public Map<String, String> createGame(WebRequest request) throws IOException {
//        HashMap myMap = new HashMap<String, String>();

//        ObjectMapper objectMapper = new ObjectMapper();
//        myMap = objectMapper.readValue(test.getBytes(), HashMap.class);
//        System.out.println(test);
        String[] cmd = request.getParameter("text").toLowerCase().split(" ");
        System.out.println("Command:");
        for (String item : cmd
        ) {
            System.out.println(item);
        }

        if (cmd.length == 4) { // it means we are adding a new game
            Player p1 = playerRepository.findByName(cmd[0]);
            Player p2 = playerRepository.findByName(cmd[3]);


            if (p1 == null) {
                System.out.println("Creating player 1");
                p1 = new Player(cmd[0]);
                playerRepository.save(p1);
            }
            if (p2 == null) {
                System.out.println("Creating player 2");
                p2 = new Player(cmd[3]);
                playerRepository.save(p2);
            }


            int score1 = Integer.parseInt(cmd[1]);
            int score2 = Integer.parseInt(cmd[2]);

            System.out.println(score1);
            System.out.println(score2);
            Game game = null;
            if (score1 > score2) {
                game = new Game(p1, p2, score1, score2);
                gameRepository.save(game);
            } else if (score2 > score1) {
                game = new Game(p2, p1, score1, score2);
                gameRepository.save(game);
            } else {
                // match nul
            }

            HashMap<String, String> map = new HashMap<>();
            map.put("response_type", "in_channel");
            map.put("text", " 🏓 Game successfully added! (ps: " + game.getLoser().getName() + " try to improve for next time please 😂)");
            return map;
        } else if (cmd.length == 1 && cmd[0].equals("classement")) { // we are asking classement
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
                if (cr.getNbLose() + cr.getNbWin() > 0) {
                    int total = cr.getNbLose()+cr.getNbWin();
                    System.out.println("TOTAL: " + total);
                    int pourcentage_victoire = cr.getTotalScore() == 0 ? 0 : (int) (((float) cr.getNbWin()/total)*100);
                    System.out.println(cr.getPlayer().getName() + ": " + cr.getTotalScore() + "(" + pourcentage_victoire + "%)");
                    classementText += "🏓 " + cr.getPlayer().getName() + ": " + cr.getTotalScore() + "pts (" + pourcentage_victoire + "%)\n";   
                }
            }

            HashMap<String, String> map = new HashMap<>();


            map.put("response_type", "in_channel");
            map.put("text", classementText);
            return map;
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("text", "😰 Commande inconnue");
            return map;
        }
    }


}