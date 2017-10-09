/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.Entities;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

/**
 *
 * @author jens.papenhagen
 */
public class Tier {

    @Getter
    @Setter
    private int tierId;
    
    @Getter
    @Setter
    private List<Match> matchList;

    public Tier(int tierId) {
        this.matchList = new ArrayList<>();
        this.tierId = tierId;
    }

}
