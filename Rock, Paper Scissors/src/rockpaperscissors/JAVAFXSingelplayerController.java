/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class JAVAFXSingelplayerController implements Initializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(JAVAFXDemomodusController.class);

    @FXML
    private ListView backlog;
    @FXML
    private Button matchButton;
    @FXML
    private JFXButton selectedPapier;
    @FXML
    private JFXButton selectedStein;
    @FXML
    private JFXButton selectedSchere;
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

    public Boolean player1ready = false;

    public Boolean stillInFight = false;

    public int playerPostion = 1;

    public UtilityMethodes funk = new UtilityMethodes();

    ObservableList<String> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        result.setText("Bitte noch Symbol wählen");
    }

    @FXML
    private void handlePapierButton(ActionEvent event) {
        Player p = new Player(1, Enums.Playercondition.PLAYER);
        p.setPlayerSymbole(Enums.Symbole.PAPER);
        player1ready = true;

        //fill Protocoll and change UI
        changePlayerUI(p, 1);
        result.setText("lets go");
        addToProtocol("Player1: Papier");
        LOG.debug("Player1: Papier");
    }

    @FXML
    private void handleSteinButton(ActionEvent event) {

        Player p = new Player(2, Enums.Playercondition.PLAYER);
        p.setPlayerSymbole(Enums.Symbole.ROCK);
        player1ready = true;

        //fill Protocoll and change UI
        changePlayerUI(p, 1);
        result.setText("lets go");
        addToProtocol("Player1: Stein");
        LOG.debug("Player1: Stein");
    }

    @FXML
    private void handleSchereButton(ActionEvent event) {
        Player p = new Player(3, Enums.Playercondition.PLAYER);
        p.setPlayerSymbole(Enums.Symbole.SCISSOR);
        player1ready = true;

        //fill Protocoll and change UI
        changePlayerUI(p, 1);
        result.setText("lets go");
        addToProtocol("Player1: Schere");
        LOG.debug("Player1: Schere");
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException {
        if (player1ready) {
            //clean the protocol and start the fight
            getCleanProtocol(backlog);
            fight();
        } else {
            LOG.debug("new player input requested");
            result.setText("Bitte noch Symbol wählen");
            stillInFight = true;
        }
    }

    /**
     * Starting the fight
     */
    public void fight() {
        //disable button in fight
        matchButton.setDisable(true);

        //building the 2 player objects
        Random random = new Random();
        int Player1ID = 4;
        int Player2ID = random.nextInt((10 - 1) + 1) + 1;

        Player p1 = new Player(Player1ID, Enums.Playercondition.PLAYER);
        Player p2 = new Player(Player2ID, Enums.Playercondition.PLAYER);
        Ruler ruler = new Ruler();

        //get new behavor for next round
        Behavor behv = new Behavor();

        //fight
        Enum symbole1 = p1.getPlayerSymbole();
        Enum symbole2 = null;
        addToProtocol("Player1: " + symbole1);

        if (stillInFight) {
            symbole2 = behv.getBehavor(symbole1, Enums.Symbole.PAPER);

            //check buttons are pressed
            if (selectedPapier.isPressed()) {
                symbole1 = Enums.Symbole.PAPER;
            }
            if (selectedStein.isPressed()) {
                symbole1 = Enums.Symbole.ROCK;
            }
            if (selectedSchere.isPressed()) {
                symbole1 = Enums.Symbole.SCISSOR;
            }

        } else {
            getCleanProtocol(backlog);
            changePlayerUI(p2, 2);
            symbole2 = p2.getPlayerSymbole();
        }
        changePlayerUI(p1, 1);
        roundNr.setText(0 + "");

        //fight
        Enum figtresult = ruler.comparingSymboles(symbole1, symbole2);
        changePlayerUI(p2, 2);
        addToProtocol("Player2: " + symbole2);
        addToProtocol("Player 1 has: " + figtresult);

        //fight was draw
        //waiting on new user input
        if (figtresult.equals(Enums.Fightstat.DRAW)) {
            addToProtocol("First Match was a draw");
            addToProtocol("Please select a Symbole and klick match again");
            LOG.debug("First Match was a draw");

            player1ready = false;
            stillInFight = true;
        }

        addToProtocol("Player 1 has: " + figtresult);
        result.setText("Player 1 has: " + figtresult);

        getProtocol(backlog);
        LOG.debug(backlog.toString());
        matchButton.setDisable(false);
    }

    /**
     * changing the player in the UI
     *
     * @param p
     * @param pos
     */
    public void changePlayerUI(Player p, int pos) {
        Enum symbole = p.getPlayerSymbole();
        Image playerSymbole = null;
        try {
            playerSymbole = funk.givebackImg(symbole);
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }

        switch (pos) {
            case 1:
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

    public void addToProtocol(String input) {
        data.add(input);
    }

    public void getProtocol(ListView lv) {
        lv.setItems(data);
    }

    public void getCleanProtocol(ListView lv) {
        data.clear();
        lv.setItems(data);
    }
}
