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
    
    public boolean randomfight = false;
    
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
        fight();
    }

    @FXML
    private void handleMatchButton(ActionEvent event) throws InterruptedException, Exception {
        System.out.println("Random Match");
        randomfight = true;
        fight();
    }

    public void fight() {
        int Player1ID = 3;
        int Player2ID = 4;
        
        if (randomfight) {
            Random random = new Random();
            Player1ID = random.nextInt((10 - 1) + 1) + 1;
            Player2ID = random.nextInt((10 - 1) + 1) + 1;

            if (Player1ID == Player2ID) {
                Player2ID = Player1ID + 1;
            }
        }

        Player p1 = new Player(Player1ID, ENUMS.PLAYERCONDITION.PLAYER.toString());
        Player p2 = new Player(Player2ID, ENUMS.PLAYERCONDITION.PLAYER.toString());

        Ruler ruler = new Ruler();
        getCleanProtocol(backlog); //clean the protocol

        try {
            //give out the view
            changePlayerSymbolImg(p1, playerPostion);
            playerPostion = 2;
            changePlayerSymbolImg(p2, playerPostion);
            roundNr.setText("");

            //fight
            Enum resultFromfight = ruler.comparingSymboles(p1.getPlayerSymbole(), p2.getPlayerSymbole());
            addToProtocol("Player1: " + p1.getPlayerSymbole());
            addToProtocol("Player2: " + p2.getPlayerSymbole());
            addToProtocol("Ausgabe normal Fight: " + resultFromfight);
            //fight again if the fight was a draw
            if (resultFromfight.equals(ENUMS.FIGHTSTAT.DRAW)) {
                addToProtocol("First Match was a draw, NOW Round 1");
                changePlayerSymbolImg(p1, 1);
                changePlayerSymbolImg(p2, 2);
                resultFromfight = runde(p1, p2);
            }
            //removce the lost player
            toRemoveID(resultFromfight, p1, p2);
            result.setText("Player 1 has "+resultFromfight.toString());

        } catch (IOException ex) {
            Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        getProtocol(backlog);
        randomfight = false;
    }

    public Enum runde(Player p1, Player p2) throws IOException {
        Enum fightresult = null;
        Ruler ruler = new Ruler();

        int maxrounds = 5;
        for (int rounds = 1; rounds <= maxrounds; rounds++) {
            //fight
            Enum player1symbole = ruler.getVerhalten(p1.getPlayerSymbole(), p2.getPlayerSymbole());
            Enum player2symbole = ruler.getVerhalten(p2.getPlayerSymbole(), p1.getPlayerSymbole());

            addToProtocol("Player1: " + player1symbole);
            addToProtocol("Player2: " + player2symbole);
            p1.setPlayerSymbole(player1symbole);
            p2.setPlayerSymbole(player2symbole);
            
            changePlayerSymbolImg(p1, 1);
            changePlayerSymbolImg(p2, 2);
            
            fightresult = ruler.comparingSymboles(player1symbole, player2symbole);

            addToProtocol("Runden " + rounds + " Ergebnis: " + fightresult);
            if (!fightresult.equals(ENUMS.FIGHTSTAT.DRAW)) {
                changePlayerSymbolImg(p1, 1);
                changePlayerSymbolImg(p2, 2);
                break;
            }

            if (rounds == maxrounds) {
                fightresult = ENUMS.FIGHTSTAT.WON;//froce win
                break;
            }

            roundNr.setText(" " + rounds);
        }
        
        return fightresult;
    }

    public void toRemoveID(Enum result, Player p1, Player p2) {
        Integer removePlayerID = 0;

        try {
            if (result.equals(ENUMS.FIGHTSTAT.WON) ) {
                removePlayerID = p2.getPlayerID();
            } else {
                removePlayerID = p1.getPlayerID();
            }
        } catch (NullPointerException ex) {
            removePlayerID = p1.getPlayerID();  //froce win
        }

        changeRemovePlayerIDLable("" + removePlayerID);
    }

    public void changePlayerSymbolImg(Player p, int playerPostion) throws IOException {
        Enum symbole = p.getPlayerSymbole();
        Image playerSymbole = funk.givebackImg(symbole);

        switch (playerPostion) {
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
