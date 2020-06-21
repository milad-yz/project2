package org.drawer.battle;

import org.fileWorks.login;
import org.player.Player;
import org.stuff.Deck;
import org.stuff.cards.Minion;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class PassiveInfo {
    private Player p;
    private Deck deck;
    private JFrame frame;
    private int deck2battlePerTurn = 1;
    private int mana;
    private int firstMana = 2;
    private int perTurnHeroPower = 1;

    public PassiveInfo(Player p, JFrame frame) {
        this.p = p;
        this.deck = p.currentDeck.getClone();
        this.mana = firstMana;
        this.frame = frame;
        draw();
    }

    private void draw() {
        try {
            login.body(p.getUserName(), "play", "just want to play");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashSet<Integer> randomVariables = new HashSet<>();
        Random random = new Random();
        while (randomVariables.size() < 3) {
            randomVariables.add(random.nextInt(5));
        }
        Iterator<Integer> itr = randomVariables.iterator();
        ArrayList<String> passiveInfo = new ArrayList<>();
        passiveInfo.add("freePower");
        passiveInfo.add("twiceDraw");
        passiveInfo.add("offCards");
        passiveInfo.add("manaJump");
        passiveInfo.add("HeroPower");
        passiveInfo.add("minionDamage");
        passiveInfo.add("minionHealth");
        //
        JPanel passiveInfoPanel = new JPanel(null);
        passiveInfoPanel.setBounds(0, 0, 1200, 700);
        //
        JLabel label = new JLabel("select your passive Info");
        label.setBounds(500, 0, 300, 30);

        for (int i = 0; i < 3; i++) {
            JButton button = new JButton(passiveInfo.get(itr.next()));
            button.setBounds(250 * i + 100, 400, 200, 50);
            button.addActionListener(e -> {
                frame.remove(passiveInfoPanel);
                changePassiveInfo(button.getText());
                new Battle(frame,p,deck,deck2battlePerTurn,mana,perTurnHeroPower,firstMana);
            });
            passiveInfoPanel.add(button);
        }
        //
        frame.add(passiveInfoPanel);
        frame.repaint();
        frame.revalidate();
    }
    private void changePassiveInfo(String passiveInfo) {
        switch (passiveInfo) {
            case "freePower":
                deck.deckHero.mana -= 1;
                perTurnHeroPower = 2;
                break;
            case "offCards":
                for (int i = 0; i < deck.deckCards.size(); i++) {
                    deck.deckCards.get(i).mana -= 1;
                }
                break;
            case "twiceDraw":
                deck2battlePerTurn = 2;
                break;
            case "manaJump":
                firstMana = 3;
                mana = 3;
                break;
            case "HeroPower":
                deck.deckHero.health += 10;
                break;
            case "minionDamage":
                for (int i = 0; i < deck.deckCards.size(); i++) {
                    if (deck.deckCards.get(i).getClass().getName().equals("Minion")) {
                        Minion minion = (Minion) deck.deckCards.get(i);
                        minion.setDamage(minion.getDamage()+1);
                    }
                }
                break;
            case "minionHealth":
                for (int i = 0; i < deck.deckCards.size(); i++) {
                    if (deck.deckCards.get(i).getClass().getName().equals("Minion")) {
                        Minion minion = (Minion) deck.deckCards.get(i);
                        minion.setHealth(minion.getHealth()+1);
                    }
                }
                break;
        }
    }
}
