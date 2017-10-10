/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.assertj.core.api.Assertions.*;
import eu.papenhagen.rockpaperscissor.Entities.Enums;
import org.junit.Test;
import eu.papenhagen.rockpaperscissor.Service.Fight;
import eu.papenhagen.rockpaperscissor.Entities.Match;
import eu.papenhagen.rockpaperscissor.Entities.Player;

/**
 *
 * @author jens.papenhagen
 */
public class RundenTest {

    public RundenTest() {
    }

    /**
     * Test of fightround method, of class Runden.
     */
    @Test
    public void testFightround() throws InterruptedException, ExecutionException {

        Player p1 = new Player(5, Player.Playercondition.FREEWIN);
        Player p2 = new Player(3, Player.Playercondition.PLAYER);

        p1.setSymbole(Player.Symbole.PAPER);
        p2.setSymbole(Player.Symbole.PAPER);

        ExecutorService es = Executors.newSingleThreadExecutor();

        Match match = new Match(1, p1, p2);

        Fight game = new Fight(1, match);
        //give back the loser
        Future<Player> result10 = es.submit(game);

        es.shutdown();
        assertThat(p2.getID()).isNotEqualTo(result10.get().getID()).as("Vergleich player2 id zu gewinner");

        assertThat(result10.get().getCondition()).isEqualTo(p1.getCondition()).as("Vergleich Playercondition");

    }

}
