/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jens.papenhagen
 */
public class Game implements Callable<Player> {

    private final int matchNr;
    private final Player player1;
    private final Player player2;

    public Game(int _matchNr, Player _player1, Player _player2) {
        matchNr = _matchNr;
        player1 = _player1;
        player2 = _player2;
    }

    @Override
    public Player call() {
        Ruler ruler = new Ruler();

        String player1Symbole = player1.getPlayerSymbole();
        String player2Symbole = player2.getPlayerSymbole();

        //set the playername fix
        String player1Name = player1.getPlayerName();
        String player2Name = player2.getPlayerName();

        player1.setPlayerName(player1Name);
        player2.setPlayerName(player2Name);

        String result = null;

        //remove NON player
        if (!player1.getPlayerStatus().equals(Constans.PLAYERCONDITION.PLAYER.toString())) {
            return player1;
        }
        if (!player2.getPlayerStatus().equals(Constans.PLAYERCONDITION.PLAYER.toString())) {
            return player2;
        }

        result = ruler.result(player1Symbole, player2Symbole);

        //the cli output
        System.out.println("Match " + matchNr + ": Player 1 Name: " + player1Name + " mit der Nr." + player1.getPlayerID() + " nimmt: " + player1Symbole
                + " gegen Player 2 Name: " + player2Name + " mit der Nr. " + player2.getPlayerID() + " mit " + player2Symbole
                + " -- Das Ergebnis ist " + result);

        if (result.equalsIgnoreCase(Constans.FIGHTSTAT.UNENTSCHIEDEN.toString())) {
            result = new Runden(player1Symbole, player2Symbole).fightround();
        }

        //remove the lost player 
        Player loser = removeLosingPlayer(result, player1, player2);

        return loser;
    }

    public Player removeLosingPlayer(String result, Player _player1, Player _player2) {
        Ruler ruler = new Ruler();

        try {
            if (ruler.fightstatus(result).equals(Constans.FIGHTSTAT.GEWONNEN.toString())) {
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
