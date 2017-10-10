/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.EAO;

import eu.papenhagen.rockpaperscissor.Entities.*;
import eu.papenhagen.rockpaperscissor.Main;
import eu.papenhagen.rockpaperscissor.Service.TournamentLogging;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.LoggerFactory;

/**
 * Here A Methoes for handling the Tournament
 *
 * @author jens.papenhagen
 */
public class TournamentHandler {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(TournamentHandler.class);
    private static TournamentLogging tournierlogger = new TournamentLogging();

    /**
     * Try to buildMatches a tournament grid in console. using a List of Tier
     * Objects for this. Winner on the top and than to the button
     *
     * @param tournament
     */
    public static void displayTournament() {
        //go from last to frist Tier
        for (int i = tournierlogger.getTournament().size() - 1; i >= 0; i--) {
            //build a emptry string witgh 100 spaces
            StringBuilder spaces = new StringBuilder();
            spaces.append(String.join("", Collections.nCopies(200, " ")));
            int maxl = spaces.length();
            //redruce this string every round
            spaces.delete((i * 10) + 30, maxl);
            //output the number of the tier
            System.out.printf("%s\t%s", tournierlogger.getTournament().get(i).getTierId() + 1, spaces.toString());
            //setting the winner on the top
            for (int j = 0; j < tournierlogger.getTournament().get(i).getMatchList().size(); j++) {
                System.out.printf("\t%s", String.format("%s", tournierlogger.getTournament().get(i).getMatchList().get(j).getWinner().getID()));
            }
            //line break
            System.out.println("");
            //adding all "ID vs ID " to gether and plot it
            System.out.printf("%s\t%s", tournierlogger.getTournament().get(i).getTierId() + 1, spaces.toString());
            for (int j = 0; j < tournierlogger.getTournament().get(i).getMatchList().size(); j++) {
                System.out.printf("%s\t", String.format(" %s vs. %s", tournierlogger.getTournament().get(i).getMatchList().get(j).getPlayer1().getID(), 
                        tournierlogger.getTournament().get(i).getMatchList().get(j).getPlayer2().getID()));
            }
            //line break with 200 -´s
            System.out.println("");
            System.out.println(String.join("", Collections.nCopies(200, "-")));
        }
    }

    
    /**
     * Run all the Tier only need a List of Player
     * @param playerList for the starting
     */
      public static void runTiers(List<Player> playerList) {
        //the Executor Service for this tournier
        ExecutorService tournamentexecutor = Executors.newCachedThreadPool();
        boolean firstround = true;
        //calc new matchcount
        Main.setMaxMatches(playerList.size() / 2);
        LOG.debug("maxMatches " + Main.getMaxMatches());
        //run the tier
        for (int tierCounter = 0; tierCounter < Main.getCountOfTiers(); tierCounter++) {
            //building the tier
            Tier tier = new Tier(tierCounter);

            if (firstround) {
                Main.setMaxMatchesInNextTier( Main.getMaxMatches() );
                //shuffle the playerlist
                long seed = System.nanoTime();
                Collections.shuffle(playerList, new Random(seed));
                //set firstorut of false
                firstround = false;
                LOG.debug("First round");
                LOG.debug("maxMatchesInNextTier " + Main.getMaxMatchesInNextTier());
            } else {
                //shuffle symbole for eath tier;
                playerList.forEach((Player p) -> {
                    p.setSymbole(p.getRandomSymbole());
                });
                //giveback the count of max games for this tier
                Main.setMaxMatchesInNextTier( TierHandler.getMaxFightsInTier(Main.getMaxMatchesInNextTier()) );
                LOG.debug("Round nr: " + tierCounter);
            }
            //make a new matchList
            List<Match> matchListForThisTier = new ArrayList<>(Main.getMaxMatchesInNextTier());
            
    
            //build all Matchs in a Callable List and log all match in a List<Match>
            List<List> matchbuild = MatchHandler.buildMatches(Main.getMaxMatchesInNextTier(), playerList, matchListForThisTier);
            //split the both return objects
            List<Callable<Player>> callableList = matchbuild.get(0);
            matchListForThisTier = matchbuild.get(1);

            //build a list of Losers
            int sizeOfLoserlist = matchListForThisTier.size() / 2;
            List<Player> loserList = new ArrayList<>(sizeOfLoserlist);
            //staring the Callable
            try {
                //submit Callable tasks to be executed by thread pool
                //CompletableFuture
                List<Future<Player>> futureList = tournamentexecutor.invokeAll(callableList);

                //adding loser of the fight to the loser List
                for (Future<Player> p : futureList) {
                    if (p.isCancelled()) {
                        LOG.error("error task get canceled");
                    }
                    LOG.debug("the get " + p.get().getName());
                    loserList.add(p.get());
                }
            } catch (InterruptedException | ExecutionException ex) {
                LOG.error(ex.getMessage());
            }

            //remove doubles form the loser list agains threadhandling fails
            List<Player> depdupeCustomers = new ArrayList<>(new LinkedHashSet<>(loserList));
            loserList.clear();
            loserList.addAll(depdupeCustomers);

            //remove the loser from the remainingPlayerList
            playerList.removeAll(loserList);

            //adding the winner into the matchlog by check the losing
            matchListForThisTier.forEach((Match m) -> {
                if (m.getPlayer1().getWon() > m.getPlayer2().getWon()) {
                    m.setWinner(m.getPlayer1());
                } else {
                    m.setWinner(m.getPlayer2());
                }
            });
            //reduce the fightcount
            Main.setMaxMatchesInNextTier(playerList.size());

            //fillup the remainingplayer with Freewin
            //adding FREEWIN odd size of remainingPlayerList
            //not in the last round
            //only as fallback
            if (Main.getMaxMatchesInNextTier() != 1 && playerList.size() % 2 != 0) {
                Player p1 = new Player(Main.getMaxPlayer() + 1, Player.PLAYERCONDITION.FREEWIN);
                p1.setName("FreeWin");
                //add to random postion in the remainingPlayerList
                int ranhdomIndex = new Random().nextInt(playerList.size() - 1);
                playerList.add(ranhdomIndex, p1);
                LOG.debug("added Freewin player");
            }
            //adding the matchlist to this tier
            tier.setMatchList(matchListForThisTier);
            //adding this tier to the tournament (lsit of tiers)
            
            tournierlogger.getTournament().add(tier);
            //log the end of the tier
            LOG.debug("tier finish");
            if (Main.isCalm()) {
                LOG.debug(String.join("", Collections.nCopies(100, "-")));
            } else {
                System.out.println("\n" + String.join("", Collections.nCopies(100, "-")) + "\n");
            }
        }
        //try to politly shutdown the executer
        tournamentexecutor.shutdown();

    }

}
