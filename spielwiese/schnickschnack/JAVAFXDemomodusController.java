/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spielwiese.schnickschnack;

import java.io.File;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author jens.papenhagen
 */
public class JAVAFXDemomodusController implements Initializable {

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
    private Label removeID;
    @FXML
    private Label roundNr;
    @FXML
    private ListView backlog;

    ObservableList<String> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fight(3, 4);
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("Random Match");
        randomFight();

    }

    public void randomFight() {
        getCleanProtocol(); //clean the protocol
        Random random = new Random();
        int randomPlayerNr1 = random.nextInt((10 - 1) + 1) + 1;
        int randomPlayerNr2 = random.nextInt((10 - 1) + 1) + 1;

        if (randomPlayerNr1 == randomPlayerNr2) {
            randomPlayerNr2 = randomPlayerNr1 + 1;
        }

        fight(randomPlayerNr1, randomPlayerNr2);
    }

    public void fight(int _playerID1, int _playerID2) {
        Ruler ruler = new Ruler();
        getCleanProtocol(); //clean the protocol

        try {
            //give out the view
            String symbole1 = showPlayer1(_playerID1);
            String symbole2 = showPlayer2(_playerID2);
            changeRoundCounter(0 + "");

            //fight
            
            String result = ruler.result(symbole1, symbole1);
            addToProtocol("Player1: " + symbole1);
            addToProtocol("Player2: " + symbole1);
            addToProtocol("Ausgabe normal Fight: " + result);
            //fight again if the fight was a draw
            if (result.equalsIgnoreCase(Constans.fightstat.UNENTSCHIEDEN.toString())) {
                addToProtocol("First Match was a draw, NOW Round 1");
                result = runde(_playerID1, _playerID2, symbole1, symbole2);
            }
            //removce the lost player
            toRemoveID(result, _playerID1, _playerID2);

            changeWinnerLable(result);

        } catch (DrawException | IOException ex) {
            Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        getProtocol();

    }

    public String runde(int _playerID1, int _playerID2, String _lastPlayer1Symbole, String _lastPlayer2Symbole) throws DrawException {
        String result = null;
        Ruler ruler = new Ruler();

        int maxrounds = 5;
        for (int rounds = 1; rounds <= maxrounds; rounds++) {
            changeRoundCounter("" + rounds);

            //fight
            String player1symbole = ruler.getVerhalten(_lastPlayer1Symbole, _lastPlayer2Symbole);
            String player2symbole = ruler.getVerhalten(_lastPlayer2Symbole, _lastPlayer1Symbole);

            addToProtocol("Player1: " + player1symbole);
            addToProtocol("Player2: " + player2symbole);
            result = ruler.result(player1symbole, player2symbole);

            addToProtocol("Runden " + rounds + " Ergebnis: " + result);
            if (!result.equalsIgnoreCase(Constans.fightstat.UNENTSCHIEDEN.toString())) {
                break;                
            }

            if (rounds == maxrounds) {
                result = "Player 1 gewinnt";                //froce win
                break;
            }

        }

        return result;
    }

    public String showPlayer1(int playerID1) throws IOException {
        Player p1 = new Player(playerID1, Constans.playerStatus.PLAYER.toString());

        String symbole1 = p1.getPlayerSymbole();
        Image playerSymbole = givebackImg(symbole1);

        player1Nr.setText("" + playerID1);
        player1Name.setText(p1.getPlayerName());
        player1img.setImage(playerSymbole);

        return symbole1;
    }

    public String showPlayer2(int playerID2) throws IOException {
        Player p2 = new Player(playerID2, Constans.playerStatus.PLAYER.toString());

        String symbole2 = p2.getPlayerSymbole();
        Image playerSymbole = givebackImg(symbole2);

        player2Nr.setText("" + playerID2);
        player2Name.setText(p2.getPlayerName());
        player2img.setImage(playerSymbole);

        return symbole2;
    }

    public Image givebackImg(String randomSymbole) throws IOException {
        Image myPicture;
        switch (randomSymbole) {
            case "SCHERE":
                myPicture = new Image(new File("./src/spielwiese/schnickschnack/files/schere.png").toURI().toString());
                break;
            case "PAPIER":
                myPicture = new Image(new File("./src/spielwiese/schnickschnack/files/papier.png").toURI().toString());
                break;
            case "STEIN":
                myPicture = new Image(new File("./src/spielwiese/schnickschnack/files/stein.png").toURI().toString());
                break;
            default:
                myPicture = new Image(new File("./src/spielwiese/schnickschnack/files/papier.png").toURI().toString());
        }

        return myPicture;
    }

    public void toRemoveID(String result, int _playerID1, int _playerID2) {
        //remove the loser
        Integer removePlayerID = 0;
        Ruler ruler = new Ruler();

        try {
            if (ruler.fightstatus(result).equals(Constans.fightstat.GEWONNEN.toString())) {
                removePlayerID = _playerID2;
            } else {
                removePlayerID = _playerID1;
            }
        } catch (NullPointerException ex) {
            //froce win
            removePlayerID = _playerID1;
        }

        changeRemovePlayerIDLable("" + removePlayerID);
    }

    public void changeRoundCounter(String input) {
        roundNr.setText(input + "");
    }

    public void changeWinnerLable(String input) {
        result.setText(input);
    }

    public void changeRemovePlayerIDLable(String input) {
        removeID.setText(input);
    }

    public void addToProtocol(String input) {
        data.add(input);
    }

    public void getProtocol() {
        backlog.setItems(data);
    }

    public void getCleanProtocol() {
        data.clear();
        backlog.setItems(data);
    }

}
