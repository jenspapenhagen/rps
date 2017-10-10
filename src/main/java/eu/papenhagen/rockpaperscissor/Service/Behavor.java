/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.Service;

import eu.papenhagen.rockpaperscissor.Entities.Enums;
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

    private Enum OppositeOfLastRound(Enum lastRoundSymbol1) {
        LOG.debug("OppositeOfLastRound");
        return getOppositeSymbole(lastRoundSymbol1);
    }

    private Enum SameOfLastRound(Enum lastRoundSymbol1) {
        LOG.debug("SameOfLastRound");
        return lastRoundSymbol1;
    }

    private Enum getOppositeSymbole(Enum Symbol) {
        //take a random Enum but NOT default
        Enums.Symbole output = Enums.Symbole.values()[new Random().nextInt(Enums.Symbole.values().length - 1)];

        return output;
    }

    /**
     * get a random behavor
     *
     * @param Symbole Enum form typ Symbole
     * @return get random Enum back
     */
    public Enum getBehavor(Enum Symbole) {
        int indexer = new Random().nextInt(3);
        Enum output;

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
