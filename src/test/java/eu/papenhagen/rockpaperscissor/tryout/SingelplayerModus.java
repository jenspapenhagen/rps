/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.tryout;

import java.io.IOException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.Test;

/**
 *
 * @author jens.papenhagen
 */
public class SingelplayerModus {

    public boolean complete = false;

    @Test
    public void tryout() throws InterruptedException {

        JFXPanel jfxPanel = new JFXPanel(); // To start the platform

        Platform.runLater(() -> {
            Stage stage = new Stage();

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/JAVAFXSingelplayer.fxml"));

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
                stage.showAndWait();
                
            } catch (IOException ex) {

            }

            complete = true;
        });
        while (!complete) {
            Thread.sleep(500);
        }
    }

}
