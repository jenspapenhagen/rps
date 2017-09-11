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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

/**
 *
 * @author jens.papenhagen
 */
public class Funktions {

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
