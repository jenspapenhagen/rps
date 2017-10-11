/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import eu.papenhagen.rockpaperscissor.EAO.*;
import eu.papenhagen.rockpaperscissor.Entities.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import lombok.*;

/**
 *
 * @author jens.papenhagen
 */
public class Main {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);

    @Getter
    private static int bestOf;
    @Getter
    private static boolean calm;
    @Getter
    private static int maxPlayer;
    @Getter
    @Setter
    private static int maxMatches;
    @Getter
    @Setter
    private static int maxMatchesInNextTier;
    @Getter
    @Setter
    private static int countOfTiers;

    public static void main(String[] args) {
        //config area for this tournament
        //there are 8/16/32/64/128 tournaments please select a number near this
        maxPlayer = 100;
        calm = true;
        bestOf = 5;

        //STOP CHANGING HERE
        int firstID = 1;
        countOfTiers = TierHandler.calulateMaxTiers(maxPlayer);
        maxMatches = maxPlayer / 2;
        maxMatchesInNextTier = maxMatches;

        LOG.debug("maxplayer for this tournament " + maxPlayer);
        LOG.debug("max Match games for the frist round " + maxMatches);
        LOG.debug("maxGamesNextTier " + maxMatchesInNextTier);
        LOG.debug("Count of tiers for (only for logging) " + countOfTiers);

        LOG.debug("This Fight is calm: " + calm);

        //build moving list for all the player
        List<Player> playerList = new ArrayList<>(maxPlayer);

        //build up the PlayerList
        playerList = PlayerHandler.getListOfPlayerWithCondition(maxPlayer, firstID, Player.PLAYERCONDITION.PLAYER);

        //give next bigger amount of player
        int missingPlayer = PlayerHandler.nextBiggerPlayerCount(maxPlayer) - playerList.size();

        //adding FreePlayer to the List in the first round 
        List<Player> missingPlayerList = PlayerHandler.getListOfPlayerWithCondition(missingPlayer, (maxPlayer + 1), Player.PLAYERCONDITION.FREEWIN);
        playerList.addAll(missingPlayerList);
        LOG.debug("added Freewin playerlist");

        //run Tiers in TournamentHandler        
        TournamentHandler.runTiers(playerList);

        //display in better cli
        TournamentHandler.displayTournament();

        //export to JSON
        //saveToJson(TournamentHandler.runTiers(remainingPlayerList, tournament));
    }

}
