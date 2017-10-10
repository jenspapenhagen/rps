/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.EAO;

import eu.papenhagen.rockpaperscissor.Entities.Player;
import eu.papenhagen.rockpaperscissor.Entities.Enums;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jens.papenhagen
 */
public class PlayerHandler {

    public static List<Player> getListOfPlayerWithCondition(int number, int starterId,Enums.Playercondition condition) {
        List<Player> list = new ArrayList<>(number);
        for (int i = starterId; i <= (number + starterId) ; i++) {
            Player p1 = new Player(i, condition);
            list.add(p1);
        }
        return list;
    }

}
