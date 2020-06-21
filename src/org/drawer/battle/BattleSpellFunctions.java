package org.drawer.battle;

import org.drawer.labelsAndButtons.CardButton;
import org.drawer.labelsAndButtons.CardLabel;
import org.stuff.Card;
import org.stuff.Hero;
import org.stuff.Stuff;
import org.stuff.cards.Minion;
import org.stuff.cards.Weapon;
import org.stuff.cards.weapon.GearBlade;
import org.stuff.cards.weapon.HeavyAxe;
import org.stuff.cards.weapon.WickedKnife;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BattleSpellFunctions {

    public static void attack(Battle battle) {
        battle.whoseTurn().setN(8);
        battle.whoseNotTurn().setN(9);
        battle.whoseNotTurn().setM(1);
        battle.semaphoreNotify();
    }

    public static void attackAction(Battle battle, Stuff stuff, int damage) {
        if (stuff.getClass().getSuperclass().getName().equals("org.stuff.Hero")) {
            Hero hero = (Hero) stuff;
            hero.health -= damage;
        } else {
            Minion minion = (Minion) stuff;
            minion.setHealth(minion.getHealth() - damage);
        }
        battle.semaphoreNotify();
    }

    public static void takeMinion(Battle battle) {
        battle.whoseTurn().setN(8);
        battle.whoseNotTurn().setN(9);
        battle.whoseNotTurn().setM(2);
        battle.semaphoreNotify();
    }

    public static void takeMinionAction(Battle battle, Minion minion) {
        for (int i = 0; i < 7; i++) {
            if (battle.whoseTurn().battleCards.get(i) == null) {
                battle.whoseTurn().battleCards.put(i, (Minion) minion.getClone());
                break;
            }
        }
        for (int i = 0; i < 7; i++) {
            if(battle.whoseNotTurn().battleCards.get(i)!=null) {
                if (battle.whoseNotTurn().battleCards.get(i).name.equals(minion.name)) {
                    battle.whoseNotTurn().battleCards.put(i, null);
                    break;
                }
            }
        }
        battle.semaphoreNotify();
    }
    //z=1 weapon
    public static void Discover(Battle battle, int z) {
        if(z==1){
            JPanel chooseYourCardPanel = new JPanel(new GridBagLayout());
            ArrayList<Card> drapedCards = new ArrayList<>();
            drapedCards.add(new GearBlade());
            drapedCards.add(new HeavyAxe());
            drapedCards.add(new WickedKnife());
            for (int i = 0; i < drapedCards.size(); i++) {
                Weapon weapon = (Weapon) drapedCards.get(i);
                CardButton cardButton=new CardButton(weapon);
                cardButton.addActionListener(e->{
                    chooseYourCardPanel.removeAll();
                    battle.whoseTurn().handCards.add(weapon.getClone());
                    battle.whoseTurn().handCards.add(weapon.getClone());
                    chooseYourCardPanel.repaint();
                });
                chooseYourCardPanel.add(cardButton);
            }
            JOptionPane.showMessageDialog(battle.frame, chooseYourCardPanel, "choose one of these 3", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
