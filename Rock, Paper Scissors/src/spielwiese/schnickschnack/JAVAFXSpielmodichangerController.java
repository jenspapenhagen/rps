/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spielwiese.schnickschnack;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author jens.papenhagen
 */
public class JAVAFXSpielmodichangerController implements Initializable {

    @FXML
    private JFXButton demoButton;
    @FXML
    private JFXButton singelButton;

    @FXML
    private void handelDemoButtonClick(ActionEvent event) throws InterruptedException, Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JAVAFXDemomodus.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setTitle("Stein Schere Papier Demo Modus");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("files/icon.jpg")));

            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handelSingelButtonClick(ActionEvent event) throws InterruptedException, Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JAVAFXSingelplayer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setTitle("Stein Schere Papier Singelplayer");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("files/icon.jpg")));

            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

}
