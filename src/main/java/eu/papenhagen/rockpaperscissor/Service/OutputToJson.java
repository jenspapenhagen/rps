/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.Service;

import com.google.gson.Gson;
import eu.papenhagen.rockpaperscissor.EAO.PlayerHandler;
import eu.papenhagen.rockpaperscissor.Entities.ExportPlayer;
import eu.papenhagen.rockpaperscissor.Entities.Match;
import eu.papenhagen.rockpaperscissor.Entities.Tier;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Output to JSON
 * @author jens.papenhagen
 */
public class OutputToJson {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(OutputToJson.class);
    /**
     * Save the tournament to a JSON file for the output in webview
     *
     * @param tournament
     */
    private static void saveToJson(List<Tier> tournament) {
        //build a simle nested list cauz the orgianl json is very nested
        List<List<List<ExportPlayer>>> exportMatchList = new ArrayList<>();
        //fill the HashMap
        tournament.forEach((Tier t) -> {
            List<List<ExportPlayer>> list = new ArrayList<>();
            t.getMatchList().forEach((Match m) -> {
                //newlist for Player
                List<ExportPlayer> exportPlayerList = new ArrayList<>();
                //convert player
                ExportPlayer exp1 = PlayerHandler.createExportPlayerOutOfPlayer(m.getPlayer1());
                ExportPlayer exp2 = PlayerHandler.createExportPlayerOutOfPlayer(m.getPlayer2());
                //add to exportPlayer list
                exportPlayerList.add(exp1);
                exportPlayerList.add(exp2);
                //nest it again
                list.add(exportPlayerList);
            });
            //next nested lvl
            exportMatchList.add(list);
        });
        //Convert object to JSON string
        Gson gson = new Gson();
        String jsonString = gson.toJson(exportMatchList);
        //safe to Extern file
        String fileName = System.getProperty("user.home") + "//Desktop//tournament.json";
        File file = new File(fileName);
        //save to file as OutputStream
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            LOG.info("Tournament file: " + fileName + " created");
            fos.write(jsonString.getBytes());
            //move all to file befor close it
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }
    }
    
}
