/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author jens.papenhagen
 */
public class OneonOneController implements Initializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(OneonOneController.class);

    @FXML
    private ListView backlog;
    @FXML
    private Button matchButton;
    @FXML
    private Button restartButton;
    @FXML
    private ComboBox combobox1;
    @FXML
    private ComboBox combobox2;
    @FXML
    private ImageView player1img;
    @FXML
    private ImageView player2img;
    @FXML
    private Label result;

    private final UtilityMethodes funk = new UtilityMethodes();

    ObservableList<String> data = FXCollections.observableArrayList();

    Player p1 = new Player(1, Enums.Playercondition.PLAYER);
    Player p2 = new Player(1, Enums.Playercondition.PLAYER);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //clean the log
        data.clear();

        //show a text animate the singel player to start the game
        result.setText("Bitte noch Symbol wählen");

        //fill the combox
        combobox1.getItems().addAll(EnumSet.allOf(Enums.Symbole.class));
        combobox1.getItems().remove(Enums.Symbole.DEFAULT);
        combobox2.getItems().addAll(EnumSet.allOf(Enums.Symbole.class));
        combobox2.getItems().remove(Enums.Symbole.DEFAULT);

        //hidebutton and 2. combobox
        matchButton.setVisible(false);
        restartButton.setVisible(false);
        combobox2.setVisible(true);

        backlog.setVisible(false);
    }

    @FXML
    private void handleSelectedCombobox1(ActionEvent event) {
        //set the player symbole
        p1.setSymbole((Enum) combobox1.getValue());

        //fill Protocoll
        result.setText("player2 select a symbole please");

        //move to next player
        //hide combobox 1
        combobox1.setVisible(false);
        combobox2.setVisible(true);
    }

    @FXML
    private void handleSelectedCombobox2(ActionEvent event) {
        p2.setSymbole((Enum) combobox2.getValue());

        //set matchButton to visable
        matchButton.setVisible(true);
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException {
        fight();
    }

    @FXML
    private void handleRestartButton(ActionEvent event) throws InterruptedException {
        //set both player to default
        p1.setSymbole(Enums.Symbole.DEFAULT);
        p2.setSymbole(Enums.Symbole.DEFAULT);

        //change UI
        changePlayerUI(p1, 1);
        changePlayerUI(p2, 2);

        //clean the log
        data.clear();

        //clear and fill the combox
        combobox1.getItems().clear();
        combobox2.getItems().clear();
        
        combobox1.getItems().addAll(EnumSet.allOf(Enums.Symbole.class));
        combobox1.getItems().remove(Enums.Symbole.DEFAULT);
        combobox2.getItems().addAll(EnumSet.allOf(Enums.Symbole.class));
        combobox2.getItems().remove(Enums.Symbole.DEFAULT);

        //unset matchbutton
        matchButton.setVisible(false);
        //unset restartbutton
        restartButton.setVisible(false);
        //unset combobox2 set combobox1
        combobox1.setVisible(true);
        combobox2.setVisible(false);

        backlog.setVisible(false);

        backlog.setVisible(false);
    }

    /**
     * Starting the fight
     */
    private void fight() {

        //show log again
        backlog.setVisible(true);

        //show comboox1 again
        combobox1.setVisible(true);

        //change UI
        changePlayerUI(p1, 1);
        changePlayerUI(p2, 2);

        //fight
        Enum figtresult = null;
        Enums.Symbole tempsymbole = (Enums.Symbole) p1.getSymbole();
        if (tempsymbole.loseAgaist((Enums.Symbole) p1.getSymbole(), (Enums.Symbole) p2.getSymbole())) {
            //player 1 have lost
            figtresult = Enums.Fightstat.LOST;
        } else if (p1.getSymbole().equals(p2.getSymbole())) {
            //can happend but raw
            figtresult = Enums.Fightstat.DRAW;
        } else {
            //player 1 have won
            figtresult = Enums.Fightstat.WON;
        }

        //fill the protocol
        addToProtocol("Player1: " + p1.getSymbole());
        addToProtocol("Player2: " + p2.getSymbole());
        addToProtocol("Player 1 has: " + figtresult);

        //change UI
        result.setText("Player 1 has: " + figtresult);

        //plot the Protocol
        getProtocol(backlog);

        //populate the debug log
        LOG.debug(data.toString());

        //end of fight
        matchButton.setVisible(false);
        restartButton.setVisible(true);

    }

    /**
     * changing the player in the UI on a given postion
     *
     * @param p player object
     * @param pos postion in the UI
     */
    private void changePlayerUI(Player p, int pos) {
        //get the player symbole
        Enum symbole = p.getSymbole();
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
                player2img.setImage(playerSymbole);
                break;
            default:
                break;
        }

    }

    private void addToProtocol(String input) {
        data.add(input);
    }

    private void getProtocol(ListView lv) {
        lv.setItems(data);
    }

    private void getCleanProtocol(ListView lv) {
        data.clear();
        lv.setItems(data);
    }
}
