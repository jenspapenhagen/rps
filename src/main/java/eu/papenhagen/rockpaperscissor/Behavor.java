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
    public Enum Behavor1(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        LOG.debug("Behavor1: take scissor");
        return Enums.Symbole.SCISSOR;
    }

    public Enum Behavor2(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        LOG.debug("Behavor2: take rock");
        return Enums.Symbole.ROCK;
    }

    public Enum Behavor3(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        LOG.debug("Behavor3: take paper");
        return Enums.Symbole.PAPER;
    }

    public Enum OppositeOfLastRound(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        LOG.debug("OppositeOfLastRound");
        return getOppositeSymbole(lastRoundSymbol1);
    }


    public Enum SameOfLastRound(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        LOG.debug("SameOfLastRound");
        return lastRoundSymbol1;
    }


    public Enum getOppositeSymbole(Enum Symbol) {
        Enum output = Enums.Symbole.ROCK;
        if (Symbol.equals(Enums.Symbole.SCISSOR)) {
            output = Enums.Symbole.PAPER;
        }
        if (Symbol.equals(Enums.Symbole.PAPER)) {
            output = Enums.Symbole.ROCK;
        }
        if (Symbol.equals(Enums.Symbole.ROCK)) {
            output = Enums.Symbole.SCISSOR;
        }

        return output;
    }

    /**
     * get a random behavor
     *
     * @param lastRoundSymbol1
     * @param lastRoundSymbol2
     * @return
     */
    public Enum getBehavor(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        int indexer = new Random().nextInt(5);
        Enum output;

        switch (indexer) {
            case 1:
                output = Behavor1(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 2:
                output = Behavor2(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 3:
                output = Behavor3(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 4:
                output = OppositeOfLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 5:
                output = SameOfLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            default:
                output = Behavor1(lastRoundSymbol1, lastRoundSymbol2);

        }

        LOG.debug("" + output);
        return output;
    }
}
