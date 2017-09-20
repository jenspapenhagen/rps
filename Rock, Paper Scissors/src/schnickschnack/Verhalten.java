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
    public String Verhalten1(String lastRoundSymbol1, String lastRoundSymbol2) {
        
        return Constans.SYMBOLE.SCHERE.toString();
    }

    public String Verhalten2(String lastRoundSymbol1, String lastRoundSymbol2) {
        return Constans.SYMBOLE.STEIN.toString();
    }

    public String Verhalten3(String lastRoundSymbol1, String lastRoundSymbol2) {
        return Constans.SYMBOLE.PAPIER.toString();
    }

    public String OppositeOfLastRound(String lastRoundSymbol1, String lastRoundSymbol2) {
        String output = OppositeSymbole(lastRoundSymbol1);

        return output;
    }

    public String OppositeOfEnemieFromLastRound(String lastRoundSymbol1, String lastRoundSymbol2) {
        String output = OppositeSymbole(lastRoundSymbol2);

        return output;
    }

    public String SameOfLastRound(String lastRoundSymbol1, String lastRoundSymbol2) {
        String output = lastRoundSymbol1;

        return output;
    }

    public String SameOfEnemieFromLastRound(String lastRoundSymbol1, String lastRoundSymbol2) {
        String output = lastRoundSymbol2;

        return output;
    }

    public String OppositeSymbole(String Symbol) {
        String output;
        //the rules
        switch (Symbol) {
            case "SCHERE":
                output = Constans.SYMBOLE.PAPIER.toString();
                break;
            case "PAPIER":
                output = Constans.SYMBOLE.STEIN.toString();
                break;
            case "STEIN" :
                output = Constans.SYMBOLE.SCHERE.toString();
                break;
            default:
                output = Constans.SYMBOLE.PAPIER.toString();
                
        }

        return output;
    }

}
