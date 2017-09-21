/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jens.papenhagen
 */
public final class Player {


    private String PlayerName;
    private Enum PlayerSymbole;
    private final int PlayerID;
    private Enum PlayerCondition;

    public Player(int ID, Enum PlayerCondition) {
        this.PlayerName = getRandomName();
        this.PlayerID = ID;
        this.PlayerCondition = PlayerCondition;
        this.PlayerSymbole = getRandomSymbole();
    }

    public String getPlayerName() {
        return this.PlayerName;
    }

    public Enum getPlayerSymbole() {
        return this.PlayerSymbole;
    }

    public int getPlayerID() {
        return this.PlayerID;
    }

    public Enum getPlayerCondtion() {
        return this.PlayerCondition;
    }

    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    public void setPlayerSymbole(Enum PlayerSymbole) {
        this.PlayerSymbole = PlayerSymbole;
    }

    public void setPlayerCondition(Enum PlayerCondition) {
        this.PlayerCondition = PlayerCondition;
    }

    public Enum getRandomSymbole() {
        int indexer = new Random().nextInt(Enums.Symbole.values().length);
        
        return (Enums.Symbole.values()[indexer]);
    }

    public String getRandomName() {
        String output = "";
        try {
            List<String> Namelist = FileUtils.readLines(
                    new File("./src/schnickschnack/files/forename.txt"), "utf-8");

            int randomline = new Random().nextInt(Namelist.size());
            output = Namelist.get(randomline);

        } catch (FileNotFoundException e) {
            System.out.println("Liste alle");

        } catch (IOException e) {
            System.out.println("Liste alle2");
        }

        return output;

    }

}
