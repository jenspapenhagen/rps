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
public class Game implements Callable<Player> {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Game.class);

    private final int matchNr;
    private final Player player1;
    private final Player player2;

    public Game(int matchID, Player p1, Player p2) {
        matchNr = matchID;
        player1 = p1;
        player2 = p2;
        LOG.debug("Match with ID:" + matchNr + " getstarted." + player1.getPlayerID() + " vs. " + player2.getPlayerID());
    }

    
    public void comparingPlayerCondition(Player p1, Player p2) {

    }
    @Override
    public Player call() {
        Ruler ruler = new Ruler();

        String player1Symbole = player1.getPlayerSymbole();
        String player2Symbole = player2.getPlayerSymbole();

        String result = null;

         
    

    
    //remove player

    if (!player1.getPlayerCondtion () 
        .equals(CONSTANS.PLAYERCONDITION.PLAYER.toString())) {
            return player1;
    }

    if (!player2.getPlayerCondtion () 
        .equals(CONSTANS.PLAYERCONDITION.PLAYER.toString())) {
            return player2;
    }

    result  = ruler.comparingSymboles(player1Symbole, player2Symbole);

    //the cli output
    System.out.println (

    "Match " + matchNr + ": Player 1 Name: " +  player1.getPlayerName() + " mit der Nr." + player1.getPlayerID() + " nimmt: " + player1Symbole
                + " gegen Player 2 Name: " + player2.getPlayerName() + " mit der Nr. " + player2.getPlayerID() + " mit " + player2Symbole
                + " -- Das Ergebnis ist " + result);

        if (result.equalsIgnoreCase (CONSTANS.FIGHTSTAT.UNENTSCHIEDEN.toString 
        ())) {
            result = new Runden(player1Symbole, player2Symbole).fightround();
    }

    //remove the lost player 
    Player loser = removeLosingPlayer(result, player1, player2);

    return loser ;
}

public Player removeLosingPlayer(String result, Player _player1, Player _player2) {
        Ruler ruler = new Ruler();

        try {
            if (ruler.fightstatus(result).equals(CONSTANS.FIGHTSTAT.GEWONNEN.toString())) {
                return _player2;
            } else {
                return _player1;
            }
        } catch (NullPointerException ex) {
            //froce win for player 2
            return _player1;
        }

    }

}
