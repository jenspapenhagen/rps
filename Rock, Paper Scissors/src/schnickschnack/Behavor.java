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
public class Behavor {

    public Behavor() {
    }

    //allways the same
    public Enum Behavor1(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        return ENUMS.SYMBOLE.SCISSOR;
    }

    public Enum Behavor2(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        return ENUMS.SYMBOLE.STONE;
    }

    public Enum Behavor3(Enum lastRoundSymbol1, Enum lastRoundSymbol2) {
        return ENUMS.SYMBOLE.PAPER;
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
        if (Symbol.equals(ENUMS.SYMBOLE.SCISSOR)) {
            output = ENUMS.SYMBOLE.PAPER;
        }
        if (Symbol.equals(ENUMS.SYMBOLE.PAPER)) {
            output = ENUMS.SYMBOLE.STONE;
        }
        if (Symbol.equals(ENUMS.SYMBOLE.STONE)) {
            output = ENUMS.SYMBOLE.SCISSOR;
        }
        
        return output;
    }

}
