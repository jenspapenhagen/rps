/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.util.EnumSet;
import java.util.Random;
import schnickschnack.Enums.Symbole;

/**
 *
 * @author jens.papenhagen
 */
public final class Ruler {

    public Ruler() {
    }

    public Ruler(Enum symbole1, Enum symbole2) {
        comparingSymboles(symbole1, symbole2);
    }

    
    
    /**
     * check the bigger range of symbole
     * @param symbole1
     * @param symbole2
     * @return true symbole1 have lost
     */
    public boolean comparingBigSymboleRange(Symbole symbole1, Symbole symbole2) {
        return symbole1.loseAgaist(symbole1,symbole2);
    }

    /**
     *
     * @param symbole1
     * @param symbole2
     * @return Schere gewinnt gegen Papier, Papier gewinnt gegen Stein, Stein
     * gewinnt gegen Schere
     */
    public Enum comparingSymboles(Enum symbole1, Enum symbole2) {
       
        Enum output = null;
        boolean nondraw = false;

        if (symbole1 == null ? symbole2 == null : symbole1.equals(symbole2)) {
            return Enums.Fightstat.DRAW;
        }

        //the rules
        if (symbole1.equals(Enums.Symbole.SCISSOR) && symbole2.equals(Enums.Symbole.PAPER)) {
            nondraw = true;
        }

        if (symbole1.equals(Enums.Symbole.PAPER) && symbole2.equals(Enums.Symbole.ROCK)) {
            nondraw = true;
        }

        if (symbole1.equals(Enums.Symbole.ROCK) && symbole2.equals(Enums.Symbole.SCISSOR)) {
            nondraw = true;
        }

        if (nondraw) {
            output = Enums.Fightstat.WON;
        } else {
            output = Enums.Fightstat.LOST;
        }

        return output;
    }

    public Enum getBehavor(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        int indexer = new Random().nextInt(7);
        Behavor behave = new Behavor();
        Enum output;

        switch (indexer) {
            case 1:
                output = behave.Behavor1(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 2:
                output = behave.Behavor2(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 3:
                output = behave.Behavor3(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 4:
                output = behave.OppositeOfEnemieFromLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 5:
                output = behave.OppositeOfLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 6:
                output = behave.SameOfEnemieFromLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 7:
                output = behave.SameOfLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            default:
                output = behave.Behavor1(lastRoundSymbol1, lastRoundSymbol2);

        }

        return output;
    }

}
