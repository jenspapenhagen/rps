/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.papenhagen.rockpaperscissor.Entities;

/**
 *
 * @author jens.papenhagen
 */

import lombok.*;

public class ExportPlayer {
    
    @Getter
    private String name;
    
    @Getter
    private String id;
    
    @Getter
    private int seed;

    public void setName(Player p) {
        this.name = p.getName();
    }

    public void setId(Player p) {
        this.id = p.getName().toLowerCase();
    }

    public void setSeed(Player p) {
        this.seed = p.getID();
    }
    
    
    
}
