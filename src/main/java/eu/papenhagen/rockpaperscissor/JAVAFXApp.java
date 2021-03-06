/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public class JAVAFXApp extends Application {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(JAVAFXApp.class);

    @Override
    public void start(Stage stage) {
        startGame(stage);
    }

    public void restart(Stage stage) {
        startGame(stage);
    }

    
    public void startGame(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Tabs.fxml"));
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }

        Scene scene = new Scene(root);
        //css get added in teh fxml files
        scene.getStylesheets().add(getClass().getResource("/styles/javafxdialog.css").toExternalForm());

        stage.setResizable(true);
        stage.setTitle("Stein Schere Papier Game");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.jpg")));

        //keep resize in a dianginal way
//        stage.minWidthProperty().bind(scene.heightProperty().multiply(1.5));
//        stage.minHeightProperty().bind(scene.widthProperty().divide(1.5));

        stage.setScene(scene);
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
