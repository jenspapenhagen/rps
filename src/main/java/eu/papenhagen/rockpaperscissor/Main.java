/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import eu.papenhagen.rockpaperscissor.EAO.PlayerHandler;
import eu.papenhagen.rockpaperscissor.EAO.MatchHandler;
import eu.papenhagen.rockpaperscissor.Entities.*;
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
import eu.papenhagen.rockpaperscissor.EAO.TierHandler;
import eu.papenhagen.rockpaperscissor.EAO.TournamentHandler;
import lombok.*;

/**
 *
 * @author jens.papenhagen
 */
public class Main {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);

    @Getter
    private static int bestOf;
    @Getter
    private static boolean calm;
    @Getter
    private static int maxPlayer;
    @Getter
    private static int maxMatches;
    @Getter
    private static int maxMatchesInNextTier;
    @Getter
    private static int countOfTiers;

    public static void main(String[] args) {
        //config area for this tournament
        //there are 8/16/32/64/128 tournaments please select a number near this
        maxPlayer = 100;
        calm = true;
        bestOf = 5;

        //STOP CHANGING HERE
        int firstID = 1;
        countOfTiers = TierHandler.calulateMaxTiers(maxPlayer);
        maxMatches = maxPlayer / 2;
        maxMatchesInNextTier = maxMatches;

        LOG.debug("maxplayer for this tournament " + maxPlayer);
        LOG.debug("max Match games for the frist round " + maxMatches);
        LOG.debug("maxGamesNextTier " + maxMatchesInNextTier);
        LOG.debug("count of tiers " + countOfTiers);

        LOG.debug("This Fight is calm: " + calm);

        //build up the tournament
        List<Tier> tournament = new ArrayList<>(countOfTiers);

        //build moving list for all the player
        List<Player> remainingPlayerList = new ArrayList<>(maxPlayer);

        //build up the PlayerList
        remainingPlayerList = PlayerHandler.getListOfPlayerWithCondition(maxPlayer, firstID, Player.PlayerCondition.PLAYER);

        //give next bigger amount of player
        int missingPlayer = PlayerHandler.nextBiggerPlayerCount(maxPlayer) - remainingPlayerList.size();

        LOG.debug("missingPlayer " + missingPlayer);
        LOG.debug("nextBiggerPlayerCount(maxPlayer) " + PlayerHandler.nextBiggerPlayerCount(maxPlayer));
        LOG.debug("remainingPlayerList size " + remainingPlayerList.size());

        //adding FreePlayer to the List in the first round 
        List<Player> missingPlayerList = PlayerHandler.getListOfPlayerWithCondition(missingPlayer, (maxPlayer + 1), Player.PlayerCondition.FREEWIN);
        remainingPlayerList.addAll(missingPlayerList);
        LOG.debug("added Freewin player");

        //run Tiers
        List<Tier> listOfTier = runTiers(remainingPlayerList, tournament);

        //display in better cli
        TournamentHandler.displayTournament(listOfTier);

        //export to JSON
        //saveToJson(listOfTier);
    }

    private static List<Tier> runTiers(List<Player> playerList, List<Tier> tournament) {

        //the Executor Service for this tournier
        ExecutorService tournamentexecutor = Executors.newCachedThreadPool();

        boolean firstround = true;

        //calc new matchcount
        maxMatches = playerList.size() / 2;

        LOG.debug("maxMatches " + maxMatches);

        //run the tier
        for (int tierCounter = 0; tierCounter < countOfTiers; tierCounter++) {

            //building the tier
            Tier tier = new Tier(tierCounter);

            //giveback the count of max games for this tier
            maxMatchesInNextTier = TierHandler.getMaxFightsInTier(maxMatchesInNextTier);
            LOG.debug("maxMatchesInNextTier " + maxMatchesInNextTier);

            if (firstround) {
                maxMatchesInNextTier = maxMatches;
                //shuffle the playerlist
                long seed = System.nanoTime();
                Collections.shuffle(playerList, new Random(seed));
                //set firstorut of false
                firstround = false;
                LOG.debug("First round");
                LOG.debug("maxMatchesInNextTier " + maxMatchesInNextTier);
            } else {
                //shuffle symbole for eath tier;
                playerList.forEach((p) -> {
                    p.setSymbole(p.getRandomSymbole());
                });
                LOG.debug("Round nr: " + tierCounter);
            }

            //make a new matchList
            List<Match> matchListForThisTier = new ArrayList<>(maxMatchesInNextTier);
            LOG.debug("matchListForThisTier size before " + matchListForThisTier.size());

            //build all Matchs in a Callable List and log all match in a List<Match> 
            List<List> matchbuild = MatchHandler.buildMatches(maxMatchesInNextTier, playerList, matchListForThisTier);

            //split the both return objects 
            List<Callable<Player>> callableList = matchbuild.get(0);
            matchListForThisTier = matchbuild.get(1);
            LOG.debug("callableList size after fwilling with match " + callableList.size());
            LOG.debug("matchListForThisTier size afther filling with match " + matchListForThisTier.size());

            //build a list of Losers
            int sizeOfLoserlist = (matchListForThisTier.size() / 2);
            LOG.debug("sizeOfLoserlist " + sizeOfLoserlist);
            List<Player> loserList = new ArrayList<>();
            LOG.debug("loserList size  afther filling with match " + loserList.size());

            //staring the Callable
            try {
                //submit Callable tasks to be executed by thread pool
                //CompletableFuture
                List<Future<Player>> futureList = tournamentexecutor.invokeAll(callableList);
                LOG.debug("size of futureList" + futureList.size());
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

            LOG.debug("loserList size after run " + loserList.size());
            //remove doubles form the loser list agains threadhandling fails
            List<Player> depdupeCustomers = new ArrayList<>(new LinkedHashSet<>(loserList));
            loserList.clear();
            loserList.addAll(depdupeCustomers);
            LOG.debug("loserList size after remove doubles " + loserList.size());

            //remove the loser from the remainingPlayerList
            playerList.removeAll(loserList);
            LOG.debug("remainingPlayerList size after " + playerList.size());

            //adding the winner into the matchlog by check the losing
            matchListForThisTier.forEach((m) -> {
                if (m.getPlayer1().getWon() > m.getPlayer2().getWon()) {
                    m.setWinner(m.getPlayer1());
                } else {
                    m.setWinner(m.getPlayer2());
                }
            });

            //reduce the fightcount
            maxMatchesInNextTier = playerList.size();

            //fillup the remainingplayer with Freewin
            //adding FREEWIN odd size of remainingPlayerList
            //not in the last round
            //only as fallback
            if (maxMatchesInNextTier != 1 && playerList.size() % 2 != 0) {
                Player p1 = new Player((maxPlayer + 1), Player.PlayerCondition.FREEWIN);
                p1.setName("FreeWin");

                //add to random postion in the remainingPlayerList
                int ranhdomIndex = new Random().nextInt(playerList.size() - 1);
                playerList.add(ranhdomIndex, p1);
                LOG.debug("added Freewin player");
            }

            //adding the matchlist to this tier
            tier.setMatchList(matchListForThisTier);

            //adding this tier to the tournament (lsit of tiers)
            tournament.add(tier);

            //log the end of the tier
            LOG.debug("tier finish");
            if (calm) {
                LOG.debug(String.join("", Collections.nCopies(100, "-")));
            } else {
                System.out.println("\n" + String.join("", Collections.nCopies(100, "-")) + "\n");
            }
        }

        //try to politly shutdown the executer
        tournamentexecutor.shutdown();

        return tournament;

    }

}
