/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public class Main {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException, TimeoutException, ExecutionException, Throwable {
        ExecutorService turnierround = Executors.newFixedThreadPool(4);

        int maxPlayer = 100;
        int maxMatches = maxPlayer / 2;
        int maxFightInNextTier = 0;
        int countOfTiers = (int) Math.sqrt(maxMatches);
        int FreeWinID = maxPlayer + 3;

        LOG.info("maxplayer for this tournament " + maxPlayer);
        LOG.info("max Match games for the frist round" + maxMatches);
        LOG.info("maxGamesNextTier " + maxFightInNextTier);
        LOG.info("count of tiers " + countOfTiers);
        LOG.info("ID from the freeWin player " + FreeWinID);

        List<Tier> tournament = new ArrayList<>(countOfTiers);

        //build two moving list
        List<Player> remainingPlayerList = new ArrayList<>();
        List<Player> playerListForTier = new ArrayList<>();

        //build up the rawPlayerList
        for (int i = 1; i <= maxPlayer; i++) {
            Player p1 = new Player(i, Enums.Playercondition.PLAYER);
            playerListForTier.add(p1);
        }

        //run the tier
        for (int tierCounter = 1; tierCounter < countOfTiers; tierCounter++) {
            //building the tier
            Tier tier = new Tier(tierCounter);

            //make a new matchList
            List<Match> matchListForThisTier = new ArrayList<>();

            //giveback the count of max games for this tier
            maxFightInNextTier = getMaxFightsInTier(maxFightInNextTier);

            if (tierCounter == 1) {
                //first round
                LOG.debug("first round");
                maxFightInNextTier = maxMatches;
                remainingPlayerList.addAll(playerListForTier);
            } else {
                //shuffle the playerlist
                long seed = System.nanoTime();
                Collections.shuffle(remainingPlayerList, new Random(seed));
                //shuffle symbole;
                remainingPlayerList.forEach((p) -> {
                    p.setPlayerSymbole(p.getRandomSymbole());
                });
            }
            LOG.debug("maxFightInNextTier" + maxFightInNextTier);

            List<Player> loserList = new ArrayList<>(maxFightInNextTier);
            Iterator<Player> playerListIterator = remainingPlayerList.iterator();
            
            LOG.debug("lets fight");
            for (int matches = 1; matches <= maxFightInNextTier; matches++) {
                //set default match log
                Match matchlog = new Match(1, 2, 3);

                if (playerListIterator.hasNext()) {
                    Player p1 = playerListIterator.next();
                    Player p2 = playerListIterator.next();
                    //start the fight
                    Fight fight = new Fight(matches, p1, p2);

                    //adding both player to the matchlog
                    matchlog.setPlayer1ID(p1.getPlayerID());
                    matchlog.setPlayer2ID(p2.getPlayerID());

                    //adding loser of the fight to the loser List
                    loserList.add(turnierround.submit(fight).get());
                }

                //adding the winner into the matchlog
                if (matchlog.getPlayer1ID() == loserList.get(matches - 1).getPlayerID()) {
                    matchlog.setWinnerID(matchlog.getPlayer1ID());
                } else {
                    matchlog.setWinnerID(matchlog.getPlayer2ID());
                }
                //adding the right match ID into the matchlog
                matchlog.setId(matches);
                //add this matchlog to the matchlist
                matchListForThisTier.add(matchlog);
            }
            LOG.debug("tier finish");
            System.out.println("");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("");

            //renove the loser from the remainingPlayerList
            remainingPlayerList.removeAll(loserList);

            //reduce the fightcount
            maxFightInNextTier = remainingPlayerList.size();

            //fillup the remainingplayer with Freewin
            //adding FREEWIN odd size of remainingPlayerList
            if (remainingPlayerList.size() % 2 != 0) {
                Player p1 = new Player(FreeWinID, Enums.Playercondition.FREEWIN);
                remainingPlayerList.add(p1);
            }

            //adding the matchlist to this tier
            tier.setMatchList(matchListForThisTier);

            //adding this tier to the tournament (lsit of tiers)
            tournament.add(tier);

        }
        turnierround.shutdown();

        //display in better cli
        displayTournament(tournament);
    }

    /**
     * giveback the count of max games for this tier. onyl 2 fights are left do
     * not half the fightcount on odd fights add one fight befor half the
     * fightcount
     *
     * @param fights
     * @return maxfights for next tier
     */
    public static int getMaxFightsInTier(int fights) {
        int output;
        if (fights == 2) {
            //if onyl 2 player are left
            output = fights; //lastround
        } else if (fights % 2 == 0) {
            //if max games count is even  
            output = fights / 2;
        } else {
            //if odd, add one match number befor half the numbers
            output = (fights + 1) / 2;
        }

        return output;
    }

    public static void displayTournament(List<Tier> tournament) {
        for (Tier t : tournament) {
            System.out.println("");
            System.out.println("Tier: " + t.getTierId());
            for (Match m : t.getMatchList()) {
                System.out.println("Match: " + m.getId());
                System.out.println("Player1: " + m.getPlayer1ID());
                System.out.println("Player2: " + m.getPlayer2ID());
                System.out.println("and the Winner is: " + m.getWinnerID());

            }

        }

    }

}
