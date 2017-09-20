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
        Player p = new Player(1, CONSTANS.PLAYERCONDITION.PLAYER.toString());
        p.setPlayerSymbole(CONSTANS.SYMBOLE.PAPIER.toString());
        player1symbol = changePlayerSymbolImg(p, 1);
    }

    @FXML
    private void handleSteinButton(ActionEvent event) throws InterruptedException, Exception {
        addToProtocol("Player1: Stein");
        Player p = new Player(2, CONSTANS.PLAYERCONDITION.PLAYER.toString());
        p.setPlayerSymbole(CONSTANS.SYMBOLE.STEIN.toString());
        player1symbol = changePlayerSymbolImg(p, 1);
    }

    @FXML
    private void handleSchereButton(ActionEvent event) throws InterruptedException, Exception {
        addToProtocol("Player1: Schere");
        Player p = new Player(3, CONSTANS.PLAYERCONDITION.PLAYER.toString());
        p.setPlayerSymbole(CONSTANS.SYMBOLE.SCHERE.toString());
        player1symbol = changePlayerSymbolImg(p, 1);
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException, Exception {
        Random random = new Random();
        int randomPlayerNr2 = random.nextInt((10 - 1) + 1) + 1;

        if (player1symbol.length() != 0) {
            getCleanProtocol(backlog); 
            fight(player1symbol, randomPlayerNr2);
        } else {
            result.setText("Bitte noch Symbol wählen");
            inFight = true;
        }
    }

    public void fight(String _playerSymbol, int _playerID2) throws InterruptedException {
        matchButton.setDisable(true);
        Random random = new Random();
        int Player1ID = 4;
        int Player2ID = random.nextInt((10 - 1) + 1) + 1;
        Player p1 = new Player(Player1ID, CONSTANS.PLAYERCONDITION.PLAYER.toString());
        Player p2 = new Player(Player2ID, CONSTANS.PLAYERCONDITION.PLAYER.toString());
        Ruler ruler = new Ruler();

        try {
            String symbole1 = p1.getPlayerSymbole();
            String symbole2 = "";
            addToProtocol("Player1: " + symbole1);

            if (inFight) {
                symbole2 = ruler.getVerhalten(symbole1, CONSTANS.SYMBOLE.PAPIER.toString());

                if (selectedPapier.isPressed()) {
                    symbole1 = CONSTANS.SYMBOLE.PAPIER.toString();
                }
                if (selectedStein.isPressed()) {
                    symbole1 = CONSTANS.SYMBOLE.STEIN.toString();
                }
                if (selectedSchere.isPressed()) {
                    symbole1 = CONSTANS.SYMBOLE.SCHERE.toString();
                }

            } else {
                getCleanProtocol(backlog); 
                symbole2 = changePlayerSymbolImg(p2, 2);
            }
            changePlayerSymbolImg(p1, 1);
            roundNr.setText(0 + "");

            //fight
            addToProtocol("Player2: " + symbole2);
            String figtresult = ruler.comparingSymboles(symbole1, symbole2);
            changePlayerSymbolImg(p2, 2);
            addToProtocol("Ausgabe Fight: " + figtresult);

            //fight was draw waiting on new user input
            if (figtresult.equalsIgnoreCase(CONSTANS.FIGHTSTAT.UNENTSCHIEDEN.toString())) {
                addToProtocol("First Match was a draw");
                addToProtocol("Please select a Symbole and klick match again");
                System.out.println("First Match was a draw");

                player1symbol = "";
                inFight = true;
            }

            result.setText(figtresult);

        } catch ( IOException ex) {
            Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        getProtocol(backlog);
        matchButton.setDisable(false);
        playerPostion = 1;
    }

    public String changePlayerSymbolImg(Player p, int playerPostion) throws IOException {
        String symbole = p.getPlayerSymbole();
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

        return symbole;
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
