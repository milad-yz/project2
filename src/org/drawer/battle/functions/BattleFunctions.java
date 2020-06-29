package org.drawer.battle.functions;

import org.drawer.battle.Battle;
import org.drawer.battle.PlayerDisplay;
import org.stuff.cards.Minion;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class BattleFunctions {

    public static void paladinSpecialPower(PlayerDisplay playerDisplay) {
        ArrayList<Integer> haveMinion = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (playerDisplay.battleCards.get(i) != null)
                haveMinion.add(i);
        }
        if (haveMinion.size() > 0) {
            Random random = new Random();
            int randomInt = haveMinion.get(random.nextInt(haveMinion.size()));
            playerDisplay.battleCards.get(randomInt).setDamage(playerDisplay.battleCards.get(randomInt).getDamage() + 1);
            playerDisplay.battleCards.get(randomInt).setHealth(playerDisplay.battleCards.get(randomInt).getHealth() + 1);
        }
    }

    public static void drawCardNotify(PlayerDisplay playerDisplay) {
        for (int i = 0; i < 7; i++) {
            if (playerDisplay.battleCards.get(i) != null)
                if (playerDisplay.battleCards.get(i).name.equals("CurioCollector")) {
                    playerDisplay.battleCards.get(i).setDamage(playerDisplay.battleCards.get(i).getDamage() + 1);
                    playerDisplay.battleCards.get(i).setHealth(playerDisplay.battleCards.get(i).getHealth() + 1);
                }
        }
        playerDisplay.semaphoreNotify();
    }

    public static void summonNotify(Battle battle, PlayerDisplay playerDisplay, Minion summonMinion) {
        for (int i = 0; i < 7; i++) {
            if (playerDisplay.battleCards.get(i) != null)
                if (playerDisplay.battleCards.get(i).name.equals("HighPriestAmet")) {
                    summonMinion.setHealth(playerDisplay.battleCards.get(i).getHealth());
                }
        }
        battle.semaphoreNotify();
    }

    public static void dealDamage2allMinions(Battle battle) {
        for (int i = 0; i < 7; i++) {
            if (battle.whoseTurn().battleCards.get(i) != null)
                battle.whoseTurn().battleCards.get(i).setHealth(battle.whoseTurn().battleCards.get(i).getHealth() - 1);
            if (battle.whoseNotTurn().battleCards.get(i) != null)
                battle.whoseNotTurn().battleCards.get(i).setHealth(battle.whoseNotTurn().battleCards.get(i).getHealth() - 1);
        }
    }

    public static void battleCardsUpdate(PlayerDisplay playerDisplay, Minion minion) {
        for (int i = 0; i < 7; i++) {
            if (playerDisplay.battleCards.get(i) != null)
                if (playerDisplay.battleCards.get(i).name.equals("RaidLeader"))
                    minion.setDamage(minion.getDamage() + 1);

        }
    }

    public static void animatedCard(JPanel panel, int i,boolean playerTurn, Minion minion){
        int x1,x2,y1,y2;
        if(playerTurn){
            x1=450;
            y1=530;
            y2=330;
            x2=110 + 90 * i;
        }else{
            x1=450 ;
            y1=0;
            y2=200;
            x2=110 + 90 * i;
        }
        new AnimationCard(panel,x1,y1,x2,y2,minion);
    }

    public static void attackFromSwamp(Battle battle, Minion minion) {
        for (int i = 0; i < 7; i++) {
            if(battle.whoseNotTurn().battleCards.get(i)!=null){
                if(battle.whoseNotTurn().battleCards.get(i).name.equals("SwampKingDread")){
                    battle.whoseNotTurn().battleCards.get(i).attack(minion);
                }
            }
        }
    }
}
