/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.Service;

import eu.papenhagen.rockpaperscissor.Entities.Player;
import java.util.Random;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public class Behavor {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Behavor.class);

    public Behavor() {
    }

    private Player.Symbole OppositeOfLastRound(Player.Symbole lastRoundSymbol1) {
        LOG.debug("OppositeOfLastRound");
        return getOppositeSymbole(lastRoundSymbol1);
    }

    private Player.Symbole SameOfLastRound(Player.Symbole lastRoundSymbol1) {
        LOG.debug("SameOfLastRound");
        return lastRoundSymbol1;
    }

    private Player.Symbole getOppositeSymbole(Player.Symbole Symbol) {
        //take a random Enum but NOT default
        Player.Symbole output = Player.Symbole.values()[new Random().nextInt(Player.Symbole.values().length - 1)];

        return output;
    }

    /**
     * get a random behavor
     *
     * @param Symbole Enum form typ Symbole
     * @return get random Enum back
     */
    public Player.Symbole getBehavor(Player.Symbole Symbole) {
        int indexer = new Random().nextInt(3);
        Player.Symbole output;

        switch (indexer) {
            case 1:
                output = OppositeOfLastRound(Symbole);
                break;
            case 2:
                output = SameOfLastRound(Symbole);
                break;
            default:
                output = SameOfLastRound(Symbole);

        }

        LOG.debug("" + output);
        return output;
    }
}
