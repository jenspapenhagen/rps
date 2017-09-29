/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author jens.papenhagen
 */
public class JAVAFXDemomodusController implements Initializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(JAVAFXDemomodusController.class);

    @FXML
    private ListView backlog;
    @FXML
    private Label player1Nr;
    @FXML
    private Label player2Nr;
    @FXML
    private Label player1Name;
    @FXML
    private Label player2Name;
    @FXML
    private ImageView player1img;
    @FXML
    private ImageView player2img;
    @FXML
    private Label result;
    @FXML
    private Label roundNr;
    @FXML
    private Label removeID;

    private int maxrounds = 5;

    private UtilityMethodes funk = new UtilityMethodes();

    ObservableList<String> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.debug("staring the fight");
        fight();
    }

    @FXML
    private void handleMatchButton(ActionEvent event) {
        LOG.debug("Random Match");
        fight();
    }

    /**
     * starting the fight
     */
    private void fight() {
        //get the ruler to determinate the winner or loser
        Ruler ruler = new Ruler();

        //generate 2 random numbers
        Random random = new Random();
        int ID1 = random.nextInt((10 - 1) + 1) + 1;
        int ID2 = random.nextInt((10 - 1) + 1) + 1;
        LOG.debug("Player1ID" + ID1);
        LOG.debug("Player2ID" + ID2);

        //if the random ID generator do not make to diff. numbers
        if (ID1 == ID2) {
            ID2 = ID1 + 1;
            LOG.debug("Change Player ID2 to " + ID2);
        }

        //build the two player objects
        Player p1 = new Player(ID1, Enums.Playercondition.PLAYER);
        Player p2 = new Player(ID2, Enums.Playercondition.PLAYER);

        //clean the protocol
        getCleanProtocol(backlog);

        //change the player symboles
        changePlayerUI(p1, 1);
        changePlayerUI(p2, 2);

        //reset the round counter in the UI
        roundNr.setText("");

        //fight
        Enum resultFromfight = null;
        if (ruler.comparingBigSymboleRange((Enums.Symbole) p1.getPlayerSymbole(), (Enums.Symbole) p2.getPlayerSymbole())) {
            resultFromfight = Enums.Fightstat.LOST;
        } else if (p1.getPlayerSymbole().equals(p2.getPlayerSymbole())) {
            resultFromfight = Enums.Fightstat.DRAW;
        } else {
            resultFromfight = Enums.Fightstat.WON;
        }

        //fill the Protocol
        addToProtocol("Player1: " + p1.getPlayerSymbole());
        addToProtocol("Player2: " + p2.getPlayerSymbole());
        addToProtocol("Ausgabe normal Fight: " + resultFromfight);

        //fight again if the fight was a draw
        if (resultFromfight.equals(Enums.Fightstat.DRAW)) {
            addToProtocol("First Match was a draw, NOW Round 1");
            //change the UI
            changePlayerUI(p1, 1);
            changePlayerUI(p2, 2);

            //retrun the Loser
            resultFromfight = figthinground(p1, p2);
        }

        //removce the lost player
        showLostPlayer(resultFromfight, p1, p2);

        //show the fight result  
        result.setText("Player 1 has: " + resultFromfight);

        //draw the complett protocol
        getProtocol(backlog);
    }

    /**
     * if we have a draw fight again
     *
     * @param p1
     * @param p2
     * @return
     */
    private Enum figthinground(Player p1, Player p2) {
        Enum fightresult = null;
        //get the ruler to determinate the winner or loser
        Ruler ruler = new Ruler();

        //get new behavor for next round
        Behavor behv = new Behavor();

        //the max round count
        int maxr = this.maxrounds;

        //start this rounds
        for (int rounds = 1; rounds <= maxr; rounds++) {
            //change the behavor of the player
            Enum player1symbole = behv.getBehavor(p1.getPlayerSymbole(), p2.getPlayerSymbole());
            Enum player2symbole = behv.getBehavor(p2.getPlayerSymbole(), p1.getPlayerSymbole());

            //set the new player symbole
            p1.setPlayerSymbole(player1symbole);
            p2.setPlayerSymbole(player2symbole);

            //fill the Protocol
            addToProtocol("Player1: " + player1symbole);
            addToProtocol("Player2: " + player2symbole);

            //change the player symboles
            changePlayerUI(p1, 1);
            changePlayerUI(p2, 2);

            //fight
            fightresult = ruler.comparingSymboles(player1symbole, player2symbole);

            //fill the Protocol
            addToProtocol("Runden " + rounds + " Ergebnis: " + fightresult);

            //fight again if this fight was a draw, too.
            if (!fightresult.equals(Enums.Fightstat.DRAW)) {
                changePlayerUI(p1, 1);
                changePlayerUI(p2, 2);
                break;
            }

            //check if max round counter is not reached
            if (rounds == maxr) {
                //froce win
                LOG.debug("froce win");
                fightresult = Enums.Fightstat.WON;
                break;
            }

            //change the round counter
            roundNr.setText(" " + rounds);
        }

        return fightresult;
    }

    /**
     * giveback the Lost player in the UI
     *
     * @param result
     * @param p1
     * @param p2
     */
    private void showLostPlayer(Enum result, Player p1, Player p2) {
        Integer removePlayerID = 0;

        try {
            if (result.equals(Enums.Fightstat.WON)) {
                removePlayerID = p2.getPlayerID();
            } else {
                removePlayerID = p1.getPlayerID();
            }
        } catch (NullPointerException ex) {
            //froce win
            LOG.debug("froce win");
            LOG.debug(ex.getMessage());
            removePlayerID = p1.getPlayerID();
        }

        //show the player id, whitch has lost the game
        changeRemovePlayerIDLable("" + removePlayerID);
    }

    /**
     * changing the player in the UI
     *
     * @param p
     * @param pos
     */
    private void changePlayerUI(Player p, int pos) {
        //get the player symbole
        Enum symbole = p.getPlayerSymbole();
        //change the old Imaage
        Image playerSymbole = null;
        try {
            playerSymbole = funk.givebackImg(symbole);
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }
        
        //witch postion have to be change
        switch (pos) {
            case 1:
                player1Nr.setText("" + p.getPlayerID());
                player1Name.setText(p.getPlayerName());
                player1img.setImage(playerSymbole);
                break;

            case 2:
                player2Nr.setText("" + p.getPlayerID());
                player2Name.setText(p.getPlayerName());
                player2img.setImage(playerSymbole);
                break;
            default:
                break;
        }
    }

    private void changeRemovePlayerIDLable(String input) {
        removeID.setText(input);
    }

    private void addToProtocol(String input) {
        data.add(input);
    }

    private void getProtocol(ListView lv) {
        lv.setItems(data);
    }

    private void getCleanProtocol(ListView lv) {
        data.clear();
        lv.setItems(data);
    }

}
