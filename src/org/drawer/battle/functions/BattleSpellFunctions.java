package org.drawer.battle.functions;

import org.drawer.battle.Battle;
import org.drawer.labelsAndButtons.CardButton;
import org.stuff.Card;
import org.stuff.Hero;
import org.stuff.Stuff;
import org.stuff.cards.Minion;
import org.stuff.cards.Weapon;
import org.stuff.cards.minions.Sheep;
import org.stuff.cards.weapon.GearBlade;
import org.stuff.cards.weapon.HeavyAxe;
import org.stuff.cards.weapon.WickedKnife;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BattleSpellFunctions {

    public static void dealDamage(Battle battle) {
        battle.whoseTurn().setN(8);
        battle.whoseNotTurn().setN(9);
        battle.whoseNotTurn().setM(1);
        battle.semaphoreNotify();
    }

    public static void dealDamageAction(Battle battle, Stuff stuff, int damage) {
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
            if (battle.whoseNotTurn().battleCards.get(i) != null) {
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
        if (z == 1) {
            JPanel chooseYourCardPanel = new JPanel(new GridBagLayout());
            ArrayList<Card> drapedCards = new ArrayList<>();
            drapedCards.add(new GearBlade());
            drapedCards.add(new HeavyAxe());
            drapedCards.add(new WickedKnife());
            for (int i = 0; i < drapedCards.size(); i++) {
                Weapon weapon = (Weapon) drapedCards.get(i);
                CardButton cardButton = new CardButton(weapon);
                cardButton.addActionListener(e -> {
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

    public static void dealDamage2all(Battle battle, int damage) {
        for (int i = 0; i < 7; i++) {
            if (battle.whoseTurn().battleCards.get(i) != null)
                battle.whoseTurn().battleCards.get(i).setHealth(battle.whoseTurn().battleCards.get(i).getHealth() - damage);
            if (battle.whoseNotTurn().battleCards.get(i) != null)
                battle.whoseNotTurn().battleCards.get(i).setHealth(battle.whoseNotTurn().battleCards.get(i).getHealth() - damage);
        }
        battle.whoseTurn().hero.health -= damage;
        battle.whoseNotTurn().hero.health -= damage;
        battle.semaphoreNotify();
    }

    public static void giveHealth2hero(Battle battle, int health) {
        battle.whoseTurn().hero.health += health;
        battle.semaphoreNotify();
    }

    public static void convert2Sheep(Battle battle) {
        battle.whoseTurn().setN(8);
        battle.whoseNotTurn().setN(9);
        battle.whoseNotTurn().setM(3);
        battle.semaphoreNotify();
    }

    public static void convert2SheepAction(Battle battle, Minion minion) {
        for (int i = 0; i < 7; i++) {
            if (battle.whoseNotTurn().battleCards.get(i) != null) {
                if (battle.whoseNotTurn().battleCards.get(i) == minion) {
                    battle.whoseNotTurn().battleCards.put(i, new Sheep());
                }
            }
        }
        battle.semaphoreNotify();
    }

    public static void summon(Battle battle, Minion minion, int summonNumber) {
        int j=0;
        for (int i = 0; i < 7&&j<summonNumber; i++) {
            if(battle.whoseTurn().battleCards.get(i)==null){
                battle.whoseTurn().battleCards.put(i,(Minion)minion.getClone());
                j++;
            }
        }
        battle.semaphoreNotify();
    }

    public static void giveHealth2minions(Battle battle, HashMap<Integer, Minion> battleCards, int health) {
        for (int i = 0; i <7; i++) {
            if(battleCards.get(i)!=null){
                battleCards.get(i).setHealth(battleCards.get(i).getHealth()+2);
            }
        }
        battle.semaphoreNotify();
    }

    public static void summonFromDeck(Battle battle, int summonNumber) {
        int j=0;
        for (int i = 0; i < battle.whoseTurn().deck.deckCards.size()&&j<summonNumber; i++) {
            if(battle.whoseTurn().deck.deckCards.get(i).getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")){
                for (int k = 0; k < 7; k++) {
                    if(battle.whoseTurn().battleCards.get(k)==null){
                        battle.whoseTurn().battleCards.put(k,(Minion) battle.whoseTurn().deck.deckCards.get(i).getClone());
                        break;
                    }
                }
                j++;
            }
        }
        battle.semaphoreNotify();
    }
}
