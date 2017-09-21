/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schnickschnack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jens.papenhagen
 */
public class Tier implements Callable<List<Player>> {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Rounds.class);
    
    private final int maxGames;
    private final List<Player> playerList;

    public Tier(int maxGames, List<Player> playerList) {
        this.maxGames = maxGames;
        this.playerList = playerList;
        
        LOG.debug("maxGames" + maxGames);
        LOG.debug("playerList" + playerList);
    }

    /**
     * a round i the tournier get called tier
     * @return
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Player> call() throws InterruptedException, ExecutionException {
         ExecutorService executerForThisTier = Executors.newFixedThreadPool(4);

        //fill the gamesList
        List<Player> loserList = new ArrayList<>(maxGames);
        Iterator<Player> playerListIterator = playerList.iterator();
        LOG.debug("List filled");

        for (int matches = 1; matches <= maxGames; matches++) {
            Match game = new Match(matches, playerListIterator.next(), playerListIterator.next());
            loserList.add(executerForThisTier.submit(game).get());
        }

        executerForThisTier.shutdown();
        LOG.debug("Executors shutdown");
        playerList.removeAll(loserList);

        return playerList;
    }

}
