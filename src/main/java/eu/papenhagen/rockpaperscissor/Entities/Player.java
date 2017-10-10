/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.Entities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import org.slf4j.LoggerFactory;
import lombok.*;

/**
 * mostly pojo class have a random player name generator
 *
 * @author jens.papenhagen
 */
public final class Player {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Player.class);

    @Getter
    @Setter
    private int ID;
    
    @Setter
    @Getter
    private String name;
    
    @Setter
    @Getter
    private Enum symbole;

    @Setter
    @Getter
    private Enum condition;
    
    @Setter
    @Getter
    private int won;

    public static enum Symbole {
        FIRE,
        SCISSOR,
        ROCK,
        GUN,
        LIGHTNING,
        DEVIL,
        DRAGON,
        WATER,
        AIR,
        PAPER,
        SPONGE,
        WOLF,
        TREE,
        HUMAN,
        SNAKE,
        DEFAULT;

        public boolean loseAgaist(Symbole e, Symbole s) {
            switch (e) {
                case FIRE:
                    return EnumSet.of(ROCK, GUN, LIGHTNING, DEVIL, DRAGON, WATER, AIR).contains(s);
                case SCISSOR:
                    return EnumSet.of(FIRE, ROCK, GUN, LIGHTNING, DEVIL, DRAGON, WATER).contains(s);
                case ROCK:
                    return EnumSet.of(GUN, LIGHTNING, DEVIL, DRAGON, WATER, AIR, PAPER).contains(s);
                case GUN:
                    return EnumSet.of(LIGHTNING, DEVIL, DRAGON, WATER, AIR, PAPER, SPONGE).contains(s);
                case LIGHTNING:
                    return EnumSet.of(DEVIL, DRAGON, WATER, AIR, PAPER, SPONGE, WOLF).contains(s);
                case DEVIL:
                    return EnumSet.of(DRAGON, WATER, AIR, PAPER, SPONGE, WOLF, TREE).contains(s);
                case DRAGON:
                    return EnumSet.of(WATER, AIR, PAPER, SPONGE, WOLF, TREE, HUMAN).contains(s);
                case WATER:
                    return EnumSet.of(AIR, PAPER, SPONGE, WOLF, TREE, HUMAN, SNAKE).contains(s);
                case AIR:
                    return EnumSet.of(PAPER, SPONGE, WOLF, TREE, HUMAN, SNAKE, SCISSOR).contains(s);
                case PAPER:
                    return EnumSet.of(SPONGE, WOLF, TREE, HUMAN, SNAKE, SCISSOR, FIRE).contains(s);
                case SPONGE:
                    return EnumSet.of(WOLF, TREE, HUMAN, SNAKE, SCISSOR, FIRE, ROCK).contains(s);
                case WOLF:
                    return EnumSet.of(TREE, HUMAN, SNAKE, SCISSOR, FIRE, ROCK, GUN).contains(s);
                case TREE:
                    return EnumSet.of(HUMAN, SNAKE, SCISSOR, FIRE, ROCK, GUN, LIGHTNING).contains(s);
                case HUMAN:
                    return EnumSet.of(SNAKE, SCISSOR, FIRE, ROCK, GUN, LIGHTNING, DEVIL).contains(s);
                case SNAKE:
                    return EnumSet.of(SCISSOR, FIRE, ROCK, GUN, LIGHTNING, DEVIL, DRAGON).contains(s);
                case DEFAULT:
                    return false;
                default:
                    return false;
            }

        }
    };


    public enum Playercondition {
        PLAYER,
        FREEWIN,
        DISQUALIFIZIED
    };
    
    
    
    public Player(int ID, Enum condition) {
        this.name = getRandomName();
        this.ID = ID;
        this.condition = condition;
        this.symbole = getRandomSymbole();
    }

    public Enum getRandomSymbole() {
        //so not select the default Enum
        int indexer = new Random().nextInt(Symbole.values().length - 1);

        Symbole output = Symbole.values()[indexer];
        //no deauflt better froce it to AIR
        if(output.equals(Symbole.DEFAULT)){
            output = Symbole.AIR;
        }
        return output;
    }

    public String getRandomName() {
        String output = "";
        List<String> Namelist = new ArrayList<>();
        Namelist.add("Jens");
        Namelist.add("Pascal");
        Namelist.add("Jacob");
        Namelist.add("Hassan");
        Namelist.add("Joerg");
        Namelist.add("Malte");
        Namelist.add("Oliver");
        Namelist.add("Wolfgang");
        Namelist.add("Lucas");
        
        //get the resources as stream
//        try {
//            Namelist = Files.readAllLines(Paths.get(this.getClass().getResource("/resources/forename.txt").toURI()), Charset.defaultCharset());
//        } catch (URISyntaxException | IOException ex) {
//            LOG.error(ex.getMessage());
//        }

        //get a random line
        int randomline = new Random().nextInt(Namelist.size());

        output = Namelist.get(randomline);

        return output;

    }

}
