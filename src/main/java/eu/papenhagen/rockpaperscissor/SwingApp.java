/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor;

import eu.papenhagen.rockpaperscissor.Service.Behavor;
import eu.papenhagen.rockpaperscissor.Entities.Match;
import eu.papenhagen.rockpaperscissor.Entities.Player;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.ListModel;

/**
 *
 * @author jens.papenhagen
 */
public final class SwingApp extends javax.swing.JDialog {

    public int playerID1;
    public int playerID2;
    public DefaultListModel protocolModel;
    //icon
    Image img = new ImageIcon(SwingApp.class.getResource("/images/icon.jpg")).getImage();

    public SwingApp(java.awt.Frame parent, boolean modal) throws IOException {
        super(parent, modal);
        this.protocolModel = new DefaultListModel();
        this.playerID2 = 4;
        this.playerID1 = 3;

        initComponents();
        fight(3, 4);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        player1Nr = new javax.swing.JLabel();
        player1Name = new javax.swing.JLabel();
        player1symbole = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        player2Nr = new javax.swing.JLabel();
        player2Name = new javax.swing.JLabel();
        player2symbole = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        winnerLable = new javax.swing.JLabel();
        MatchButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        removedPlayerID = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        roundCounter = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        RoundProtocol = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Stein Schere Papier Game");
        setBackground(new java.awt.Color(204, 0, 51));
        setIconImage(img);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Stein Schere Papier");

        jLabel2.setText("Spieler:");

        player1Nr.setText("nr");

        player1Name.setText("Spielername");

        player1symbole.setText("player1symbol");

        jLabel3.setText("Spieler:");

        player2Nr.setText("nr");

        player2Name.setText("Spielername");

        player2symbole.setText("player2symbol");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel4.setText("VS");

        jLabel5.setText("And the Winner is:");

        winnerLable.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        winnerLable.setText("player");

        MatchButton.setText("Match");
        MatchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MatchButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("To Remove ID:");

        removedPlayerID.setText("lost");

        jLabel7.setText("Round Counter");

        roundCounter.setText("roundcount");

        RoundProtocol.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(RoundProtocol);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(player1symbole, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(player1Nr))
                            .addComponent(player1Name))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(player2Nr))
                            .addComponent(player2Name)
                            .addComponent(player2symbole, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(121, 121, 121))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(winnerLable))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roundCounter))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MatchButton)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(removedPlayerID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(47, 47, 47)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
                        .addGap(0, 320, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(player2Nr))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(player2Name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(player2symbole, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(player1Nr))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(player1Name)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(player1symbole, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jLabel4)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(winnerLable))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(MatchButton)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(removedPlayerID)))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(roundCounter))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MatchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MatchButtonActionPerformed
        randomFight();
    }//GEN-LAST:event_MatchButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SwingApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            SwingApp dialog = null;
            try {
                dialog = new SwingApp(new javax.swing.JFrame(), true);
            } catch (IOException ex) {
                Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton MatchButton;
    private javax.swing.JList<String> RoundProtocol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel player1Name;
    private javax.swing.JLabel player1Nr;
    private javax.swing.JLabel player1symbole;
    private javax.swing.JLabel player2Name;
    private javax.swing.JLabel player2Nr;
    private javax.swing.JLabel player2symbole;
    private javax.swing.JLabel removedPlayerID;
    private javax.swing.JLabel roundCounter;
    private javax.swing.JLabel winnerLable;
    // End of variables declaration//GEN-END:variables

    public void fight(int ID1, int ID2) {
        getCleanProtocol(); //clean the protocol

        try {
            Thread.sleep(5000);
            //give out the view
            Player.SYMBOLE symbole1 = showPlayer1(ID1);
            Player.SYMBOLE symbole2 = showPlayer2(ID2);
            changeRoundCounter(0 + "");

            //fight
            Enum result = null;
            
            Player.SYMBOLE tempsymbole =  symbole1;
            if (tempsymbole.loseAgaist( symbole1, symbole2)) {
                result = Match.Fightstat.LOST;
            } else if (player1symbole.equals(player2symbole)) {
                result = Match.Fightstat.DRAW;
            } else {
                result = Match.Fightstat.WON;
            }
            
            
            
            addToProtocol("Ausgabe normal Fight: " + result);
            //fight again if the fight was a draw
            if (result.equals(Match.Fightstat.DRAW)) {
                addToProtocol("First Match was a draw, NOW Round 1");
                result = runde(ID1, ID2, symbole1, symbole2);
            }
            
            
            
            
            //removce the lost player
            toRemoveID(result, ID1, ID2);

            changeWinnerLable("Player 1 has: " +result);

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SwingApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        getProtocol(protocolModel);

    }

    public Enum runde(int ID1, int ID2, Player.SYMBOLE lastPlayer1Symbole, Player.SYMBOLE lastPlayer2Symbole)  {
        Enum result = null;
        Behavor behv = new Behavor();

        int maxrounds = 5;
        for (int rounds = 1; rounds <= maxrounds; rounds++) {
            changeRoundCounter("" + rounds);

            //fight
            result = null;
            
            Player.SYMBOLE tempsymbole = lastPlayer1Symbole;
            if (tempsymbole.loseAgaist( behv.getBehavor(lastPlayer1Symbole),  behv.getBehavor(lastPlayer2Symbole))) {
                result = Match.Fightstat.LOST;
            } else if (player1symbole.equals(player2symbole)) {
                result = Match.Fightstat.DRAW;
            } else {
                result = Match.Fightstat.WON;
            }
            
            

            addToProtocol("Runden "+ rounds +" Ergebnis: " + result);
            if (!result.equals(Match.Fightstat.DRAW)) {
                break;
            }

            if (rounds == maxrounds) {
                //froce win
                result = Match.Fightstat.WON;
                break;
            }

        }
  
        return result;
    }

    public void toRemoveID(Enum result, int ID1, int ID2) {
        //remove the loser
        Integer removePlayerID = 0;
        try {
            if (result.equals(Match.Fightstat.WON)) {
                removePlayerID = ID2;
            } else {
                removePlayerID = ID1;
            }
        } catch (NullPointerException ex) {
            //froce win
            removePlayerID = ID1;
        }

        changeRemovePlayerIDLable("" + removePlayerID);
    }

    public void randomFight() {
        getCleanProtocol(); //clean the protocol
        Random random = new Random();
        int randomPlayerNr1 = random.nextInt((10 - 1) + 1) + 1;
        int randomPlayerNr2 = random.nextInt((10 - 1) + 1) + 1;

        if (randomPlayerNr1 == randomPlayerNr2) {
            randomPlayerNr2 = randomPlayerNr1 + 1;
        }

        fight(randomPlayerNr1, randomPlayerNr2);
    }

    public void changeRoundCounter(String input) {
        roundCounter.setText(input + "");
    }

    public void changeWinnerLable(String input) {
        winnerLable.setText(input);
    }

    public void changeRemovePlayerIDLable(String input) {
        removedPlayerID.setText(input);
    }

    public Player.SYMBOLE showPlayer1(int playerID1) throws IOException {
        Player p1 = new Player(playerID1, Player.PLAYERCONDITION.PLAYER );

        Player.SYMBOLE symbole1 = p1.getSymbole();

        BufferedImage playerSymbole = givebackImg(symbole1);
        ImageIcon imageIcon = new ImageIcon(playerSymbole);

        player1symbole.setIcon(imageIcon);
        player1Name.setText(p1.getName());
        player1Nr.setText(playerID1 + "");

        addToProtocol("Player1: " + symbole1);

        return symbole1;

    }

    public Player.SYMBOLE showPlayer2(int playerID2) throws IOException {
        Player p2 = new Player(playerID2, Player.PLAYERCONDITION.PLAYER);

        Player.SYMBOLE symbole2 = p2.getSymbole();

        BufferedImage playerSymbole = givebackImg(symbole2);
        ImageIcon imageIcon = new ImageIcon(playerSymbole);

        player2symbole.setIcon(imageIcon);
        player2Name.setText(p2.getName());
        player2Nr.setText("" + playerID2);

        addToProtocol("Player2: " + symbole2);

        return symbole2;
    }

    public BufferedImage givebackImg(Player.SYMBOLE symbole) throws IOException {
        BufferedImage myPicture;
        if (symbole.equals(Player.SYMBOLE.SCISSOR)) {
            myPicture = ImageIO.read(SwingApp.class.getResource("/images/scissor.png"));
        }
        if (symbole.equals(Player.SYMBOLE.PAPER)) {
            myPicture = ImageIO.read(SwingApp.class.getResource("/images/paper.png"));
        }
        if (symbole.equals(Player.SYMBOLE.ROCK)) {
            myPicture = ImageIO.read(SwingApp.class.getResource("/images/rock.png"));
        } else {
            myPicture = ImageIO.read(SwingApp.class.getResource("/images/scissor.png"));
        }

        return myPicture;
    }

    public void addToProtocol(String input) {
        protocolModel.addElement(input);
    }

    public void getProtocol(ListModel protocol) {
        //scroll to button of the List
        int lastIndex = RoundProtocol.getModel().getSize() - 1;
        if (lastIndex >= 0) {
            RoundProtocol.ensureIndexIsVisible(lastIndex);
        }
        
        RoundProtocol.setModel(protocol);
    }

    public void getCleanProtocol() {
        RoundProtocol.removeAll();
        protocolModel.removeAllElements();
    }

}
