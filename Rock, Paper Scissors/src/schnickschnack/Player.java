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
    private String PlayerSymbole;
    private final int PlayerID;
    private String PlayerCondition;

    Player(int _ID, String _PlayerStatus) {
        this.PlayerName = getRandomName();
        this.PlayerID = _ID;
        this.PlayerCondition = _PlayerStatus;
        this.PlayerSymbole = getRandomSymbole();
    }

    public String getPlayerName() {
        return this.PlayerName;
    }

    public String getPlayerSymbole() {
        return this.PlayerSymbole;
    }

    public int getPlayerID() {
        return this.PlayerID;
    }

    public String getPlayerCondtion() {
        return this.PlayerCondition;
    }

    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    public void setPlayerSymbole(String PlayerSymbole) {
        this.PlayerSymbole = PlayerSymbole;
    }

    public void setPlayerStatus(String PlayerStatus) {
        this.PlayerCondition = PlayerStatus;
    }

    public String getRandomSymbole() {
        int indexer = new Random().nextInt(CONSTANS.SYMBOLE.values().length);
        String randomSymbole = (CONSTANS.SYMBOLE.values()[indexer].toString());

        return randomSymbole;
    }

    public String getRandomName() {
        String output = "";
        try {
            List<String> Namelist = FileUtils.readLines(
                    new File("./src/schnickschnack/files/Vornamen.txt"), "utf-8");

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
