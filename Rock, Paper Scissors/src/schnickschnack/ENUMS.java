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
    public enum SYMBOLE {
        PAPER,
        SCISSOR,
        STONE
    };

    public enum FIGHTSTAT {
        WON,
        LOST,
        DRAW
    };
    
    public enum PLAYERCONDITION {
        PLAYER,
        FREEWIN,
        DISQUALIFIZIED
    };
}
