/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spielwiese.schnickschnack;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jens.papenhagen
 */
public class Runden {

    private final String lastPlayer1Symbole;
    private final String lastPlayer2Symbole;

    public Runden(String _lastPlayer1Symbole, String _lastPlayer2Symbole) {
        lastPlayer1Symbole = _lastPlayer1Symbole;
        lastPlayer2Symbole = _lastPlayer2Symbole;

    }

    public String fightround() {
        Ruler ruler = new Ruler();
        String result = null;
        int maxrounds = 5;
        
        for (int rounds = 1; rounds < maxrounds; rounds++) {
            try {
                result = ruler.result(ruler.getVerhalten(lastPlayer1Symbole, lastPlayer2Symbole),
                        ruler.getVerhalten(lastPlayer2Symbole, lastPlayer1Symbole));
                System.out.println("Result of Round: " + rounds + " is: " + result);
            } catch (DrawException ex) {
                Logger.getLogger(Runden.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!result.equals(Constans.fightstat.UNENTSCHIEDEN.toString())) {
                break;
            }

            if (rounds == maxrounds) {
                //froce win
                result = "Player 1 gewinnt";
                break;
            }

        }

        return result;
    }

}
