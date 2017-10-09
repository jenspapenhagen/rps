/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static int FreeWinID;
    
    @Getter
    private static List<Match> matchListForThisTier;

    @Getter
    @Setter
    private static int maxPlayer;

    @Getter
    @Setter
    private static int maxMatches;

    @Getter
    @Setter
    private static int maxMatchesInNextTier;

    @Getter
    @Setter
    private static int countOfTiers;

    public static void main(String[] args) {
        //config area for this tournament
        //there are 8/16/32/64/128 tournaments please select a number near this
        maxPlayer = 64;
        maxMatches = maxPlayer / 2;
        maxMatchesInNextTier = maxMatches;
        FreeWinID = maxPlayer + 3;
        calm = false;
        bestOf = 5;

        //calculate the maxTiers
        int maxLevle = 1;
        while (maxPlayer / 2 != 1) {
            maxLevle++;
            maxPlayer = maxPlayer / 2;
            if (maxPlayer % 2 != 0 && maxPlayer > 1) {
                maxPlayer = maxPlayer + 1;
            }
        }
        countOfTiers = maxLevle;

        //change maxplayer back to orgianl size
        maxPlayer = maxMatches * 2;

        System.out.println("maxplayer for this tournament " + maxPlayer);
        System.out.println("max Match games for the frist round " + maxMatches);
        System.out.println("maxGamesNextTier " + maxMatchesInNextTier);
        System.out.println("count of tiers " + countOfTiers);
        System.out.println("ID from the freeWin player " + FreeWinID);
        System.out.println("This Fight is calm: " + calm);

        //the Executor Service for this tournier
        ExecutorService tournamentexecutor = Executors.newCachedThreadPool();

        //build up the tournament
        List<Tier> tournament = new ArrayList<>(countOfTiers);

        //build moving list for all the player
        List<Player> remainingPlayerList = new ArrayList<>(maxPlayer);

        //build up the PlayerList
        for (int i = 1; i <= maxPlayer; i++) {
            Player p1 = new Player(i, Enums.Playercondition.PLAYER);
            remainingPlayerList.add(p1);
        }

        //give next bigger amount of player
        int missingPlayer = nextBiggerPlayerCount(maxPlayer) - remainingPlayerList.size();

        System.out.println("missingPlayer " + missingPlayer);
        System.out.println("nextBiggerPlayerCount(maxPlayer) " + nextBiggerPlayerCount(maxPlayer));
        System.out.println("remainingPlayerList size " + remainingPlayerList.size());

        //adding FreePlayer to the List in the first round 
        for (int i = 1; i <= missingPlayer; i++) {
            Player p1 = new Player(FreeWinID + i, Enums.Playercondition.FREEWIN);
            p1.setName("FreeWIN");
            remainingPlayerList.add(p1);
            LOG.debug("added Freewin player");
        }

        //calc new matchcount
        maxMatches = remainingPlayerList.size() / 2;

        System.out.println("maxMatches " + maxMatches);

        //run Tiers
        tournament = runTiers(tournamentexecutor, remainingPlayerList, tournament);

        //try to politly shutdown the executer
        tournamentexecutor.shutdown();

        //display in better cli
        displayTournament(tournament);

        //export to JSON
        saveToJson(tournament);
    }

    private static List<Tier> runTiers(ExecutorService executor, List<Player> remainingPlayerList, List<Tier> tournament) {
        boolean firstround = true;

        //run the tier
        for (int tierCounter = 0; tierCounter <= countOfTiers - 1; tierCounter++) {

            //building the tier
            Tier tier = new Tier(tierCounter);

            //giveback the count of max games for this tier
            maxMatchesInNextTier = getMaxFightsInTier(maxMatchesInNextTier);
            System.out.println("maxMatchesInNextTier " + maxMatchesInNextTier);

            if (firstround) {
                LOG.debug("first round");
                maxMatchesInNextTier = maxMatches;
                //shuffle the playerlist
                long seed = System.nanoTime();
                Collections.shuffle(remainingPlayerList, new Random(seed));
                //set firstorut of false
                firstround = false;
                System.out.println("First round");
            } else {
                //shuffle symbole for eath tier;
                remainingPlayerList.forEach((p) -> {
                    p.setSymbole(p.getRandomSymbole());
                });
                System.out.println("Round nr: " + tierCounter);
            }
            LOG.debug("maxMatchesInNextTier" + maxMatchesInNextTier);
            System.out.println("maxMatchesInNextTier output " + maxMatchesInNextTier);

            //make a new matchList
            matchListForThisTier = new ArrayList<>(maxMatchesInNextTier);
            System.out.println("matchListForThisTier size before " + matchListForThisTier.size());

            //build a list of Losers
            List<Player> loserList = new ArrayList<>(matchListForThisTier.size() / 2);
            System.out.println("loserList size before " + loserList.size());

            //build all Matchs in a Callable List
            List<Callable<Player>> callableList = new MatchBuilder().build(maxMatchesInNextTier, remainingPlayerList);
            

            //staring the Callable
            try {
                //make a CountDownLatch for waiting all matches in a tier are finish to go to the next tier
                CountDownLatch latch = new CountDownLatch(callableList.size());

                //submit Callable tasks to be executed by thread pool
                //CompletableFuture
                List<Future<Player>> futureList = executor.invokeAll(callableList);
                //adding loser of the fight to the loser List
                for (Future<Player> p : futureList) {
                    if (p.isCancelled()) {
                        LOG.error("error task get canceled");
                    }
                    loserList.add(p.get());
                    //count the latch down to "wait" for all player get added to list
                    latch.countDown();
                }

                //wait for countdown
                latch.await();
            } catch (InterruptedException | ExecutionException ex) {
                LOG.error(ex.getMessage());
            }

            System.out.println("loserList size after run " + loserList.size());
            //remove doubles form the loser list agains threadhandling fails
            List<Player> depdupeCustomers = new ArrayList<>(new LinkedHashSet<>(loserList));
            loserList.clear();
            loserList.addAll(depdupeCustomers);
            System.out.println("loserList size after remove doubles" + loserList.size());

            //remove the loser from the remainingPlayerList
            remainingPlayerList.removeAll(loserList);
            System.out.println("remainingPlayerList size after " + remainingPlayerList.size());

            //adding the winner into the matchlog by check the losing
            matchListForThisTier.forEach((m) -> {
                if (m.getPlayer1().getWon() > m.getPlayer2().getWon()) {
                    m.setWinner(m.getPlayer1());
                } else {
                    m.setWinner(m.getPlayer2());
                }
            });

            //reduce the fightcount
            maxMatchesInNextTier = remainingPlayerList.size();

            //fillup the remainingplayer with Freewin
            //adding FREEWIN odd size of remainingPlayerList
            //not in the last round
            if (maxMatchesInNextTier != 1 && remainingPlayerList.size() % 2 != 0) {
                Player p1 = new Player(FreeWinID, Enums.Playercondition.FREEWIN);
                p1.setName("FreeWin");
                //add to random postion in the remainingPlayerList
                int ranhdomIndex = new Random().nextInt(remainingPlayerList.size() - 1);
                remainingPlayerList.add(ranhdomIndex, p1);
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

        return tournament;

    }

    /**
     * giveback the count of max games for this tier. onyl 2 fights are left do
     * not half the fightcount on odd fights add one fight befor half the
     * fightcount
     *
     * @param numbersOfFights
     * @return maxfights for next tier
     */
    private static int getMaxFightsInTier(int numbersOfFights) {
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
     * for this. Winner on the top and than to the button
     *
     * @param tournament
     */
    private static void displayTournament(List<Tier> tournament) {
        //go from last to frist Tier
        for (int i = tournament.size() - 1; i >= 0; i--) {

            //build a emptry string witgh 100 spaces
            StringBuilder spaces = new StringBuilder();
            spaces.append(String.join("", Collections.nCopies(200, " ")));

            int maxl = spaces.length();
            //redruce this string every round
            spaces.delete((i * 10) + 30, maxl);

            //output the number of the tier
            System.out.printf("%s\t%s", tournament.get(i).getTierId() + 1, spaces.toString());

            //setting the winner on the top
            for (int j = 0; j < tournament.get(i).getMatchList().size(); j++) {
                System.out.printf("\t%s", String.format("%s", tournament.get(i).getMatchList().get(j).getWinner().getID()));
            }

            //line break
            System.out.println("");
            //adding all "ID vs ID " to gether and plot it
            System.out.printf("%s\t%s", tournament.get(i).getTierId() + 1, spaces.toString());
            for (int j = 0; j < tournament.get(i).getMatchList().size(); j++) {
                System.out.printf("%s\t", String.format(" %s vs. %s", tournament.get(i).getMatchList().get(j).getPlayer1().getID(), tournament.get(i).getMatchList().get(j).getPlayer2().getID()));
            }

            //line break with 200 -´s
            System.out.println("");
            System.out.println(String.join("", Collections.nCopies(200, "-")));
        }
    }

    /**
     * Save the tournament to a JSON file for the output in webview
     *
     * @param tournament
     */
    private static void saveToJson(List<Tier> tournament) {
        //build a simle nested list cauz the orgianl json is very nested
        List< List< List<ExportPlayer>>> exportMatchList = new ArrayList<>();

        //fill the HashMap
        tournament.forEach((t) -> {
            List<List<ExportPlayer>> list = new ArrayList<>();
            t.getMatchList().forEach((m) -> {
                //newlist for Player
                List<ExportPlayer> exportPlayerList = new ArrayList<>();
                //convert player
                ExportPlayer exp1 = createExportPlayerOutOfPlayer(m.getPlayer1());
                ExportPlayer exp2 = createExportPlayerOutOfPlayer(m.getPlayer2());
                //add to exportPlayer list
                exportPlayerList.add(exp1);
                exportPlayerList.add(exp2);

                //nest it again
                list.add(exportPlayerList);
            });
            //next nested lvl
            exportMatchList.add(list);
        });

        //Convert object to JSON string
        Gson gson = new Gson();
        String jsonString = gson.toJson(exportMatchList);

        //safe to Extern file
        String fileName = "C:\\EAI\\tournament.json";
        File file = new File(fileName);
        //save to file as OutputStream
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            LOG.info("Tournament file: " + fileName + " created");

            fos.write(jsonString.getBytes());

            //move all to file befor close it
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }

    }

    private static ExportPlayer createExportPlayerOutOfPlayer(Player p) {
        //sonvert the Player to a much simpler Object
        ExportPlayer exp = new ExportPlayer();
        exp.setId(p);
        exp.setName(p);
        exp.setSeed(p);

        return exp;

    }

    /**
     * Giveback the maxplayer
     *
     * @param maxPlayer
     * @return
     */
    private static int nextBiggerPlayerCount(int maxPlayer) {
        int outout = 0;
        //never start with an odd player count
        if (maxPlayer % 2 != 0) {
            outout = maxPlayer + 1;
        }

        //round up to next biger tournament
        if (maxPlayer < 8) {
            outout = 8;
        } else if (maxPlayer <= 16) {
            outout = 16;
        } else if (maxPlayer <= 32) {
            outout = 32;
        } else if (maxPlayer <= 64) {
            outout = 64;
        } else if (maxPlayer <= 128) {
            outout = 128;
        }

        //max limeted on added freewin player
        if (outout - maxPlayer >= 6) {
            outout = maxPlayer + 6;
        }

        return outout;
    }

}
