/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.EntitiesService;

import eu.papenhagen.rockpaperscissor.Entities.Player;
import eu.papenhagen.rockpaperscissor.Enums;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jens.papenhagen
 */
public class PlayerService {

    public static List<Player> getListOfPlayerWithCondition(int number, Enums.Playercondition condition) {
        List<Player> list = new ArrayList<>(number);
        for (int i = 1; i <= number; i++) {
            Player p1 = new Player(i, condition);
            list.add(p1);
        }
        return list;
    }

}
