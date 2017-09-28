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

    private Enum symbole1 = null;

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
        symbole1 = p.getPlayerSymbole();
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
        symbole1 = p.getPlayerSymbole();
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
        symbole1 = p.getPlayerSymbole();
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException {
        //playerready get set true on button pressed
        if (player1ready) {
            //clean the protocol and start the fight
            getCleanProtocol(backlog);
            fight();
        } else {
            //give out that you are waiting on player input
            LOG.debug("new player input requested");
            result.setText("Bitte noch Symbol wählen");
            stillInFight = true;
        }
    }

    /**
     * Starting the fight
     */
    public void fight() {
        //disable match button in fight
        matchButton.setDisable(true);

        //get random ID´s for nicer UI output
        Random random = new Random();
        int Player1ID = 4;
        int Player2ID = random.nextInt((10 - 1) + 1) + 1;

        //building the 2 player objects
        Player p1 = new Player(Player1ID, Enums.Playercondition.PLAYER);
        Player p2 = new Player(Player2ID, Enums.Playercondition.PLAYER);
        Ruler ruler = new Ruler();

        //get new behavor for next round
        Behavor behv = new Behavor();

        //fight
        Enum infightSymbole1 = null;
        Enum infightSymbole2 = null;
        addToProtocol("Player1: " + infightSymbole1);
        
        //check still in fight or the game starts again
        if (stillInFight) {
            //new symbole form player 1
            infightSymbole1 = this.symbole1;
            //new symbole for player 2
            infightSymbole2 = behv.getBehavor(infightSymbole1, Enums.Symbole.PAPER);
        } else {
            //start new and clean the Protocol
            getCleanProtocol(backlog);
            //change the UI
            changePlayerUI(p2, 2);

            //get both symboles
            infightSymbole1 = this.symbole1;
            infightSymbole2 = p2.getPlayerSymbole();
        }

        //set the given symbole
        p1.setPlayerSymbole(infightSymbole1);

        //change UI
        changePlayerUI(p1, 1);
        roundNr.setText(0 + "");

        //fight
        Enum figtresult = null;
        if (ruler.comparingBigSymboleRange((Enums.Symbole) infightSymbole1, (Enums.Symbole) infightSymbole2)) {
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
            player1ready = false;
            //the game is still running to NOT restart it
            stillInFight = true;
        } else {
            //player 1 have won
            figtresult = Enums.Fightstat.WON;
        }

        //change the UI
        changePlayerUI(p2, 2);

        //fill the protocol
        addToProtocol("Player1: " + infightSymbole1);
        addToProtocol("Player2: " + infightSymbole2);
        addToProtocol("Player 1 has: " + figtresult);

        //change UI
        result.setText("Player 1 has: " + figtresult);

        //plot the Protocol
        getProtocol(backlog);
        //populate the debug log
        LOG.debug(backlog.toString());
        //now the match button can be used again
        matchButton.setDisable(false);
    }

    /**
     * changing the player in the UI on a given postion
     *
     * @param p
     * @param pos
     */
    public void changePlayerUI(Player p, int pos) {
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
