package org.drawer.battle.functions;

import org.drawer.battle.Battle;
import org.drawer.battle.BattleHandler;
import org.drawer.battle.PlayerDisplay;
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
import java.util.Random;

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
            if(minion.name.equals("SecurityRover")){
                BattleHandler.damagedStuffHandler(battle,battle.whoseNotTurn(),minion);
            }
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
            if (battle.whoseTurn().battleCards.get(i) != null) {
                battle.whoseTurn().battleCards.get(i).setHealth(battle.whoseTurn().battleCards.get(i).getHealth() - damage);
                if(battle.whoseTurn().battleCards.get(i).name.equals("SecurityRover")){
                    BattleHandler.damagedStuffHandler(battle,battle.whoseTurn(),battle.whoseTurn().battleCards.get(i));
                }
            }
            if (battle.whoseNotTurn().battleCards.get(i) != null) {
                battle.whoseNotTurn().battleCards.get(i).setHealth(battle.whoseNotTurn().battleCards.get(i).getHealth() - damage);
                if(battle.whoseNotTurn().battleCards.get(i).name.equals("SecurityRover")){
                    BattleHandler.damagedStuffHandler(battle,battle.whoseNotTurn(),battle.whoseNotTurn().battleCards.get(i));
                }
            }
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

    public static void summon(Battle battle, PlayerDisplay playerDisplay, Minion minion, int summonNumber) {
        int j=0;
        for (int i = 0; i < 7&&j<summonNumber; i++) {
            if(playerDisplay.battleCards.get(i)==null){
                Minion summonMinion=(Minion)minion.getClone();
                if(!summonMinion.name.equals("TombWarden"))
                BattleHandler.battleCryHandler(playerDisplay,battle,summonMinion);
                BattleFunctions.summonNotify(battle,playerDisplay,summonMinion);
                playerDisplay.battleCards.put(i,summonMinion);
                j++;
            }
        }
        battle.semaphoreNotify();
    }

    public static void giveHealth2minions(Battle battle, HashMap<Integer, Minion> battleCards, int health) {
        for (int i = 0; i <7; i++) {
            if(battleCards.get(i)!=null){
                battleCards.get(i).setHealth(battleCards.get(i).getHealth()+health);
            }
        }
        battle.semaphoreNotify();
    }

    public static void summonFromDeck(Battle battle, int summonNumber) {
        int j=0;
        ArrayList<Minion>minions=new ArrayList<>();
        for (int i = 0; i < battle.whoseTurn().deck.deckCards.size()&&j<summonNumber; i++) {
            if(battle.whoseTurn().deck.deckCards.get(i).getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")){
                minions.add((Minion)battle.whoseTurn().deck.deckCards.get(i));
            }
        }
        for (int k = 0; k < 7; k++) {
            if(battle.whoseTurn().battleCards.get(k)==null){
                Random random =new Random();
                Minion summonMinion=minions.get(random.nextInt(minions.size()-1));
                battle.whoseTurn().battleCards.put(k,summonMinion);
                BattleHandler.battleCryHandler(battle.whoseTurn(),battle,summonMinion);
                BattleFunctions.summonNotify(battle,battle.whoseTurn(),summonMinion);
                j++;
                if(j>=summonNumber){
                    break;
                }
            }
        }
        battle.semaphoreNotify();
    }

    public static void ArmyKnife(Battle battle) {
        battle.whoseTurn().setN(7);
        battle.whoseTurn().setM(2);
        battle.whoseNotTurn().setN(3);
        battle.semaphoreNotify();
    }

    public static void ArmyKnifeAction(Battle battle, Minion minion) {
        minion.haveShield=true;
        minion.haveTaunt=true;
        minion.havePoisonous=true;
        minion.haveLifeSteal=true;
        minion.perTurnAttack=2;
        battle.semaphoreNotify();
    }

    public static void blessing(Battle battle) {
        battle.whoseTurn().setN(7);
        battle.whoseTurn().setM(3);
        battle.whoseNotTurn().setN(3);
        battle.semaphoreNotify();
    }

    public static void blessingAction(Battle battle, Minion minion) {
        minion.setHealth(minion.getHealth()+4);
        minion.setDamage(minion.getDamage()+4);
        minion.haveTaunt=true;
        minion.haveShield=true;
        battle.semaphoreNotify();
    }
}
