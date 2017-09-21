/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
    public void testFightround() {

         String output = new Runden(CONSTANS.SYMBOLE.SCHERE.toString(), CONSTANS.SYMBOLE.SCHERE.toString()).fightround();
         assertThat(output).isEqualToIgnoringCase("Player 1 gewinnt").as("Player 1 gewinnt");
         assertThat(output).isEqualToIgnoringCase("Player 2 gewinnt").as("Player 2 gewinnt");

        
    }
    
}
