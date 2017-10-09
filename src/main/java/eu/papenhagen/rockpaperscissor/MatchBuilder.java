/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import org.slf4j.LoggerFactory;

/**
 * Build up the matches
 *
 * @author jens.papenhagen
 */
public class MatchBuilder {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(MatchBuilder.class);

    MatchBuilder() {

    }

    /**
     * build the matches in a extra methode
     *
     * @param maxMatchesInNextTier
     * @param remainingPlayerList
     * @return
     */
    public ReturnObject build(int maxMatchesInNextTier, List<Player> remainingPlayerList, List<Match> matchListForThisTier) {
        //build list
        List<Callable<Player>> callableList = new LinkedList<>();

        //using the Iterator for getting the next player and check if there is even a nextplayer
        Iterator<Player> playerListIterator = remainingPlayerList.iterator();

        LOG.debug("lets fight");
        for (int matchcount = 1; matchcount <= maxMatchesInNextTier; matchcount++) {

            //check if there is a next player in the playerlist and 
            if (playerListIterator.hasNext()) {
                //getting the 2 player
                Player p1 = playerListIterator.next();
                //agains the java.util.NoSuchElementException adding a FreeWIn player
                Player p2 = null;
                if (playerListIterator.hasNext()) {
                    p2 = playerListIterator.next();
                } else {
                    p2 = new Player(Main.getFreeWinID(), Enums.Playercondition.FREEWIN);
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

        ReturnObject ro = new ReturnObject(callableList, matchListForThisTier);
        
        return ro;
    }

}


