/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spielwiese.schnickschnack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author jens.papenhagen
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException, TimeoutException, DrawException, ExecutionException, Throwable {
        ExecutorService executorTier = Executors.newCachedThreadPool();

        int maxGames = 50;
        int maxPlayer = maxGames * 2;
        int maxGamesNextTier = 0;
        int anzahlDerTiers = (int) Math.sqrt(maxGames);
        int FreiLosPlayerID = maxPlayer + 3;

        List<Player> playerList = new ArrayList<>(maxPlayer);
        List<Player> remainingPlayer = new ArrayList<>();

        //fill playerList with ints
        for (int i = 1; i <= maxPlayer; i++) {
            Player p1 = new Player(i, Constans.playerStatus.PLAYER.toString());
            playerList.add(p1);
        }

        List<Player> megaplayerList = new ArrayList<>();
        megaplayerList.addAll(playerList);

        //run the tier
        for (int tierCounter = 1; tierCounter < anzahlDerTiers; tierCounter++) {
            //giveback the count of max games for this tier
            if (maxGamesNextTier == 2) {
                maxGamesNextTier = maxGamesNextTier; //lastround
            } else if (maxGamesNextTier % 2 == 0) {
                maxGamesNextTier = maxGamesNextTier / 2;
            } else {
                maxGamesNextTier = (maxGamesNextTier + 1) / 2;
            }

            if (tierCounter == 1) {
                maxGamesNextTier = maxGames;
                Tier tier = new Tier(maxGamesNextTier, playerList); //ersterste Tier.
                Future<List<Player>> result10 = executorTier.submit(tier);
                remainingPlayer = result10.get();
            } else {
                System.out.println("");
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("");

                //shuffle the list
                long seed = System.nanoTime();
                Collections.shuffle(remainingPlayer, new Random(seed));

                Tier tier = new Tier(maxGamesNextTier, remainingPlayer);
                Future<List<Player>> result10 = executorTier.submit(tier);
                remainingPlayer = result10.get();
            }

            //fillup the remainingplayer 
            if (remainingPlayer.size() % 2 != 0) {
                Player p1 = new Player(FreiLosPlayerID, Constans.playerStatus.FREILOS.toString());
                remainingPlayer.add(p1);//adding freilos if size is a odd number
            }
            megaplayerList.addAll(remainingPlayer);
        }
        executorTier.shutdown();

    }

   

}
