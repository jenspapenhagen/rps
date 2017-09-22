/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors;

import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 *
 * @author jens.papenhagen
 */
public class UtilityMethodes {

    public Image givebackImg(Enum symbole) throws IOException {
        Image myPicture = null;

        if (symbole.equals(Enums.Symbole.SCISSOR)) {
            myPicture = new Image(new File("./src/rockpaperscissors/files/scissor.png").toURI().toString());
        }
        if (symbole.equals(Enums.Symbole.PAPER)) {
            myPicture = new Image(new File("./src/rockpaperscissors/files/paper.png").toURI().toString());
        }
        if (symbole.equals(Enums.Symbole.ROCK)) {
            myPicture = new Image(new File("./src/rockpaperscissors/files/stone.png").toURI().toString());
        }

        return myPicture;
    }

}
