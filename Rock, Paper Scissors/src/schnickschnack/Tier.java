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

/**
 *
 * @author jens.papenhagen
 */
public class Tier implements Callable<List<Player>> {

    private final int maxGames;
    private final List<Player> playerList;

    public Tier(int _maxGames, List<Player> _playerList) {
        maxGames = _maxGames;
        playerList = _playerList;
    }

    @Override
    public List<Player> call() throws Exception {
        return runThisTier();
    }

    public List<Player> runThisTier() throws InterruptedException, ExecutionException {
        ExecutorService tierForExecuter = Executors.newFixedThreadPool(4);

        //fill the gamesList
        List<Player> loserList = new ArrayList<>(maxGames);
        Iterator<Player> playerListIterator = playerList.iterator();

        for (int matches = 1; matches <= maxGames; matches++) {
            Game game = new Game(matches, playerListIterator.next(), playerListIterator.next());
            loserList.add(tierForExecuter.submit(game).get());
        }

        tierForExecuter.shutdown();
        playerList.removeAll(loserList);

        return playerList;
    }

}
