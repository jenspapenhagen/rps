/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.papenhagen.rockpaperscissor.EAO;

/**
 *
 * @author jens.papenhagen
 */
public class TierHandler {

    /**
     * giveback the count of max games for this tier. onyl 2 fights are left do
     * not half the fightcount on odd fights add one fight befor half the
     * fightcount
     *
     * @param numbersOfFights number of fights
     * @return maxfights for next tier
     */
    public static int getMaxFightsInTier(int numbersOfFights) {
        int output;
        if (numbersOfFights == 2) {
            //if onyl 2 player are left
            output = numbersOfFights; //lastround
        } else if (numbersOfFights % 2 == 0) {
            //if max games count is even
            output = numbersOfFights / 2;
        } else {
            //if odd, add one match number befor half the numbers
            output = (numbersOfFights + 1) / 2;
        }
        return output;
    }

    /**
     * calulateMaxTiers by simble up counting
     *
     * @param player for the maximal amout of tiers
     * @return the amount of tiers the game can run
     */
    public static int calulateMaxTiers(int player) {
        //calculate the maxTiers
        int maxLevle = 1;
        //loop and count maxLevle one up so long i can dif. player into 2
        while (player / 2 != 1) {
            maxLevle++;
            player = player / 2;
            //adding one up to odd numbers but only if player count smaller 1
            if (player % 2 != 0 && player > 1) {
                player = player + 1;
            }
        }
        return maxLevle;
    }
    
}
