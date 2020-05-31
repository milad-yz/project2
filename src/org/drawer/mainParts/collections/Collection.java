package org.drawer.mainParts.collections;

import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;
import org.stuff.Deck;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Collection {
    Player p;
    JFrame frame ;

    public Collection(JFrame frame, Player p) {
        this.frame=frame;
        this.p = p;
    }

    public void collection() {
        JPanel collectionPanel = new JPanel();
        collectionPanel.setLayout(null);
        collectionPanel.setBounds(0, 0, 1200, 800);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 240, 100, 30);
        collectionPanel.add(backButton);
        backButton.addActionListener(e -> {
            try {
                login.body(p.getUserName(), "back buton", "went to menu");
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.remove(collectionPanel);
            Drawer.getInstance().Enter();
        });
        JLabel EnterRoom = new JLabel("Collection:");
        EnterRoom.setBounds(500, 0, 100, 30);
        collectionPanel.add(EnterRoom);
        //
        JButton cardButton = new JButton("Card");
        cardButton.setBounds(500, 80, 100, 30);
        collectionPanel.add(cardButton);
        cardButton.addActionListener(e -> {
            frame.remove(collectionPanel);
            try {
                login.body(p.getUserName(), "card", "went to card room");
                cards("", 20, 1, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton deckButton = new JButton("Deck");
        deckButton.setBounds(500, 160, 100, 30);
        collectionPanel.add(deckButton);
        deckButton.addActionListener(e -> {
            frame.remove(collectionPanel);
            try {
                login.body(p.getUserName(), "deck", "went to deck room");
                deck();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(collectionPanel);
        frame.repaint();
        frame.revalidate();
    }
    public void cards(String Hero, int mana, int filterCards, Card searchCard) throws IOException {
        CardsShowRoom.getInstance(frame,p,this).cards(Hero,mana,filterCards,searchCard);
    }

    public Card textFinder(String name,ArrayList<Card>cards) {
        double couter = 0, max = 0;
        for (int i = 0; i < cards.size(); i++) {
            couter = similarity(name, cards.get(i).name);
            if (couter > max)
                max = couter;
        }
        for (int i = 0; i < cards.size(); i++) {
            if (similarity(cards.get(i).name, name) == max)
                return cards.get(i);
        }
        return cards.get(0);
    }
    private double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }
    private int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    private void deck() {
        DeckShowRoom.getInstance(frame,p,this).deck();
    }
}
