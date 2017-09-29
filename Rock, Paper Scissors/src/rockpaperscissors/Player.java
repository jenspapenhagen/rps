/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rockpaperscissors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private int won;

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

    public int getWon() {
        return won;
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

    public void setWon(int won) {
        this.won = won;
    }

    public Enum getRandomSymbole() {
        //so not select the default Enum
        int indexer = new Random().nextInt(Enums.Symbole.values().length - 1);

        return (Enums.Symbole.values()[indexer]);
    }

    public String getRandomName() {
   String output = "";
        List<String> Namelist = new ArrayList<>();
        try {
            //read file into List
            BufferedReader br = new BufferedReader(new FileReader(new File("./src/rockpaperscissors/files/forename.txt")));
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                Namelist.add(sCurrentLine);
            }

            //get a random line
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
