/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.EAO;

import eu.papenhagen.rockpaperscissor.Service.Fight;
import eu.papenhagen.rockpaperscissor.Entities.*;
import eu.papenhagen.rockpaperscissor.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * This Match Handler have dif. methodes for Handling Match
 *
 * @author jens.papenhagen
 */
public class MatchHandler {
    
    /**
     * buildMatches the matches in a extra methode
     *
     * @param maxMatchesInNextTier an int for the maxiaml amout of machtes in this tier
     * @param remainingPlayerList a list of player
     * @param matchListForThisTier a list of matches for logging pr.
     * @return a returnobject a list of callables and a list of matches
     */
    public static List<List> buildMatches(int maxMatchesInNextTier, List<Player> remainingPlayerList, List<Match> matchListForThisTier) {
        //build list
        List<Callable<Player>> callableList = new LinkedList<>();
        
        //using the Iterator for getting the next player and check if there is even a nextplayer
        Iterator<Player> playerListIterator = remainingPlayerList.iterator();

        for (int matchcount = 1; matchcount <= maxMatchesInNextTier; matchcount++) {

            //check if there is a next player in the playerlist and 
            if (playerListIterator.hasNext()) {
                //getting the 2 player
                Player p1 = playerListIterator.next();
                //check if there is an other Player for this match if NOT build a FREEWIN one
                Player p2 = null;
                if (playerListIterator.hasNext()) {
                    p2 = playerListIterator.next();
                } else {
                    //only as Fallback
                    p2 = new Player(Main.getMaxPlayer()+1 , Player.PLAYERCONDITION.FREEWIN);
                    p2.setName("FreeWin");
                }

                //set match 
                Match match = new Match(matchcount, p1, p2);

                //addin the fight
                callableList.add(new Fight(matchcount, match));

                //add this matchlog to the matchlist
                matchListForThisTier.add(match);
            }
        }
        List<List> returnlist = new ArrayList<>();
        returnlist.add(callableList);
        returnlist.add(matchListForThisTier);

        
        return returnlist;
    }

}


