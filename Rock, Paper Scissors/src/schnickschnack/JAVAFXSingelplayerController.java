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
import javafx.scene.control.ListView;

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
        funk.changeWinnerLable("Bitte noch Symbol wählen");
    }

    @FXML
    private void handlePapierButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("handlePapierButton");
        funk.addToProtocol("Player1: Papier");
        player1symbol = funk.changePlayerSymbol(Constans.symbole.PAPIER.toString(), playerPostion);
    }

    @FXML
    private void handleSteinButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("handleSteinButton");
        funk.addToProtocol("Player1: Stein");
        player1symbol = funk.changePlayerSymbol(Constans.symbole.STEIN.toString(), playerPostion);
    }

    @FXML
    private void handleSchereButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("handleSchereButton");
        funk.addToProtocol("Player1: Schere");
        player1symbol = funk.changePlayerSymbol(Constans.symbole.SCHERE.toString(), playerPostion);
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
            funk.changeWinnerLable("Bitte noch Symbol wählen");
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
                symbole2 = funk.showPlayer(_playerID2, 2);
            }
            funk.changePlayerSymbol(symbole1, playerPostion);
            funk.changeRoundCounter(0 + "");

            //fight
            funk.addToProtocol("Player2: " + symbole2);
            String figtresult = ruler.result(symbole1, symbole2);
            playerPostion = 2;
            funk.changePlayerSymbol(symbole2, playerPostion);
            funk.addToProtocol("Ausgabe Fight: " + figtresult);

            if (figtresult.equalsIgnoreCase(Constans.fightstat.UNENTSCHIEDEN.toString())) {
                funk.addToProtocol("First Match was a draw");
                funk.addToProtocol("Please select a Symbole and klick match again");
                System.out.println("First Match was a draw");

                player1symbol = "";
                inFight = true;
            }

            funk.changeWinnerLable(figtresult);

        } catch (DrawException | IOException ex) {
            Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        funk.getProtocol(backlog);
        matchButton.setDisable(false);
    }

}
