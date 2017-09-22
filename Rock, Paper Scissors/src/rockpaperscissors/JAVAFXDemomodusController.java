/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors;

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

    public UtilityMethodes funk = new UtilityMethodes();

    public int playerPostion = 1;

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
    private void handleMatchButton(ActionEvent event) throws InterruptedException, Exception {
        LOG.debug("Random Match");
        fight();
    }

    /**
     * starting the fight
     */
    public void fight() {
        Ruler ruler = new Ruler();

        Random random = new Random();
        int Player1ID = random.nextInt((10 - 1) + 1) + 1;
        int Player2ID = random.nextInt((10 - 1) + 1) + 1;
        LOG.debug("Player1ID" + Player1ID);
        LOG.debug("Player2ID" + Player2ID);

        if (Player1ID == Player2ID) {
            Player2ID = Player1ID + 1;
            LOG.debug("Change Player ID2 to " + Player2ID);
        }

        //build the player
        Player p1 = new Player(Player1ID, Enums.Playercondition.PLAYER);
        Player p2 = new Player(Player2ID, Enums.Playercondition.PLAYER);

        //clean the protocol
        getCleanProtocol(backlog);

        try {
            //change the player symboles
            changePlayerUI(p1, 1);
            changePlayerUI(p2, 2);
            //reset the round counter
            roundNr.setText("");

            //fight
            Enum resultFromfight = ruler.comparingSymboles(p1.getPlayerSymbole(), p2.getPlayerSymbole());

            //fill the Protocol
            addToProtocol("Player1: " + p1.getPlayerSymbole());
            addToProtocol("Player2: " + p2.getPlayerSymbole());
            addToProtocol("Ausgabe normal Fight: " + resultFromfight);

            //fight again if the fight was a draw
            if (resultFromfight.equals(Enums.Fightstat.DRAW)) {
                addToProtocol("First Match was a draw, NOW Round 1");
                changePlayerUI(p1, 1);
                changePlayerUI(p2, 2);
                resultFromfight = figthinground(p1, p2);
            }
            //removce the lost player
            toRemoveID(resultFromfight, p1, p2);
            //show the fight result  
            result.setText("Player 1 has " + resultFromfight);
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }
        //draw the complett protocol
        getProtocol(backlog);
    }

    public Enum figthinground(Player p1, Player p2) throws IOException {
        Enum fightresult = null;
        Ruler ruler = new Ruler();

        int maxrounds = 5;
        for (int rounds = 1; rounds <= maxrounds; rounds++) {
            //change the behavor of the player
            Enum player1symbole = ruler.getBehavor(p1.getPlayerSymbole(), p2.getPlayerSymbole());
            Enum player2symbole = ruler.getBehavor(p2.getPlayerSymbole(), p1.getPlayerSymbole());

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
            if (rounds == maxrounds) {
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

    public void toRemoveID(Enum result, Player p1, Player p2) {
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
            removePlayerID = p1.getPlayerID();  
        }

        //show the player id, whitch has lost the game
        changeRemovePlayerIDLable("" + removePlayerID);
    }

    public void changePlayerUI(Player p, int pos) throws IOException {
        Enum symbole = p.getPlayerSymbole();
        Image playerSymbole = funk.givebackImg(symbole);

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

    public void changeRemovePlayerIDLable(String input) {
        removeID.setText(input);
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
