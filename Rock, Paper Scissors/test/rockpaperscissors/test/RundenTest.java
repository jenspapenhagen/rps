///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package rockpaperscissors.test;
//
//import rockpaperscissors.Rounds;
//import rockpaperscissors.Enums;
//import static org.assertj.core.api.Assertions.assertThat;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author jens.papenhagen
// */
//public class RundenTest {
//    
//    public RundenTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of fightround method, of class Runden.
//     */
//    @Test
//    public void testFightround() {
//         Enum output = new Rounds(Enums.Symbole.SCISSOR, Enums.Symbole.PAPER).fightround();
//         
//         assertThat(output).isEqualTo(Enums.Fightstat.WON).as("Player 1 gewinnt");
//         assertThat(output).isNotEqualTo(Enums.Fightstat.WON).as("Player 2 gewinnt");
//       
//    }
//    
//}
