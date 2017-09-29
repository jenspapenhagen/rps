/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import lombok.*;

/**
 *
 * @author jens.papenhagen
 */
public class Match {

    @Setter
    @Getter
    private int id;

    @Setter
    @Getter
    private Player player1;

    @Setter
    @Getter
    private Player player2;
    
    @Setter
    @Getter
    private Player winner;

    public Match(int id, Player player1, Player player2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
    }

}
