/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import schnickschnack.*;
import schnickschnack.Player;

import static org.junit.Assert.*;

/**
 *
 * @author jens.papenhagen
 */
public class MatchTest {

    public MatchTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    /**
     * Test of call method, of class Match.
     */
    @Test
    public void testCall() throws InterruptedException, ExecutionException {

        Player p1 = new Player(5, Enums.Playercondition.PLAYER);
        Player p2 = new Player(3, Enums.Playercondition.PLAYER);

        ExecutorService es = Executors.newSingleThreadExecutor();

        Match game = new Match(1, p1, p2);
        Future<Player> result10 = es.submit(game);

        es.shutdown();
        assertThat(p1.getPlayerID()).isEqualTo(result10.get().getPlayerID()).as("Vergleich player1 id zu gewinner");
        assertThat(p2.getPlayerID()).isEqualTo(result10.get().getPlayerID()).as("Vergleich player2 id zu gewinner");
    }

    

}
