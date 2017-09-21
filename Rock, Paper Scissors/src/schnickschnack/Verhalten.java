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
        return CONSTANS.SYMBOLE.SCISSOR;
    }

    public Enum Verhalten2(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        return CONSTANS.SYMBOLE.STONE;
    }

    public Enum Verhalten3(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        return CONSTANS.SYMBOLE.PAPER;
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
        if (Symbol.equals(CONSTANS.SYMBOLE.SCISSOR)) {
            output = CONSTANS.SYMBOLE.PAPER;
        }
        if (Symbol.equals(CONSTANS.SYMBOLE.PAPER)) {
            output = CONSTANS.SYMBOLE.STONE;
        }
        if (Symbol.equals(CONSTANS.SYMBOLE.STONE)) {
            output = CONSTANS.SYMBOLE.SCISSOR;
        }
        
        return output;
    }

}
