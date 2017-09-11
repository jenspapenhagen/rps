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
    private String PlayerStatus;

    Player(int _ID, String _PlayerStatus) {
        this.PlayerName = randomName();
        this.PlayerID = _ID;
        this.PlayerStatus = _PlayerStatus;
    }

    public String getPlayerName() {
        String output = this.PlayerName;

        return output;
    }

    public String getPlayerSymbole() {
        String output = randomSymbole();

        return output;
    }

    public int getPlayerID() {
        return this.PlayerID;
    }

    public String getPlayerStatus() {
        return this.PlayerStatus;
    }

    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    public void setPlayerStatus(String PlayerStatus) {
        this.PlayerStatus = PlayerStatus;
    }

    public String randomSymbole() {
        int indexer = new Random().nextInt(Constans.symbole.values().length);
        String randomSymbole = (Constans.symbole.values()[indexer].toString());

        return randomSymbole;
    }

    public String randomName() {
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
