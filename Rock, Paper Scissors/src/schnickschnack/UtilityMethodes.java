/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 *
 * @author jens.papenhagen
 */
public class UtilityMethodes {

    public Image givebackImg(Enum symbole) throws IOException {
        Image myPicture;

        if (symbole.equals(ENUMS.SYMBOLE.SCISSOR)) {
            myPicture = new Image(new File("./src/schnickschnack/files/schere.png").toURI().toString());
        }
        if (symbole.equals(ENUMS.SYMBOLE.PAPER)) {
            myPicture = new Image(new File("./src/schnickschnack/files/papier.png").toURI().toString());
        }
        if (symbole.equals(ENUMS.SYMBOLE.STONE)) {
            myPicture = new Image(new File("./src/schnickschnack/files/stein.png").toURI().toString());
        } else {
            myPicture = new Image(new File("./src/schnickschnack/files/papier.png").toURI().toString());
        }

        return myPicture;
    }

}
