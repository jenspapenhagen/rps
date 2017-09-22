/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors;

import java.util.EnumSet;

/**
 *
 * @author jens.papenhagen
 */
public class Enums {

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
        SNAKE;

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
                default:
                    return false;                    
            }

        }
    };

    public enum Fightstat {
        WON,
        LOST,
        DRAW
    };

    public enum Playercondition {
        PLAYER,
        FREEWIN,
        DISQUALIFIZIED
    };

   
}
