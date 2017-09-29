/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

/**
 *
 * @author jens.papenhagen
 */
public class Match {

    private int id;
    private int player1ID;
    private int player2ID;
    private int winnerID;

    public Match(int id, int player1ID, int player2ID) {
        this.id = id;
        this.player1ID = player1ID;
        this.player2ID = player2ID;
    }

    public int getId() {
        return id;
    }

    public int getPlayer1ID() {
        return player1ID;
    }

    public int getPlayer2ID() {
        return player2ID;
    }

    public int getWinnerID() {
        return winnerID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer1ID(int player1ID) {
        this.player1ID = player1ID;
    }

    public void setPlayer2ID(int player2ID) {
        this.player2ID = player2ID;
    }

    public void setWinnerID(int winnerID) {
        this.winnerID = winnerID;
    }

}
