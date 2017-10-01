/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

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

    //allways the same
    private Enum Behavor1(Enum lastRoundSymbol1) {
        LOG.debug("Behavor1: take scissor");
        return Enums.Symbole.SCISSOR;
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
     * @param lastRoundSymbol1
     * @param lastRoundSymbol2
     * @return
     */
    public Enum getBehavor(Enum lastRoundSymbol1) {
        int indexer = new Random().nextInt(3);
        Enum output;

        switch (indexer) {
            case 1:
                output = Behavor1(lastRoundSymbol1);
                break;
            case 2:
                output = OppositeOfLastRound(lastRoundSymbol1);
                break;
            case 3:
                output = SameOfLastRound(lastRoundSymbol1);
                break;
            default:
                output = Behavor1(lastRoundSymbol1);

        }

        LOG.debug("" + output);
        return output;
    }
}
