/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public final class Player {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Player.class);

    private String name;
    private Enum symbole;
    private final int ID;
    private Enum condition;

    public Player(int ID, Enum condition) {
        this.name = getRandomName();
        this.ID = ID;
        this.condition = condition;
        this.symbole = getRandomSymbole();
    }

    public String getPlayerName() {
        return this.name;
    }

    public Enum getPlayerSymbole() {
        return this.symbole;
    }

    public int getPlayerID() {
        return this.ID;
    }

    public Enum getPlayerCondtion() {
        return this.condition;
    }

    public void setPlayerName(String name) {
        this.name = name;
    }

    public void setPlayerSymbole(Enum symbole) {
        this.symbole = symbole;
    }

    public void setPlayerCondition(Enum condition) {
        this.condition = condition;
    }

    public Enum getRandomSymbole() {
        //so not select the default Enum
        int indexer = new Random().nextInt(Enums.Symbole.values().length-1);

        return (Enums.Symbole.values()[indexer]);
    }

    public String getRandomName() {
        String output = "";
        try {
            List<String> Namelist = FileUtils.readLines(
                    new File("./src/rockpaperscissors/files/forename.txt"), "utf-8");

            int randomline = new Random().nextInt(Namelist.size());
            output = Namelist.get(randomline);

        } catch (FileNotFoundException ex) {
            LOG.error("forname liste emptry");
            LOG.error(ex.getMessage());

        } catch (IOException ex) {
            LOG.error("forname can not be read");
            LOG.error(ex.getMessage());
        }

        return output;

    }

}
