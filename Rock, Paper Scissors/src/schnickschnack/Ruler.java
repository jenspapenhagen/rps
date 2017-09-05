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

    Ruler(String symbole1, String symbole2) throws DrawException {
        result(symbole1, symbole2);
    }

    /**
     *
     * @param symbole1
     * @param symbole2
     * @return Schere gewinnt gegen Papier, Papier gewinnt gegen Stein, Stein
     * gewinnt gegen Schere
     * @throws schnickschnack.DrawException
     */
    public String result(String symbole1, String symbole2) throws DrawException {
        String output = null;
        boolean nondraw = false;

        if (symbole1 == null ? symbole2 == null : symbole1.equals(symbole2)) {
            output = Constans.fightstat.UNENTSCHIEDEN.toString();

            //throw new DrawException();
            return output;

        }

        //the rules
        if (symbole1.equals(Constans.symbole.SCHERE.toString())) {
            nondraw = symbole2.equals(Constans.symbole.PAPIER.toString());
        }

        if (symbole1.equals(Constans.symbole.PAPIER.toString())) {
            nondraw = symbole2.equals(Constans.symbole.STEIN.toString());
        }

        if (symbole1.equals(Constans.symbole.STEIN.toString())) {
            nondraw = symbole2.equals(Constans.symbole.SCHERE.toString());
        }

        if (nondraw) {
            output = "Player 1 gewinnt";
        } else {
            output = "Player 2 gewinnt";
        }

        return output;
    }

    //translate the output form String to a nicer Enum
    public String fightstatus(String result) {
        String output = null;

        switch (result) {
            case "Player 1 gewinnt":
                output = Constans.fightstat.GEWONNEN.toString();
                break;
            case "Player 2 gewinnt":
                output = Constans.fightstat.VERLOHREN.toString();
                break;
            default:
                output = Constans.fightstat.GEWONNEN.toString();
                break;
        }
        
        

        return output;
    }

    public String getVerhalten(String lastRoundSymbol1, String lastRoundSymbol2) {
        int indexer = new Random().nextInt(7);
        Verhalten verh = new Verhalten();
        String output;

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
