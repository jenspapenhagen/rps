/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.util.concurrent.Callable;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public class Match implements Callable<Player> {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Match.class);

    private final int matchNr;
    private final Player player1;
    private final Player player2;

    public Match(int matchID, Player p1, Player p2) {
        matchNr = matchID;
        player1 = p1;
        player2 = p2;
        LOG.debug("Match with ID:" + matchNr + " getstarted." + player1.getPlayerID() + " vs. " + player2.getPlayerID());
    }

    /**
     * checking the condition of to player object 
     * @param p1
     * @param p2
     * @return the the non player als instant loser
     */
    public Player comparingPlayerCondition(Player p1, Player p2) {
        if (!p1.getPlayerCondtion().equals(CONSTANS.PLAYERCONDITION.PLAYER)) {
            LOG.debug("Player 1 was a non Player object");
            return p1;
        }
        if (!p2.getPlayerCondtion().equals(CONSTANS.PLAYERCONDITION.PLAYER)) {
            LOG.debug("Player 2 was a non Player object");
            return p2;
        }
        return null;
    }

    /**
     * the fight of the 2 player
     * @return 
     */
    @Override
    public Player call() {
        Ruler ruler = new Ruler();

        Enum player1Symbole = player1.getPlayerSymbole();
        Enum player2Symbole = player2.getPlayerSymbole();

        Enum result = null;

        //remove non player
        Player loser = comparingPlayerCondition(player1, player2);
        if(loser != null){
            LOG.debug("Fastgame one player was a non player object");
            return loser;
        }

        result = ruler.comparingSymboles(player1Symbole, player2Symbole);

        //the cli output
        System.out.println(
                "Match " + matchNr + ": Player 1 Name: " + player1.getPlayerName() + " mit der Nr." + player1.getPlayerID() + " nimmt: " + player1Symbole
                + " gegen Player 2 Name: " + player2.getPlayerName() + " mit der Nr. " + player2.getPlayerID() + " mit " + player2Symbole
                + " -- Das Ergebnis ist " + result);

        if (result.equals(CONSTANS.FIGHTSTAT.DRAW)) {
            result = new Rounds(player1Symbole, player2Symbole).fightround();
        }

        //remove the lost player 
        loser = removeLosingPlayer(result, player1, player2);

        return loser;
    }

    /**
     * removing the player object(witch has lost the game) 
     * @param result
     * @param player1
     * @param player2
     * @return the lost player
     */
    public Player removeLosingPlayer(Enum result, Player player1, Player player2) {
        try {
            if (result.equals(CONSTANS.FIGHTSTAT.WON)) {
                return player2;
            } else {
                return player1;
            }
        } catch (NullPointerException ex) {
            //froce win for player 2
            return player1;
        }

    }

}
