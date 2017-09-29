/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

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
    private final boolean calm;

    public Fight(int matchID, Player p1, Player p2, boolean calm) {
        this.matchNr = matchID;
        this.player1 = p1;
        this.player2 = p2;
        this.calm = calm;
        LOG.debug("Match with ID:" + matchNr);
        LOG.debug(" getstarted." + player1.getPlayerID() + " vs. " + player2.getPlayerID());
        LOG.debug("This Fight is: " + this.calm);
    }

    /**
     * checking the condition of to player object
     *
     * @param p1
     * @param p2
     * @return the the non player als instant loser
     */
    public Player comparingPlayerCondition(Player p1, Player p2) {
        if (!p1.getPlayerCondtion().equals(Enums.Playercondition.PLAYER)) {
            LOG.debug("Player 1 was a non Player object");
            return p1;
        }
        if (!p2.getPlayerCondtion().equals(Enums.Playercondition.PLAYER)) {
            LOG.debug("Player 2 was a non Player object");
            return p2;
        }
        return null;
    }

    /**
     * the fight of the 2 player
     *
     * @return
     */
    @Override
    public Player call() {
        Ruler ruler = new Ruler();

        //getting the symboles of the player
        Enum player1Symbole = player1.getPlayerSymbole();
        Enum player2Symbole = player2.getPlayerSymbole();

        Enum result = null;

        //remove player out of condition
        Player loser = comparingPlayerCondition(player1, player2);
        if (loser != null) {
            LOG.debug("Fastgame one player was a non player object");
            return loser;
        }

        //the BestOf Modus
        for (int bestOfround = 0; bestOfround < Main.getBestOf(); bestOfround++) {
            //have a way to change the behavor
            Behavor behv = new Behavor();

            //after frist bestOf round change the behavor of the player
            if (bestOfround > 0) {
                //getting the symboles of the player
                player1Symbole = behv.getBehavor(player1Symbole, player2Symbole);
                player2Symbole = behv.getBehavor(player2Symbole, player1Symbole);
            }

            //comparing the two symboles from the players
            if (player1Symbole.equals(player2Symbole)) {
                result = Enums.Fightstat.DRAW;
            } else if (ruler.comparingBigSymboleRange((Enums.Symbole) player1Symbole, (Enums.Symbole) player2Symbole)) {
                result = Enums.Fightstat.LOST;
            } else {
                result = Enums.Fightstat.WON;
            }

            if (calm) {
                LOG.debug("Match " + matchNr + ": Player 1 Name: " + player1.getPlayerName() + " mit der Nr." + player1.getPlayerID() + " nimmt: " + player2Symbole
                        + " gegen Player 2 Name: " + player2.getPlayerName() + " mit der Nr. " + player2.getPlayerID() + " mit " + player2Symbole
                        + " -- Player1 hat: " + result);
            } else {
                //the cli output
                System.out.println("Match " + matchNr + ": Player 1 Name: " + player1.getPlayerName() + " mit der Nr." + player1.getPlayerID() + " nimmt: " + player2Symbole
                        + " gegen Player 2 Name: " + player2.getPlayerName() + " mit der Nr. " + player2.getPlayerID() + " mit " + player2Symbole
                        + " -- Player1 hat: " + result);
            }

            //playing rounds if the frist fight was a draw
            if (result.equals(Enums.Fightstat.DRAW)) {
                int maxrounds = 5;
                //rounds
                for (int rounds = 1; rounds < maxrounds; rounds++) {
                    result = ruler.comparingSymboles(behv.getBehavor(player1Symbole, player2Symbole),
                            behv.getBehavor(player2Symbole, player1Symbole));
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
            if (player1.getWon() > (Main.getBestOf() / 2)) {
                result = Enums.Fightstat.WON;
            } 
            
            if (player2.getWon() > (Main.getBestOf() / 2)) {
                result = Enums.Fightstat.LOST;
            }

        }

        //giveback the lost player 
        loser = removeLosingPlayer(result, player1, player2);

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
    public Player removeLosingPlayer(Enum result, Player player1, Player player2) {
        try {
            if (result.equals(Enums.Fightstat.WON)) {
                return player2;
            } else {
                return player1;
            }
        } catch (NullPointerException ex) {
            //froce win for player 2
            LOG.debug("froce win for player 2");
            LOG.error(ex.getMessage());
            return player1;
        }

    }

}
