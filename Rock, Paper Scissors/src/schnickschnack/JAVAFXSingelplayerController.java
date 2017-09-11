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

    public String player1symbol = "";

    public Boolean inFight = false;

    public Funktions funk = new Funktions();

    public int playerPostion = 1;

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

    public JAVAFXSingelplayerController() {

    }

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
        System.out.println("handlePapierButton");
        funk.addToProtocol("Player1: Papier");
        playerPostion = 1;
        player1symbol = changePlayerSymbol(Constans.symbole.PAPIER.toString(), playerPostion);
    }

    @FXML
    private void handleSteinButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("handleSteinButton");
        funk.addToProtocol("Player1: Stein");
        playerPostion = 1;
        player1symbol = changePlayerSymbol(Constans.symbole.STEIN.toString(), playerPostion);
    }

    @FXML
    private void handleSchereButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("handleSchereButton");
        funk.addToProtocol("Player1: Schere");
        playerPostion = 1;
        player1symbol = changePlayerSymbol(Constans.symbole.SCHERE.toString(), playerPostion);
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("Match");

        Random random = new Random();
        int randomPlayerNr2 = random.nextInt((10 - 1) + 1) + 1;

        if (player1symbol.length() != 0) {
            funk.getCleanProtocol(backlog); //clean the protocol
            fight(player1symbol, randomPlayerNr2);
        } else {
            result.setText("Bitte noch Symbol wählen");
            inFight = true;
        }
    }

    public void fight(String _playerSymbol, int _playerID2) throws InterruptedException {
        matchButton.setDisable(true);
        Ruler ruler = new Ruler();

        try {
            String symbole1 = _playerSymbol;
            String symbole2 = "";

            if (inFight) {
                symbole2 = ruler.getVerhalten(symbole1, Constans.symbole.PAPIER.toString());

                if (selectedPapier.isPressed()) {
                    symbole1 = Constans.symbole.PAPIER.toString();
                }
                if (selectedStein.isPressed()) {
                    symbole1 = Constans.symbole.STEIN.toString();
                }
                if (selectedSchere.isPressed()) {
                    symbole1 = Constans.symbole.SCHERE.toString();
                }

            } else {
                funk.getCleanProtocol(backlog); //clean the protocol
                symbole2 = showPlayer(_playerID2, 2);
            }
            changePlayerSymbol(symbole1, playerPostion);
            roundNr.setText(0 + "");

            //fight
            funk.addToProtocol("Player2: " + symbole2);
            String figtresult = ruler.result(symbole1, symbole2);
            playerPostion = 2;
            changePlayerSymbol(symbole2, playerPostion);
            funk.addToProtocol("Ausgabe Fight: " + figtresult);

            if (figtresult.equalsIgnoreCase(Constans.fightstat.UNENTSCHIEDEN.toString())) {
                funk.addToProtocol("First Match was a draw");
                funk.addToProtocol("Please select a Symbole and klick match again");
                System.out.println("First Match was a draw");

                player1symbol = "";
                inFight = true;
            }

            result.setText(figtresult);

        } catch (DrawException | IOException ex) {
            Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        funk.getProtocol(backlog);
        matchButton.setDisable(false);
    }

    public String showPlayer(int playerID, int playerPostion) throws IOException {
        Player player = new Player(playerID, Constans.playerStatus.PLAYER.toString());

        String symbole = player.getPlayerSymbole();
        Image playerSymbole = funk.givebackImg(symbole);

        switch (playerPostion) {
            case 1:
                player1Nr.setText("" + playerID);
                player1Name.setText(player.getPlayerName());
                player1img.setImage(playerSymbole);
                break;

            case 2:
                player2Nr.setText("" + playerID);
                player2Name.setText(player.getPlayerName());
                player2img.setImage(playerSymbole);
                break;

            default:
                break;

        }

        return symbole;
    }

    public String changePlayerSymbol(String playerSymbol, int playerPostion) throws IOException {
        Image playerSymbole = funk.givebackImg(playerSymbol);
        switch (playerPostion) {
            case 1:
                player1img.setImage(playerSymbole);
                break;
            case 2:
                player2img.setImage(playerSymbole);
                break;

            default:
                break;

        }

        return playerSymbol;
    }

}
