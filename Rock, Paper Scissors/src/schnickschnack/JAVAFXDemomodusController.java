/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private ListView backlog;

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


    public Funktions funk = new Funktions();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int startplayer1 = 3;
        int startplayer2 = 4;
        fight(startplayer1, startplayer2);
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("Random Match");
        randomFight();
    }

    public void randomFight() {
        funk.getCleanProtocol(backlog); //clean the protocol
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
        funk.getCleanProtocol(backlog); //clean the protocol

        try {
            //give out the view
            String symbole1 = showPlayer(_playerID1, playerPostion);
            playerPostion = 2;
            String symbole2 = showPlayer(_playerID2, playerPostion);
            roundNr.setText(0 + "");

            //fight
            String resultFromfight = ruler.result(symbole1, symbole1);
            funk.addToProtocol("Player1: " + symbole1);
            funk.addToProtocol("Player2: " + symbole1);
            funk.addToProtocol("Ausgabe normal Fight: " + resultFromfight);
            //fight again if the fight was a draw
            if (resultFromfight.equalsIgnoreCase(Constans.fightstat.UNENTSCHIEDEN.toString())) {
                funk.addToProtocol("First Match was a draw, NOW Round 1");
                changePlayerSymbol(symbole1, 1);
                changePlayerSymbol(symbole2, 2);
                resultFromfight = runde(_playerID1, _playerID2, symbole1, symbole2);
            }
            //removce the lost player
            toRemoveID(resultFromfight, _playerID1, _playerID2);
            result.setText(resultFromfight);

        } catch (DrawException | IOException ex) {
            Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        funk.getProtocol(backlog);
    }

    public String runde(int _playerID1, int _playerID2, String _lastPlayer1Symbole, String _lastPlayer2Symbole) throws DrawException, IOException {
        String result = null;
        Ruler ruler = new Ruler();

        int maxrounds = 5;
        for (int rounds = 1; rounds <= maxrounds; rounds++) {
            roundNr.setText("" );

            //fight
            String player1symbole = ruler.getVerhalten(_lastPlayer1Symbole, _lastPlayer2Symbole);
            String player2symbole = ruler.getVerhalten(_lastPlayer2Symbole, _lastPlayer1Symbole);

            funk.addToProtocol("Player1: " + player1symbole);
            funk.addToProtocol("Player2: " + player2symbole);
            changePlayerSymbol(player1symbole, 1);
            changePlayerSymbol(player2symbole, 2);
            result = ruler.result(player1symbole, player2symbole);

            funk.addToProtocol("Runden " + rounds + " Ergebnis: " + result);
            if (!result.equalsIgnoreCase(Constans.fightstat.UNENTSCHIEDEN.toString())) {
                changePlayerSymbol(player1symbole, 1);
                changePlayerSymbol(player2symbole, 2);
                break;
            }

            if (rounds == maxrounds) {
                result = "Player 1 gewinnt";//froce win
                break;
            }

        }

        return result;

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


    public void changeRemovePlayerIDLable(String input) {
        removeID.setText(input);
    }

}
