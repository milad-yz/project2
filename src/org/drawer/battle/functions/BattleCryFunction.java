package org.drawer.battle.functions;

import org.drawer.battle.Battle;
import org.drawer.battle.PlayerDisplay;
import org.drawer.labelsAndButtons.CardLabel;
import org.stuff.Card;
import org.stuff.Deck;
import org.stuff.cards.Minion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BattleCryFunction {

    public static void drawCards(PlayerDisplay playerDisplay, Battle battle, int n) {
        JPanel chooseYourCardPanel = new JPanel(new GridBagLayout());
        ArrayList<Card> drapedCards = new ArrayList<>();
        drapedCards.addAll(shuffleCard(n, playerDisplay.deck));
        for (int i = 0; i < drapedCards.size(); i++) {
            CardLabel cardLabel = new CardLabel(drapedCards.get(i));
            chooseYourCardPanel.add(cardLabel);
        }
        JOptionPane.showMessageDialog(battle.frame, chooseYourCardPanel, "this card added to your hand", JOptionPane.PLAIN_MESSAGE);
        playerDisplay.handCards.addAll(drapedCards);
    }

    private static ArrayList<Card> shuffleCard(int n, Deck deck) {
        ArrayList<Card> cards = new ArrayList<>();
        Random random = new Random();
        while (n > 0 && deck.deckCards.size() != 0) {
            int i = random.nextInt(deck.deckCards.size());
            if(deck.deckCards.size()>i) {
                cards.add(deck.deckCards.get(i));
                deck.deckCards.remove(i);
            }
            n--;
        }
        return cards;
    }


    public static void swapDamageAndHealth(Battle battle) {
        if (!allNull(battle.whoseNotTurn().battleCards) || !allNull(battle.whoseTurn().battleCards)) {
            battle.whoseNotTurn().setM(1);
            battle.whoseTurn().setM(1);
            battle.whoseTurn().setN(6);
            battle.whoseNotTurn().setN(6);
            battle.semaphoreNotify();
        }
    }

    public static void swapDamageAndHealthAction(Minion minion) {
        int temp = minion.getHealth();
        minion.setHealth(minion.getDamage());
        minion.setDamage(temp);
    }

    public static void dealDamage2allEnemyMinion(HashMap<Integer, Minion> battleCards) {
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) != null)
                battleCards.get(i).setHealth(battleCards.get(i).getHealth() - 1);
        }
    }

    public static void copy2deck2handAction(PlayerDisplay playerDisplay, Minion minion) {
        playerDisplay.handCards.add(minion.getClone());
        playerDisplay.deck.deckCards.add(minion.getClone());
        playerDisplay.semaphoreNotify();
    }

    public static void copy2deck2hand(Battle battle) {
        if (!allNull(battle.whoseNotTurn().battleCards) || !allNull(battle.whoseTurn().battleCards)) {
            battle.whoseNotTurn().setM(1);
            battle.whoseTurn().setM(1);
            battle.whoseTurn().setN(7);
            battle.whoseNotTurn().setN(3);
            battle.semaphoreNotify();
        }
    }

    private static boolean allNull(HashMap<Integer, Minion> hashMap) {
        for (int i = 0; i < 7; i++) {
            if (hashMap.get(i) != null)
                return false;
        }
        return true;
    }

    public static void summon(Battle battle, Minion minion) {
        for (int i = 0; i < 7; i++) {
            if (battle.whoseTurn().battleCards.get(i) == null) {
                battle.whoseTurn().battleCards.put(i,minion);
                return;
            }
        }
    }
}
