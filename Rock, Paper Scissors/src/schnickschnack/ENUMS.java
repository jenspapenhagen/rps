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
public class ENUMS {
    public enum Symbole {
        PAPER,
        SCISSOR,
        STONE
    };

    public enum Fightstat {
        WON,
        LOST,
        DRAW
    };
    
    public enum Playercondition {
        PLAYER,
        FREEWIN,
        DISQUALIFIZIED
    };
}
