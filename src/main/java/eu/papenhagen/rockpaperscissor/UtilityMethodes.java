/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * for the FX Part get an Enum and giveack the right image of it. 
 * files must have the same name as the fitting Enum
 * @author jens.papenhagen
 */
public class UtilityMethodes {

    public Image givebackImg(Enum symbole) throws IOException {
        return new Image(getClass().getResourceAsStream("/images/" + symbole.toString().toLowerCase() + ".png"));
    }

}
