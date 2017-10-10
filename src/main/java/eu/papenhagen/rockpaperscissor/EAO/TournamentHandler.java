/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.EAO;

import eu.papenhagen.rockpaperscissor.Entities.Tier;
import java.util.Collections;
import java.util.List;

/**
 * Here A Methoes for handling the Tournament
 * @author jens.papenhagen
 */
public class TournamentHandler {

    /**
     * Try to buildMatches a tournament grid in console. using a List of Tier
     * Objects for this. Winner on the top and than to the button
     *
     * @param tournament
     */
    public static void displayTournament(List<Tier> tournament) {
        //go from last to frist Tier
        for (int i = tournament.size() - 1; i >= 0; i--) {
            //build a emptry string witgh 100 spaces
            StringBuilder spaces = new StringBuilder();
            spaces.append(String.join("", Collections.nCopies(200, " ")));
            int maxl = spaces.length();
            //redruce this string every round
            spaces.delete((i * 10) + 30, maxl);
            //output the number of the tier
            System.out.printf("%s\t%s", tournament.get(i).getTierId() + 1, spaces.toString());
            //setting the winner on the top
            for (int j = 0; j < tournament.get(i).getMatchList().size(); j++) {
                System.out.printf("\t%s", String.format("%s", tournament.get(i).getMatchList().get(j).getWinner().getID()));
            }
            //line break
            System.out.println("");
            //adding all "ID vs ID " to gether and plot it
            System.out.printf("%s\t%s", tournament.get(i).getTierId() + 1, spaces.toString());
            for (int j = 0; j < tournament.get(i).getMatchList().size(); j++) {
                System.out.printf("%s\t", String.format(" %s vs. %s", tournament.get(i).getMatchList().get(j).getPlayer1().getID(), tournament.get(i).getMatchList().get(j).getPlayer2().getID()));
            }
            //line break with 200 -´s
            System.out.println("");
            System.out.println(String.join("", Collections.nCopies(200, "-")));
        }
    }
    
}
