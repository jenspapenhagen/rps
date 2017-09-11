/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author jens.papenhagen
 */
public class Funktions {

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

    ObservableList<String> data = FXCollections.observableArrayList();

    public Image givebackImg(String randomSymbole) throws IOException {
        Image myPicture;
        switch (randomSymbole) {
            case "SCHERE":
                myPicture = new Image(new File("./src/schnickschnack/files/schere.png").toURI().toString());
                break;
            case "PAPIER":
                myPicture = new Image(new File("./src/schnickschnack/files/papier.png").toURI().toString());
                break;
            case "STEIN":
                myPicture = new Image(new File("./src/schnickschnack/files/stein.png").toURI().toString());
                break;
            default:
                myPicture = new Image(new File("./src/schnickschnack/files/papier.png").toURI().toString());
        }

        return myPicture;
    }

    public String showPlayer(int playerID, int playerPostion) throws IOException {
        Player player = new Player(playerID, Constans.playerStatus.PLAYER.toString());

        String symbole = player.getPlayerSymbole();
        Image playerSymbole = givebackImg(symbole);

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
        Image playerSymbole = givebackImg(playerSymbol);
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

    public void getProtocol(ListView lv) {
        lv.setItems(data);
    }

    public void getCleanProtocol(ListView lv) {
        data.clear();
        lv.setItems(data);
    }

}
