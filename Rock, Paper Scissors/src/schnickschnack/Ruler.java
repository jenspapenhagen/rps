/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.util.Random;

/**
 *
 * @author jens.papenhagen
 */
public final class Ruler {

    public Ruler() {

    }

    Ruler(Enum symbole1, Enum symbole2) {
        comparingSymboles(symbole1, symbole2);
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
            return CONSTANS.FIGHTSTAT.DRAW;
        }

        //the rules
        if (symbole1.equals(CONSTANS.SYMBOLE.SCISSOR) && symbole2.equals(CONSTANS.SYMBOLE.PAPER)) {
            
            nondraw = true;
        }

        if (symbole1.equals(CONSTANS.SYMBOLE.PAPER) && symbole2.equals(CONSTANS.SYMBOLE.STONE) ) {
            nondraw = true;
        }

        if (symbole1.equals(CONSTANS.SYMBOLE.STONE) && symbole2.equals(CONSTANS.SYMBOLE.SCISSOR)) {
            nondraw = true;
        }

        if (nondraw) {
            output = CONSTANS.FIGHTSTAT.WIN;
        } else {
            output = CONSTANS.FIGHTSTAT.LOST;
        }

        return output;
    }

    public Enum getVerhalten(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        int indexer = new Random().nextInt(7);
        Verhalten verh = new Verhalten();
        Enum output;

        switch (indexer) {
            case 1:
                output = verh.Verhalten1(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 2:
                output = verh.Verhalten2(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 3:
                output = verh.Verhalten3(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 4:
                output = verh.OppositeOfEnemieFromLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 5:
                output = verh.OppositeOfLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 6:
                output = verh.SameOfEnemieFromLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            case 7:
                output = verh.SameOfLastRound(lastRoundSymbol1, lastRoundSymbol2);
                break;
            default:
                output = verh.Verhalten1(lastRoundSymbol1, lastRoundSymbol2);

        }

        return output;
    }

}
