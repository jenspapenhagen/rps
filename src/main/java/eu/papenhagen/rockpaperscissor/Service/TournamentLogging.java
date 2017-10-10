/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.Service;

import eu.papenhagen.rockpaperscissor.Entities.Tier;
import eu.papenhagen.rockpaperscissor.Main;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

/**
 * extern Methode for logging the tournament
 * @author jens.papenhagen
 */
public class TournamentLogging {
   @Getter
   private List<Tier> tournament;

    public TournamentLogging() {
        this.tournament = new ArrayList<>(Main.getCountOfTiers());
    }
   
   
   
    
}
