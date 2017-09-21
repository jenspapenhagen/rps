/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public class Rounds {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Rounds.class);

    private final Enum lastPlayer1Symbole;
    private final Enum lastPlayer2Symbole;

    public Rounds(Enum lastPlayer1Symbole, Enum lastPlayer2Symbole) {
        this.lastPlayer1Symbole = lastPlayer1Symbole;
        this.lastPlayer2Symbole = lastPlayer2Symbole;
        
        LOG.debug("lastPlayer1Symbole" + lastPlayer1Symbole);
        LOG.debug("lastPlayer2Symbole" + lastPlayer2Symbole);
    }

    public Enum fightround() {
        Ruler ruler = new Ruler();
        Enum result = null;
        int maxrounds = 5;

        for (int rounds = 1; rounds < maxrounds; rounds++) {
            result = ruler.comparingSymboles(ruler.getBehavor(lastPlayer1Symbole, lastPlayer2Symbole),
                    ruler.getBehavor(lastPlayer2Symbole, lastPlayer1Symbole));
            System.out.println("Result of Round: " + rounds + " is: " + result);

            if (!result.equals(ENUMS.Fightstat.DRAW)) {
                break;
            }

            if (rounds == maxrounds) {
                //froce win
                result = ENUMS.Fightstat.WON;
                break;
            }

        }

        return result;
    }

}
