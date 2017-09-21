/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

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
import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public class Main {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException, TimeoutException, ExecutionException, Throwable {
        ExecutorService turnierround = Executors.newCachedThreadPool();

        int maxPlayer = 100;
        int maxMatches = maxPlayer / 2;
        int maxMatchesNextTier = 0;
        int countOfTiers = (int) Math.sqrt(maxMatches);
        int FreeWinID = maxPlayer + 3;

        LOG.info("maxplayer " + maxPlayer);
        LOG.info("maxGames " + maxMatches);
        LOG.info("maxGamesNextTier " + maxMatchesNextTier);
        LOG.info("anzahlDerTiers " + countOfTiers);
        LOG.info("FreiLosPlayerID " + FreeWinID);

        List<Player> rawPlayerList = new ArrayList<>(maxPlayer);
        List<Player> remainingPlayerList = new ArrayList<>();

        //fill playerList with ints
        for (int i = 1; i <= maxPlayer; i++) {
            Player p1 = new Player(i, Enums.Playercondition.PLAYER);
            rawPlayerList.add(p1);
        }

        //build a moving list
        List<Player> playerListForTiers = new ArrayList<>();
        playerListForTiers.addAll(rawPlayerList);

        //run the tier
        for (int tierCounter = 1; tierCounter < countOfTiers; tierCounter++) {
            //giveback the count of max games for this tier
            if (maxMatchesNextTier == 2) {
                maxMatchesNextTier = maxMatchesNextTier; //lastround
            } else if (maxMatchesNextTier % 2 == 0) {
                maxMatchesNextTier = maxMatchesNextTier / 2;
            } else {
                maxMatchesNextTier = (maxMatchesNextTier + 1) / 2;
            }

            if (tierCounter == 1) {
                maxMatchesNextTier = maxMatches;
                Tier tier = new Tier(maxMatchesNextTier, rawPlayerList); //ersterste Tier.
                Future<List<Player>> result10 = turnierround.submit(tier);
                remainingPlayerList = result10.get();
            } else {
                System.out.println("");
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("");

                //shuffle the list
                long seed = System.nanoTime();
                Collections.shuffle(remainingPlayerList, new Random(seed));
                //todo shuffle symbole
                
                
                

                Tier tier = new Tier(maxMatchesNextTier, remainingPlayerList);
                Future<List<Player>> result10 = turnierround.submit(tier);
                remainingPlayerList = result10.get();
            }

            //fillup the remainingplayer with Freewin
            if (remainingPlayerList.size() % 2 != 0) {
                Player p1 = new Player(FreeWinID, Enums.Playercondition.FREEWIN);
                remainingPlayerList.add(p1);//adding freilos if size is a odd number
            }
            playerListForTiers.addAll(remainingPlayerList);
        }
        turnierround.shutdown();

    }

}
