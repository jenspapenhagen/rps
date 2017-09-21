/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

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

    public enum WinAgains {
        FIRE(EnumSet.of(Symbole.ROCK, Symbole.GUN, Symbole.LIGHTNING, Symbole.DEVIL, Symbole.DRAGON, Symbole.WATER, Symbole.AIR)),
        SCISSOR(EnumSet.of(Symbole.FIRE, Symbole.ROCK, Symbole.GUN, Symbole.LIGHTNING, Symbole.DEVIL, Symbole.DRAGON, Symbole.WATER)),
        ROCK(EnumSet.of(Symbole.GUN, Symbole.LIGHTNING, Symbole.DEVIL, Symbole.DRAGON, Symbole.WATER, Symbole.AIR, Symbole.PAPER)),
        GUN(EnumSet.of(Symbole.LIGHTNING, Symbole.DEVIL, Symbole.DRAGON, Symbole.WATER, Symbole.AIR, Symbole.PAPER, Symbole.SPONGE)),
        LIGHTNING(EnumSet.of(Symbole.DEVIL, Symbole.DRAGON, Symbole.WATER, Symbole.AIR, Symbole.PAPER, Symbole.SPONGE, Symbole.WOLF)),
        DEVIL(EnumSet.of(Symbole.DRAGON, Symbole.WATER, Symbole.AIR, Symbole.PAPER, Symbole.SPONGE, Symbole.WOLF, Symbole.TREE)),
        DRAGON(EnumSet.of(Symbole.WATER, Symbole.AIR, Symbole.PAPER, Symbole.SPONGE, Symbole.WOLF, Symbole.TREE, Symbole.HUMAN)),
        WATER(EnumSet.of(Symbole.AIR, Symbole.PAPER, Symbole.SPONGE, Symbole.WOLF, Symbole.TREE, Symbole.HUMAN, Symbole.SNAKE)),
        AIR(EnumSet.of(Symbole.PAPER, Symbole.SPONGE, Symbole.WOLF, Symbole.TREE, Symbole.HUMAN, Symbole.SNAKE, Symbole.SCISSOR)),
        PAPER(EnumSet.of(Symbole.SPONGE, Symbole.WOLF, Symbole.TREE, Symbole.HUMAN, Symbole.SNAKE, Symbole.SCISSOR, Symbole.FIRE)),
        SPONGE(EnumSet.of(Symbole.WOLF, Symbole.TREE, Symbole.HUMAN, Symbole.SNAKE, Symbole.SCISSOR, Symbole.FIRE, Symbole.ROCK)),
        WOLF(EnumSet.of(Symbole.TREE, Symbole.HUMAN, Symbole.SNAKE, Symbole.SCISSOR, Symbole.FIRE, Symbole.ROCK, Symbole.GUN)),
        TREE(EnumSet.of(Symbole.HUMAN, Symbole.SNAKE, Symbole.SCISSOR, Symbole.FIRE, Symbole.ROCK, Symbole.GUN, Symbole.LIGHTNING)),
        HUMAN(EnumSet.of(Symbole.SNAKE, Symbole.SCISSOR, Symbole.FIRE, Symbole.ROCK, Symbole.GUN, Symbole.LIGHTNING, Symbole.DEVIL)),
        SNAKE(EnumSet.of(Symbole.SCISSOR, Symbole.FIRE, Symbole.ROCK, Symbole.GUN, Symbole.LIGHTNING, Symbole.DEVIL, Symbole.DRAGON));

        private EnumSet es;

        public EnumSet getEnumSet() {
            return this.es;
        }

        WinAgains(EnumSet esnumset) {
            this.es = esnumset;
        }
    }

}
