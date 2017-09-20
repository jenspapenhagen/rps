/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

/**
 *
 * @author jens.papenhagen
 */
public class Verhalten {

    public Verhalten() {
    }

    //allways the same
    public Enum Verhalten1(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        return CONSTANS.SYMBOLE.SCHERE;
    }

    public Enum Verhalten2(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        return CONSTANS.SYMBOLE.STEIN;
    }

    public Enum Verhalten3(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        return CONSTANS.SYMBOLE.PAPIER;
    }

    public Enum OppositeOfLastRound(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        Enum output = getOppositeSymbole(lastRoundSymbol1);

        return output;
    }

    public Enum OppositeOfEnemieFromLastRound(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        Enum output = getOppositeSymbole(lastRoundSymbol2);

        return output;
    }

    public Enum SameOfLastRound(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        Enum output = lastRoundSymbol1;

        return output;
    }

    public Enum SameOfEnemieFromLastRound(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        Enum output = lastRoundSymbol2;

        return output;
    }

    public Enum getOppositeSymbole(Enum Symbol) {
        Enum output = null;
        if (Symbol.equals(CONSTANS.SYMBOLE.SCHERE)) {
            output = CONSTANS.SYMBOLE.PAPIER;
        }
        if (Symbol.equals(CONSTANS.SYMBOLE.PAPIER)) {
            output = CONSTANS.SYMBOLE.STEIN;
        }
        if (Symbol.equals(CONSTANS.SYMBOLE.STEIN)) {
            output = CONSTANS.SYMBOLE.SCHERE;
        }
        
        return output;
    }

}
