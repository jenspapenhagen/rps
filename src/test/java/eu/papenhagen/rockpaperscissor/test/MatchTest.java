/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.test;

import eu.papenhagen.rockpaperscissor.Service.Fight;
import eu.papenhagen.rockpaperscissor.Entities.Enums;
import eu.papenhagen.rockpaperscissor.Entities.Match;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import eu.papenhagen.rockpaperscissor.Entities.Player;

/**
 *
 * @author jens.papenhagen
 */
public class MatchTest {

    public MatchTest() {
    }

    /**
     * Test of call method, of class Fight.
     */
    @Test
    public void testCall() throws InterruptedException, ExecutionException {

        Player p1 = new Player(5, Enums.Playercondition.PLAYER);
        Player p2 = new Player(3, Enums.Playercondition.PLAYER);

        p1.setSymbole(Enums.Symbole.PAPER);
        p2.setSymbole(Enums.Symbole.SCISSOR);

        ExecutorService es = Executors.newSingleThreadExecutor();
        Match match = new Match(1, p1, p2);
        
        Fight game = new Fight(1, match);
        //give back the loser
        Future<Player> result10 = es.submit(game);

        es.shutdown();
        
        assertThat(p1.getID()).isEqualTo(result10.get().getID()).as("Vergleich player1 id zu gewinner");
        assertThat(p2.getID()).isNotEqualTo(result10.get().getID()).as("Vergleich player2 id zu gewinner");
    }

}
