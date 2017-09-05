/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import com.jfoenix.controls.JFXButton;
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

    ObservableList<String> data = FXCollections.observableArrayList();

    public Boolean inFight = false;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeWinnerLable("Bitte noch Symbol wählen");
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("Match");

        Random random = new Random();
        int randomPlayerNr2 = random.nextInt((10 - 1) + 1) + 1;

        if (player1symbol.length() != 0) {
            fight(player1symbol, randomPlayerNr2);
        } else {
            changeWinnerLable("Bitte noch Symbol wählen");
            inFight = true;
        }

    }

    @FXML
    private void handlePapierButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("handlePapierButton");
        addToProtocol("Player1: Papier");
        player1symbol = changePlayer1Symbol(Constans.symbole.PAPIER.toString());
    }

    @FXML
    private void handleSteinButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("handleSteinButton");
        addToProtocol("Player1: Stein");
        player1symbol = changePlayer1Symbol(Constans.symbole.STEIN.toString());
    }

    @FXML
    private void handleSchereButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("handleSchereButton");
        addToProtocol("Player1: Schere");
        player1symbol = changePlayer1Symbol(Constans.symbole.SCHERE.toString());
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
                getCleanProtocol(); //clean the protocol
                symbole2 = showPlayer2(_playerID2);
            }
            changePlayer1Symbol(symbole1);
            changeRoundCounter(0 + "");

            //fight
            addToProtocol("Player2: " + symbole2);
            String figtresult = ruler.result(symbole1, symbole2);
            changePlayer2Symbol(symbole2);
            addToProtocol("Ausgabe Fight: " + figtresult);

            if (figtresult.equalsIgnoreCase(Constans.fightstat.UNENTSCHIEDEN.toString())) {
                addToProtocol("First Match was a draw");
                addToProtocol("Please select a Symbole and klick match again");
                System.out.println("First Match was a draw");

                player1symbol = "";
                inFight = true;
            }

            changeWinnerLable(figtresult);

        } catch (DrawException | IOException ex) {
            Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        getProtocol();
        matchButton.setDisable(false);
    }

    public String changePlayer1Symbol(String playerSymbol) throws IOException {

        Image playerSymbole = givebackImg(playerSymbol);
        player1img.setImage(playerSymbole);

        return playerSymbol;
    }

    public String changePlayer2Symbol(String playerSymbol) throws IOException {

        Image playerSymbole = givebackImg(playerSymbol);
        player2img.setImage(playerSymbole);

        return playerSymbol;
    }

    public String showPlayer1(int playerID1) throws IOException {
        Player p1 = new Player(playerID1, Constans.playerStatus.PLAYER.toString());

        String symbole1 = p1.getPlayerSymbole();

        player1Name.setText("Singelplayer");

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

    public void changeRoundCounter(String input) {
        roundNr.setText(input + "");
    }

    public void changeWinnerLable(String input) {
        result.setText(input);
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