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
        String uri = "./src/rockpaperscissors/files/" + symbole.toString().toLowerCase() + ".png";

        myPicture = new Image(new File(uri).toURI().toString());
        return myPicture;
    }

}
