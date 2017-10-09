/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.Entities;

import eu.papenhagen.rockpaperscissor.Enums;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.LoggerFactory;
import lombok.*;

/**
 * mostly pojo class have a random player name generator
 *
 * @author jens.papenhagen
 */
public final class Player {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Player.class);

    @Getter
    @Setter
    private int ID;
    
    @Setter
    @Getter
    private String name;
    
    @Setter
    @Getter
    private Enum symbole;

    @Setter
    @Getter
    private Enum condition;
    
    @Setter
    @Getter
    private int won;

    public Player(int ID, Enum condition) {
        this.name = getRandomName();
        this.ID = ID;
        this.condition = condition;
        this.symbole = getRandomSymbole();
    }

    public Enum getRandomSymbole() {
        //so not select the default Enum
        int indexer = new Random().nextInt(Enums.Symbole.values().length - 1);

        Enums.Symbole output = Enums.Symbole.values()[indexer];
        if(output.equals(Enums.Symbole.DEFAULT)){
            output = Enums.Symbole.AIR;
        }
        return output;
    }

    public String getRandomName() {
        String output = "";
        List<String> Namelist = new ArrayList<>();
        //get the resources as stream
        try {
            Namelist = Files.readAllLines(Paths.get(this.getClass().getResource("/resources/forename.txt").toURI()), Charset.defaultCharset());
        } catch (URISyntaxException | IOException ex) {
            LOG.error(ex.getMessage());
        }

        //get a random line
        int randomline = new Random().nextInt(Namelist.size());

        output = Namelist.get(randomline);

        return output;

    }

}
