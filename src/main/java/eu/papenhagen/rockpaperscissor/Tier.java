/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jens.papenhagen
 */
public class Tier {

    private int tierId;
    private List<Match> matchList;

    public Tier(int tierId) {
        this.matchList = new ArrayList<>();
        this.tierId = tierId;
    }

    public int getTierId() {
        return tierId;
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public void setTierId(int tierId) {
        this.tierId = tierId;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }
    

}
