/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.EAO;

import eu.papenhagen.rockpaperscissor.Entities.ExportPlayer;
import eu.papenhagen.rockpaperscissor.Entities.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * This Player Handler have dif. methodes for Handling Player
 * @author jens.papenhagen
 */
public class PlayerHandler {

    /**
     * Get a List of spezifis Player
     * @param number how many player are in the List
     * @param starterId staring ID of the Player get count up
     * @param condition what is the Condition of the Player
     * @return the list of Player
     */
    public static List<Player> getListOfPlayerWithCondition(int number, int starterId,Player.PLAYERCONDITION condition) {
        List<Player> list = new ArrayList<>(number);
        for (int i = starterId; i <= (number + starterId) ; i++) {
            Player p1 = new Player(i, condition);
            list.add(p1);
        }
        return list;
    }

    /**
     * convert a Object Player to ExportPlayer Object
     * @param p the player
     * @return giveback the Exportplayer out of Player
     */
    public static ExportPlayer createExportPlayerOutOfPlayer(Player p) {
        //sonvert the Player to a much simpler Object
        ExportPlayer exp = new ExportPlayer();
        exp.setId(p);
        exp.setName(p);
        exp.setSeed(p);
        return exp;
    }

    /**
     * Giveback the maxplayer
     *
     * @param maxPlayer are need for checking the next bigger tournament player amount
     * @return the next bigger Tournier player count
     */
    public static int nextBiggerPlayerCount(int maxPlayer) {
        int outout = 0;
        //never start with an odd player count
        if (maxPlayer % 2 != 0) {
            outout = maxPlayer + 1;
        }
        //round up to next biger tournament
        if (maxPlayer < 8) {
            outout = 8;
        } else if (maxPlayer <= 16) {
            outout = 16;
        } else if (maxPlayer <= 32) {
            outout = 32;
        } else if (maxPlayer <= 64) {
            outout = 64;
        } else if (maxPlayer <= 128) {
            outout = 128;
        }
        //max limeted on added freewin player
        if (outout - maxPlayer >= 6) {
            outout = maxPlayer + 6;
        }
        return outout;
    }

}
