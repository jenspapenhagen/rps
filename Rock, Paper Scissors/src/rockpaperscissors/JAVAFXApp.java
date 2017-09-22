/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author jens.papenhagen
 */
public class JAVAFXApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("JAVAFXTaps.fxml"));

        Scene scene = new Scene(root);

        stage.setResizable(true);
        stage.setTitle("Stein Schere Papier Game");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("files/icon.jpg")));

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