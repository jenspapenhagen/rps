/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author jens.papenhagen
 */
public class JAVAFXSpielmodichangerController implements Initializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(JAVAFXSpielmodichangerController.class);

    @FXML
    private Button demoButton;
    @FXML
    private Button singelButton;

    @FXML
    private void handelDemoButtonClick(ActionEvent event) {
        try {
            demoButton.setDisable(true);
            FXMLLoader fxmlLoader = FXMLLoader.load(getClass().getResource("/fxml/JAVAFXDemomodus.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            LOG.debug("DemoButton get pressed");
            
            //builde stage
            Stage stage = new Stage();
            //build scene
            Scene scene = new Scene(root);

            //css get added in teh fxml files
            scene.getStylesheets().add(getClass().getResource("/styles/javafxdialog.css").toExternalForm());

            stage.setResizable(true);
            stage.setTitle("Stein Schere Papier Game");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.jpg")));

            //keep resize in a dianginal way
            stage.minWidthProperty().bind(scene.heightProperty().multiply(1.5));
            stage.minHeightProperty().bind(scene.widthProperty().divide(1.5));

            stage.setScene(scene);
            stage.show();

            demoButton.setDisable(false);
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }
    }

    @FXML
    private void handelSingelButtonClick(ActionEvent event) {
        try {
            singelButton.setDisable(true);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/JAVAFXSingelplayer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            LOG.debug("Singelplayer Button get pressed");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setTitle("Stein Schere Papier Singelplayer");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/resoures/icon.jpg")));

            stage.setScene(new Scene(root1));
            stage.show();
            singelButton.setDisable(false);
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
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
