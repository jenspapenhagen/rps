/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public class Main {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        //config area for this tournament
        int maxPlayer = 100;
        int maxMatches = maxPlayer / 2;
        int maxFightInNextTier = 0;
        int countOfTiers = (int) Math.sqrt(maxMatches);
        int FreeWinID = maxPlayer + 3;
        boolean calm = true;

        LOG.info("maxplayer for this tournament " + maxPlayer);
        LOG.info("max Match games for the frist round" + maxMatches);
        LOG.info("maxGamesNextTier " + maxFightInNextTier);
        LOG.info("count of tiers " + countOfTiers);
        LOG.info("ID from the freeWin player " + FreeWinID);
        LOG.info("This Fight is: " + calm);

        ExecutorService turnierround = Executors.newFixedThreadPool(4);

        //build up the tournament
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
        for (int tierCounter = 1; tierCounter <= countOfTiers; tierCounter++) {
            //build list
            List<Callable<Player>> callableList = new LinkedList<>();

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
                //check if there is a next player in the playerlist and 
                //the loserlist is at it max. On single-elimination the loser is have to be smaller than the half remainingPlayer list.
                //
                if (playerListIterator.hasNext() && (loserList.size() * 2) < remainingPlayerList.size()) {
                    //set default match log
                    Match matchlog = new Match(1, 2, 3);

                    //getting the 2 player
                    Player p1 = playerListIterator.next();
                    Player p2 = playerListIterator.next();

                    //adding the right match ID into the matchlog and both player to the matchlog
                    matchlog.setId(matches);
                    matchlog.setPlayer1ID(p1.getPlayerID());
                    matchlog.setPlayer2ID(p2.getPlayerID());

                    callableList.add(new Fight(matches, p1, p2, calm));

                    try {
                        //submit Callable tasks to be executed by thread pool
                        List<Future<Player>> futureList = turnierround.invokeAll(callableList);
                        //adding loser of the fight to the loser List
                        for (Future<Player> p : futureList) {
                            //there for a new Player Object have to be inis.
                            Player newPlayer = p.get();
                            //only add to list if not null
                            if (newPlayer != null) {
                                loserList.add(newPlayer);
                            }

                        }
                        //clean the loser list
                        List<Player> depdupeCustomers = new ArrayList<>(new LinkedHashSet<>(loserList));
                        loserList.clear();
                        loserList.addAll(depdupeCustomers);

                        //adding the winner into the matchlog
                        if (matchlog.getPlayer1ID() == loserList.get(matches).getPlayerID()) {
                            matchlog.setWinnerID(matchlog.getPlayer2ID());
                        } else {
                            matchlog.setWinnerID(matchlog.getPlayer1ID());
                        }

                    } catch (IndexOutOfBoundsException | InterruptedException | ExecutionException ex) {
                        LOG.error(ex.getMessage());
                    }

                    //add this matchlog to the matchlist
                    matchListForThisTier.add(matchlog);

                }
            }
            LOG.debug("tier finish");
            if (calm) {
                LOG.debug("-------------------------------------------------------------------------------");
            } else {
                System.out.println("");
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("");
            }

            //renove the loser from the remainingPlayerList
            remainingPlayerList.removeAll(loserList);

            //reduce the fightcount
            maxFightInNextTier = remainingPlayerList.size();

            //fillup the remainingplayer with Freewin
            //adding FREEWIN odd size of remainingPlayerList
            //not in the last round
            if (maxFightInNextTier != 1 && remainingPlayerList.size() % 2 != 0) {
                Player p1 = new Player(FreeWinID, Enums.Playercondition.FREEWIN);
                remainingPlayerList.add(p1);
                LOG.debug("added Freewin player");
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
     * @param numbersOfFights
     * @return maxfights for next tier
     */
    public static int getMaxFightsInTier(int numbersOfFights) {
        int output;
        if (numbersOfFights == 2) {
            //if onyl 2 player are left
            output = numbersOfFights; //lastround
        } else if (numbersOfFights % 2 == 0) {
            //if max games count is even  
            output = numbersOfFights / 2;
        } else {
            //if odd, add one match number befor half the numbers
            output = (numbersOfFights + 1) / 2;
        }

        return output;
    }

    /**
     * Try to build a tournament grid in console. using a List of Tier Objects
     * for this
     *
     * @param tournament
     */
    public static void displayTournament(List<Tier> tournament) {
        for (int i = tournament.size() - 1; i >= 0; i--) {

            //build a emptry string witgh 100 spaces
            StringBuilder spaces = new StringBuilder();
            spaces.append(String.join("", Collections.nCopies(100, " ")));

            int maxl = spaces.length();
            //redruce this string every round
            spaces.delete((i * 10) + 30, maxl);

            //output the number of the tier
            System.out.printf("%s\t%s", tournament.get(i).getTierId(), spaces.toString());

            //setting the winner on the top
            for (int j = 0; j < tournament.get(i).getMatchList().size(); j++) {
                System.out.printf("\t%s", String.format("%s", tournament.get(i).getMatchList().get(j).getWinnerID()));
            }

            //line break
            System.out.println("");
            //adding all "ID vs ID " to gether and plot it
            System.out.printf("%s\t%s", tournament.get(i).getTierId(), spaces.toString());
            for (int j = 0; j < tournament.get(i).getMatchList().size(); j++) {
                System.out.printf("%s\t", String.format(" %s vs.%s", tournament.get(i).getMatchList().get(j).getPlayer1ID(), tournament.get(i).getMatchList().get(j).getPlayer2ID()));
            }

            //line break with 200 -´s
            System.out.println("");
            System.out.println(String.join("", Collections.nCopies(200, "-")));
        }

    }

}
