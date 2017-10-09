/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class OneonOneController implements Initializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(OneonOneController.class);

    @FXML
    private ListView backlog;
    @FXML
    private Button matchButton;
    @FXML
    private ComboBox combobox1;
    @FXML
    private ComboBox combobox2;
    @FXML
    private Label player2Nr;
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

    private Boolean player2ready = false;

    private final UtilityMethodes funk = new UtilityMethodes();

    ObservableList<String> data = FXCollections.observableArrayList();

    private Enum symbole1 = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //show a text animate the singel player to start the game
        result.setText("Bitte noch Symbol wählen");

        //fill the combox
        combobox1.getItems().addAll(EnumSet.allOf(Enums.Symbole.class));
        combobox2.getItems().addAll(EnumSet.allOf(Enums.Symbole.class));
    }

    @FXML
    private void handleSelectedCombobox1(ActionEvent event) {
        //build new player object 
        Player p = new Player(1, Enums.Playercondition.PLAYER);
        p.setSymbole((Enum) combobox1.getValue());

        //fill Protocoll
        result.setText("lets go");

        //only add to log it the game is not ended
        addToProtocol("Player1: " + p.getSymbole());

        //set the globle enum
        symbole1 = p.getSymbole();

        //move to next player
        nextPlayer();

    }

    @FXML
    private void handleSelectedCombobox2(ActionEvent event) {
        //build new player object 
        Player p = new Player(1, Enums.Playercondition.PLAYER);
        p.setSymbole((Enum) combobox2.getValue());

        //fill Protocoll
        result.setText("lets go");

        //only add to log it the game is not ended
        addToProtocol("Player1: " + p.getSymbole());

    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException {
        //playerready get set true on button pressed
        if (player2ready) {
            fight();
        } else {
            //give out that you are waiting on player input
            LOG.debug("new player input requested");
            result.setText("Bitte noch Symbol wählen");
        }
    }

    private void nextPlayer() {
        //set to defaule
        combobox1.getSelectionModel().clearSelection();

        //set player 2 for ready
        player2ready = true;
    }

    /**
     * Starting the fight
     */
    private void fight() {
        //set to defaule
        combobox1.getSelectionModel().selectFirst();

        //get random ID´s for nicer UI output
        Random random = new Random();
        int Player2ID = random.nextInt((10 - 1) + 1) + 1;

        //building the 2 player objects 
        Player p1 = new Player(Player2ID, Enums.Playercondition.PLAYER);
        Player p2 = new Player(Player2ID, Enums.Playercondition.PLAYER);

        //fight
        Enum infightSymbole1 = null;
        Enum infightSymbole2 = null;

        //change UI
        changePlayerUI(p1, 1);
        roundNr.setText(0 + "");

        //fight
        Enum figtresult = null;
        Enums.Symbole tempsymbole = (Enums.Symbole) infightSymbole1;
        if (tempsymbole.loseAgaist((Enums.Symbole) infightSymbole1, (Enums.Symbole) infightSymbole2)) {
            //player 1 have lost
            figtresult = Enums.Fightstat.LOST;
        } else if (infightSymbole1.equals(infightSymbole2)) {
            //can happend but raw
            figtresult = Enums.Fightstat.DRAW;

            //fight was draw
            //waiting on new user input
            addToProtocol("First Match was a draw");
            addToProtocol("Please select a Symbole and klick match again");

            LOG.debug("First Match was a draw");
            //unset the player ready (this will be set on button press)
            player2ready = false;

            //change player 1 symbole to defauled symbole
            p1.setSymbole(Enums.Symbole.DEFAULT);

            //change UI
            changePlayerUI(p1, 1);

        } else {
            //player 1 have won
            figtresult = Enums.Fightstat.WON;
        }

        //change the UI
        changePlayerUI(p2, 2);

        //fill the protocol
        addToProtocol("Player1: " + p1.getSymbole());
        addToProtocol("Player2: " + p2.getSymbole());
        addToProtocol("Player 1 has: " + figtresult);

        //change UI
        result.setText("Player 1 has: " + figtresult);

        //plot the Protocol
        getProtocol(backlog);

        //populate the debug log
        LOG.debug(backlog.toString());
    }

    /**
     * changing the player in the UI on a given postion
     *
     * @param p player object
     * @param pos postion in the UI
     */
    private void changePlayerUI(Player p, int pos) {
        //get the player symbole
        Enum symbole = p.getSymbole();
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
                player1img.setImage(playerSymbole);
                break;
            case 2:
                player2Nr.setText("" + p.getID());
                player2Name.setText(p.getName());
                player2img.setImage(playerSymbole);
                break;
            default:
                break;
        }

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
