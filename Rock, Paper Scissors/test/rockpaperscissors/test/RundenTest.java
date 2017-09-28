/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rockpaperscissors.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.assertj.core.api.Assertions.assertThat;
import rockpaperscissors.Enums;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rockpaperscissors.Fight;
import rockpaperscissors.Player;

/**
 *
 * @author jens.papenhagen
 */
public class RundenTest {
    
    public RundenTest() {
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
     * Test of fightround method, of class Runden.
     */
    @Test
    public void testFightround() throws InterruptedException, ExecutionException {
        //only log the fight to the debug log
        boolean calm = true;

        Player p1 = new Player(5, Enums.Playercondition.FREEWIN);
        Player p2 = new Player(3, Enums.Playercondition.PLAYER);
        
        p1.setPlayerSymbole(Enums.Symbole.PAPER);
        p2.setPlayerSymbole(Enums.Symbole.PAPER);
        
        ExecutorService es = Executors.newSingleThreadExecutor();

        Fight game = new Fight(1, p1, p2, calm);
        //give back the loser
        Future<Player> result10 = es.submit(game);

        es.shutdown();
        assertThat(p2.getPlayerID()).isNotEqualTo(result10.get().getPlayerID()).as("Vergleich player2 id zu gewinner");
        
        
        assertThat( result10.get().getPlayerCondtion() ).isEqualTo(p1.getPlayerCondtion()).as("Vergleich Playercondition");
       
    }
    
}
