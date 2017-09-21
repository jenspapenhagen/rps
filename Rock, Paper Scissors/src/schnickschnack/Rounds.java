/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jens.papenhagen
 */
public class Rounds {

    private final Enum lastPlayer1Symbole;
    private final Enum lastPlayer2Symbole;

    public Rounds(Enum _lastPlayer1Symbole, Enum _lastPlayer2Symbole) {
        lastPlayer1Symbole = _lastPlayer1Symbole;
        lastPlayer2Symbole = _lastPlayer2Symbole;

    }

    public Enum fightround() {
        Ruler ruler = new Ruler();
        Enum result = null;
        int maxrounds = 5;

        for (int rounds = 1; rounds < maxrounds; rounds++) {
            result = ruler.comparingSymboles(ruler.getVerhalten(lastPlayer1Symbole, lastPlayer2Symbole),
                    ruler.getVerhalten(lastPlayer2Symbole, lastPlayer1Symbole));
            System.out.println("Result of Round: " + rounds + " is: " + result);

            if (!result.equals(ENUMS.FIGHTSTAT.DRAW)) {
                break;
            }

            if (rounds == maxrounds) {
                //froce win
                result = ENUMS.FIGHTSTAT.WON;
                break;
            }

        }

        return result;
    }

}
