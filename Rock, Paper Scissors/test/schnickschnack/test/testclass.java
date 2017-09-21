/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import schnickschnack.Enums;
import schnickschnack.Match;
import schnickschnack.Player;

/**
 *
 * @author jens.papenhagen
 */
public class testclass {

    public static void main(String[] args) throws Exception {

        //runden test
//        Enum output = new Rounds(Enums.SYMBOLE.SCISSOR, Enums.SYMBOLE.PAPER).fightround();
//        System.out.println(" Ausgabe: "+ output);
//        
        //Match erspielen
        Match game;
        Player p1 = new Player(1, Enums.Playercondition.PLAYER);
        Player p2 = new Player(2, Enums.Playercondition.PLAYER);
        game = new Match(1, p1, p2);

        //tier test
//        ExecutorService tierExecuter = Executors.newFixedThreadPool(3);
//        int maxGames = 50;
//        int maxPlayer = maxGames * 2;
//        List<Player> playerList = new ArrayList<>();
//
//        //fill playerList with ints
//        for (int i = 1; i <= maxPlayer; i++) {
//            Player p3 = new Player(i, Enums.playerStatus.PLAYER.toString());
//            playerList.add(p3);
//        }
//        //fill the gamesList
//        List<Player> loserList = new ArrayList<>(maxGames);
//        int erste = 1;
//
//        for (int matches = 1; matches < maxGames; matches++) {
//            Game game;
//            if (matches == 1) {
//                game = new Game(matches, playerList.get(matches), playerList.get(matches + 1));
//            } else {
//                game = new Game(matches, playerList.get(matches + erste), playerList.get(matches + erste + 1));
//                erste++;
//            }
//
//            Future<Player> loser = tierExecuter.submit(game);
//            loserList.add(loser.get());
//        }
//        tierExecuter.shutdown();
//        
//        loserList.forEach((player) -> {
//            System.out.println("PlayerID: " + player.getPlayerID() + " PlayerName: " + player.getPlayerName());
//        });
//        
//        
//        System.out.println("Voher");
//        playerList.forEach((player) -> {
//            System.out.println("PlayerID: " + player.getPlayerID() + " PlayerName: " + player.getPlayerName());
//        });
//        
//        //remove the loser aus the playerList
//        if (!loserList.isEmpty() && !playerList.isEmpty()) {
//            playerList.removeAll(loserList);
//        } else {
//            System.out.println("List is emptry");
//        }
//
//        System.out.println("Ausgabe Gamerlist nachher");
//        playerList.forEach((player) -> {
//            System.out.println("PlayerID: " + player.getPlayerID() + " PlayerName: " + player.getPlayerName());
//        });
//
//        int maxGamesNextTier = 0;
//        int anzahlDerTiers = (int) Math.sqrt(maxGames);
//        int FreiLosPlayerID = maxPlayer + 3;
//        List<Player> otherParticipants = new ArrayList<>();
//
//        for (int tierCounter = 1; tierCounter <= anzahlDerTiers; tierCounter++) {
//            Tier tier = null;
//            //giveback the count of max games for this tier
//
//            if (maxGamesNextTier == 2) {
//                maxGamesNextTier = maxGamesNextTier; //lastround
//            } else if (maxGamesNextTier % 2 == 0) {
//                maxGamesNextTier = maxGamesNextTier / 2;
//            } else {
//                maxGamesNextTier = (maxGamesNextTier + 1) / 2;
//            }
//
//            if (tierCounter == 1) {
//                ExecutorService es = Executors.newSingleThreadExecutor();
//                Future result10 = null;
//                maxGamesNextTier = maxGames;
//
//                tier = new Tier(maxGamesNextTier, playerList); //ersterste Tier.
//                result10 = es.submit(tier);
//
//                otherParticipants = (List<Player>) result10.get();
//                Thread.sleep(3000);
//
//                es.shutdown();
//            } else {
//                ExecutorService es = Executors.newSingleThreadExecutor();
//                Future result10 = null;
//
//                if (otherParticipants.size() % 2 != 0) {
//                    Player p4 = new Player(FreiLosPlayerID, Enums.playerStatus.FREILOS.toString());
//                    otherParticipants.add(p4);//adding freilos if size is a odd number
//                }
//
//                Future result11 = null;
//                Tier nexttier = new Tier(maxGamesNextTier, otherParticipants);
//                result11 = es.submit(nexttier);
//
//                otherParticipants = (List<Player>) result11.get();
//                Thread.sleep(3000);
//
//                System.out.println("Ausgabe Gamerlist nachher");
//                otherParticipants.forEach((player) -> {
//                    System.out.println("PlayerID: " + player.getPlayerID() + " PlayerName: " + player.getPlayerName());
//                });
//
//                es.shutdown();
//            }
//            System.out.println();
//            System.out.println("" + maxGamesNextTier);
//        }
//    }
//    }
    }
}
