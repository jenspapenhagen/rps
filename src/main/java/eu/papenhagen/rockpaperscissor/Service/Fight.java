/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.Service;

import eu.papenhagen.rockpaperscissor.Entities.Enums;
import eu.papenhagen.rockpaperscissor.Entities.Match;
import eu.papenhagen.rockpaperscissor.Entities.Player;
import eu.papenhagen.rockpaperscissor.Main;
import java.util.concurrent.Callable;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public class Fight implements Callable<Player> {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Fight.class);

    private final int matchNr;
    private final Player player1;
    private final Player player2;
    private final boolean calm = Main.isCalm();
    private static final int maxrounds = 5;

    public Fight(int matchID, Match m) {

        this.matchNr = matchID;
        this.player1 = m.getPlayer1();
        this.player2 = m.getPlayer2();
        LOG.debug("Match with ID:" + matchNr);
        LOG.debug(" getstarted." + player1.getID() + " vs. " + player2.getID());
        LOG.debug("This Fight is: " + this.calm);
    }

    private void resetWonStatForPlayers(Player p1, Player p2) {
        p1.setWon(0);
        p2.setWon(0);
    }

    /**
     * checking the condition of to player object
     *
     * @param p1 player 1
     * @param p2 player 2
     * @return the the non player als instant loser
     */
    public Player comparingPlayerCondition(Player p1, Player p2) {
        if (!p1.getCondition().equals(Player.Playercondition.PLAYER)) {
            LOG.debug("Player 1 has not a Playercondition of Player");
            return p1;
        }
        if (!p2.getCondition().equals(Player.Playercondition.PLAYER)) {
            LOG.debug("Player 2 has not a Playercondition of Player");
            return p2;
        }
        return null;
    }

    private Enum complainingPlayerSymboles(Enum player1Symbole, Enum player2Symbole) {

        Enum result = null;
        Player.Symbole temoSymbole = (Player.Symbole) player1Symbole;
        //comparing the two symboles from the players
        if (player1Symbole.equals(player2Symbole)) {
            return result = Enums.Fightstat.DRAW;
        } else if (temoSymbole.loseAgaist((Player.Symbole) player1Symbole, (Player.Symbole) player2Symbole)) {
            result = Enums.Fightstat.LOST;
        } else {
            result = Enums.Fightstat.WON;
        }

        return result;
    }

    /**
     * the fight of the 2 player
     *
     * @return the lost player
     */
    @Override
    public Player call() {
        //getting the symboles of the player
        Enum player1Symbole = player1.getSymbole();
        Enum player2Symbole = player2.getSymbole();

        Enum result = null;

        //remove player out of condition
        Player loser = comparingPlayerCondition(player1, player2);
        if (loser != null) {
            LOG.debug("Fastgame one player was a non player object");
            return loser;
        }

        //reset the Won counter of all player
        resetWonStatForPlayers(player1, player2);

        //the BestOf Modus
        for (int bestOfround = 1; bestOfround < Main.getBestOf(); bestOfround++) {
            //have a way to change the behavor
            Behavor behv = new Behavor();

            //after frist bestOf round change the behavor of the player
            if (bestOfround > 1) {
                //getting the symboles of the player
                player1Symbole = behv.getBehavor(player1Symbole);
                player2Symbole = behv.getBehavor(player1Symbole);
            }

            //get back the enum for win or lost or draw
            result = complainingPlayerSymboles(player1Symbole, player2Symbole);

            if (calm) {
                LOG.debug("Match " + matchNr + ": Player 1 Name: " + player1.getName() + " with the No." + player1.getID() + " takes: " + player1Symbole
                        + " agains Player 2 Name: " + player2.getName() + " with the No. " + player2.getID() + " has " + player2Symbole
                        + " -- Player1 has: " + result);
            } else {
                //the cli output
                System.out.println("Match " + matchNr + ": Player 1 Name: " + player1.getName() + " with the No." + player1.getID() + " takes: " + player1Symbole
                        + " agains Player 2 Name: " + player2.getName() + " with the No. " + player2.getID() + " has " + player2Symbole
                        + " -- Player1 has: " + result);
            }

            //playing rounds if the frist fight was a draw
            if (result.equals(Enums.Fightstat.DRAW)) {

                //rounds
                for (int rounds = 1; rounds < Fight.maxrounds; rounds++) {
                    Enum roundSymbole1 = behv.getBehavor(player1Symbole);
                    Enum roundSymbole2 = behv.getBehavor(player2Symbole);

                    //get back the enum for win or lost or draw
                    result = complainingPlayerSymboles(roundSymbole1, roundSymbole2);
                    if (calm) {
                        LOG.debug("Result of Round: " + rounds + " is: " + result);
                    } else {
                        System.out.println("Result of Round: " + rounds + " is: " + result);
                    }

                    //we have a winner lift this loop
                    if (!result.equals(Enums.Fightstat.DRAW)) {
                        break;
                    }
                    //max round reach froce win for player 1
                    if (rounds == maxrounds) {
                        LOG.debug("froce win");
                        result = Enums.Fightstat.WON;
                        break;
                    }

                }
            }

            //count the won counter in the bestOF
            if (result.equals(Enums.Fightstat.WON)) {
                player1.setWon(player1.getWon() + 1);
            } else {
                player2.setWon(player2.getWon() + 1);
            }

            /**
             * check if more then the half of bestOF rounds have won by a player
             * to skip next rounds of this bestOf on division the lower number
             * get use 5 / 2 = 2 NOT 3 therefor we can use the > operator
             */
            if (Main.getBestOf() >= 3) {
                if (player1.getWon() > (Main.getBestOf() / 2)) {
                    result = Enums.Fightstat.WON;
                }

                if (player2.getWon() > (Main.getBestOf() / 2)) {
                    result = Enums.Fightstat.LOST;
                }
            }

        }

        //giveback the lost player 
        loser = givebackLosingPlayer((Enums.Fightstat) result, player1, player2);

        return loser;
    }

    /**
     * removing the player object(witch has lost the game)
     *
     * @param result
     * @param player1
     * @param player2
     * @return the lost player
     */
    private Player givebackLosingPlayer(Enums.Fightstat result, Player player1, Player player2) {
        try {
            if (result.equals(Enums.Fightstat.WON)) {
                return player2;
            } else {
                return player1;
            }
        } catch (NullPointerException ex) {
            //froce win for player 2
            LOG.debug("froce win for player 2");
            LOG.debug(ex.getMessage());
            return player1;
        }

    }

}
