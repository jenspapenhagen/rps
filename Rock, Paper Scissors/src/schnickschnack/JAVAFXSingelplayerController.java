/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * FXML Controller class
 *
 * @author jens.papenhagen
 */
public class JAVAFXSingelplayerController implements Initializable {

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

    public String player1symbol = "";

    public Boolean inFight = false;

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
    private void handlePapierButton(ActionEvent event) throws InterruptedException, Exception {
        addToProtocol("Player1: Papier");
        Player p = new Player(1, ENUMS.PLAYERCONDITION.PLAYER);
        p.setPlayerSymbole(ENUMS.SYMBOLE.PAPER);
        changePlayerSymbolImg(p, 1);
    }

    @FXML
    private void handleSteinButton(ActionEvent event) throws InterruptedException, Exception {
        addToProtocol("Player1: Stein");
        Player p = new Player(2, ENUMS.PLAYERCONDITION.PLAYER);
        p.setPlayerSymbole(ENUMS.SYMBOLE.STONE);
        changePlayerSymbolImg(p, 1);
    }

    @FXML
    private void handleSchereButton(ActionEvent event) throws InterruptedException, Exception {
        addToProtocol("Player1: Schere");
        Player p = new Player(3, ENUMS.PLAYERCONDITION.PLAYER);
        p.setPlayerSymbole(ENUMS.SYMBOLE.SCISSOR);
        changePlayerSymbolImg(p, 1);
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException, Exception {
        Random random = new Random();
        int randomPlayerNr2 = random.nextInt((10 - 1) + 1) + 1;

        if (player1symbol.length() != 0) {
            getCleanProtocol(backlog); 
            fight(randomPlayerNr2);
        } else {
            result.setText("Bitte noch Symbol wählen");
            inFight = true;
        }
    }

    public void fight(int ID2) throws InterruptedException {
        matchButton.setDisable(true);
        Random random = new Random();
        int Player1ID = 4;
        int Player2ID = random.nextInt((10 - 1) + 1) + 1;
        Player p1 = new Player(Player1ID, ENUMS.PLAYERCONDITION.PLAYER);
        Player p2 = new Player(Player2ID, ENUMS.PLAYERCONDITION.PLAYER);
        Ruler ruler = new Ruler();

        try {
            Enum symbole1 = p1.getPlayerSymbole();
            Enum symbole2 = null;
            addToProtocol("Player1: " + symbole1);

            if (inFight) {
                symbole2 = ruler.getVerhalten(symbole1, ENUMS.SYMBOLE.PAPER);

                if (selectedPapier.isPressed()) {
                    symbole1 = ENUMS.SYMBOLE.PAPER;
                }
                if (selectedStein.isPressed()) {
                    symbole1 = ENUMS.SYMBOLE.STONE;
                }
                if (selectedSchere.isPressed()) {
                    symbole1 = ENUMS.SYMBOLE.SCISSOR;
                }

            } else {
                getCleanProtocol(backlog); 
                changePlayerSymbolImg(p2, 2);
                symbole2 = p2.getPlayerSymbole();
            }
            changePlayerSymbolImg(p1, 1);
            roundNr.setText(0 + "");

            //fight
            addToProtocol("Player2: " + symbole2);
            Enum figtresult = ruler.comparingSymboles(symbole1, symbole2);
            changePlayerSymbolImg(p2, 2);
            addToProtocol("Ausgabe Fight: " + figtresult);

            //fight was draw waiting on new user input
            if (figtresult.equals(ENUMS.FIGHTSTAT.DRAW)) {
                addToProtocol("First Match was a draw");
                addToProtocol("Please select a Symbole and klick match again");
                System.out.println("First Match was a draw");

                player1symbol = "";
                inFight = true;
            }

            result.setText("Player 1: " + figtresult);

        } catch ( IOException ex) {
            Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        getProtocol(backlog);
        matchButton.setDisable(false);
        playerPostion = 1;
    }

    public void changePlayerSymbolImg(Player p, int playerPostion) throws IOException {
        Enum symbole = p.getPlayerSymbole();
        Image playerSymbole = funk.givebackImg(symbole);

        switch (playerPostion) {
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
