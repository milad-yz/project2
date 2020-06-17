package org.drawer.battle;

import org.drawer.CardButton;
import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;
import org.stuff.Deck;
import org.stuff.cards.Minion;
import org.stuff.cards.QuestAndReward;
import org.stuff.cards.Spell;
import org.stuff.cards.Weapon;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class Battle {
    private Player p;
    private Deck deck;
    private JFrame frame;
    private int deck2battlePerTurn = 1;
    private int mana;
    private int firstMana = 2;
    private int perTurnHeroPower = 1;
    private static int counter = 0;

    public Battle(JFrame frame, Player p, Deck deck, int deck2battlePerTurn, int mana, int perTurnHeroPower) {
        this.p = p;
        this.frame = frame;
        this.deck = deck;
        this.deck2battlePerTurn = deck2battlePerTurn;
        this.mana = mana;
        this.perTurnHeroPower = perTurnHeroPower;
        try {
            map();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void map() throws IOException {
        JPanel mapPanel = new JPanel(null);
        mapPanel.setBounds(0, 0, 1200, 700);
        //
        JPanel cardPanel = new JPanel(new GridBagLayout());
        //
        JPanel battlePanel1 = new JPanel(new GridBagLayout());
        //
        JLabel manaText = new JLabel();
        manaText.setText("your mana is : \n" + mana);
        manaText.setBounds(750, 530, 150, 110);
        manaText.setBackground(Color.CYAN);
        mapPanel.add(manaText);
        //
        JTextArea eventText = new JTextArea();
        Document eventDoc = eventText.getDocument();
        eventText.setBackground(Color.GREEN);
        eventText.setBounds(900, 200, 200, 300);
        //
        JScrollPane textScroll = new JScrollPane(eventText);
        textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textScroll.setBounds(900, 200, 200, 300);
        textScroll.setBackground(Color.YELLOW);
        mapPanel.add(textScroll);
        //
        JScrollPane battlePanel1Scroll = new JScrollPane(battlePanel1);
        battlePanel1Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        battlePanel1Scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        battlePanel1Scroll.setBounds(0, 350, 900, 150);
        battlePanel1Scroll.setBackground(Color.GRAY);
        mapPanel.add(battlePanel1Scroll);
        //
        ArrayList<Card> inMapCards = shuffleCard(3);
        chooseYourCard(inMapCards);
        ArrayList<Card> inBattleCards = new ArrayList<>();
        inHandCard(cardPanel, inMapCards, battlePanel1, eventDoc, manaText, inBattleCards);
        //
        JScrollPane cardScroll = new JScrollPane(cardPanel);
        cardScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        cardScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cardScroll.setBounds(230, 500, 500, 150);
        cardScroll.setBackground(Color.YELLOW);
        mapPanel.add(cardScroll);
        //
        JButton HeroButton = new JButton();
        BufferedImage HeroPicture = ImageIO.read(new File(deck.deckHero.icon));
        HeroButton.setIcon(new ImageIcon(HeroPicture));
        HeroButton.setText(deck.deckHero.health + " ");
        HeroButton.setBounds(0, 530, 150, 110);
        HeroButton.addActionListener(e -> {
            frame.repaint();
            frame.revalidate();
        });
        mapPanel.add(HeroButton);
        //
        JButton HeroPowerButton = new JButton();
        BufferedImage HeroPowerPicture = ImageIO.read(new File(deck.deckHero.HeroPowerIcon));
        HeroPowerButton.setIcon(new ImageIcon(HeroPowerPicture));
        HeroPowerButton.setBounds(150, 530, 75, 100);
        HeroPowerButton.addActionListener(e -> {
            frame.repaint();
            frame.revalidate();
        });
        mapPanel.add(HeroPowerButton);
        //
        JLabel deckSize = new JLabel();
        deckSize.setText("your deck size is : \n" + deck.deckCards.size());
        deckSize.setBounds(950, 100, 200, 30);
        deckSize.setBackground(Color.CYAN);
        mapPanel.add(deckSize);
        //
        JButton endTurnButton = new JButton("end turn");
        endTurnButton.setBounds(950, 60, 150, 30);
        endTurnButton.addActionListener(e -> {
            counter++;
            if (counter < 10)
                mana = counter + firstMana;
            else
                mana = 10;
            if (mana > 10)
                mana = 10;
            manaText.setText("your mana is : \n" + mana);
            if (inMapCards.size() + deck2battlePerTurn <= 12)
                inMapCards.addAll(shuffleCard(deck2battlePerTurn));
            else {
                try {
                    justShowCard(mapPanel);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                inHandCard(cardPanel, inMapCards, battlePanel1, eventDoc, manaText, inBattleCards);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            deckSize.setText("your deck size is : \n" + deck.deckCards.size());
            frame.repaint();
            frame.revalidate();
        });
        mapPanel.add(endTurnButton);
        //
        JButton endGameButton = new JButton("end Game");
        endGameButton.setBounds(950, 20, 150, 30);
        endGameButton.addActionListener(e -> {
            frame.remove(mapPanel);
            Drawer.getInstance().Enter();
        });
        mapPanel.add(endGameButton);
        //
        frame.add(mapPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void chooseYourCard(ArrayList<Card> inMapCards) throws IOException {
        JPanel chooseYourCardPanel = new JPanel(new GridBagLayout());
        for (int i = 0; i < inMapCards.size(); i++) {
            CardButton cardButton = new CardButton(inMapCards.get(i));
            Card card = inMapCards.get(i);
            cardButton.addActionListener(e -> {
                deck.deckCards.add(card);
                chooseYourCardPanel.remove(cardButton);
                inMapCards.remove(card);
                inMapCards.addAll(shuffleCard(1));
                chooseYourCardPanel.repaint();
            });
            chooseYourCardPanel.add(cardButton);
        }
        JOptionPane.showMessageDialog(frame, chooseYourCardPanel, "choose your card", JOptionPane.PLAIN_MESSAGE);
    }

    private void justShowCard(JPanel panel) throws IOException {
        Random random = new Random();
        int i = random.nextInt(deck.deckCards.size());
        JLabel cardLabel = new JLabel();
        BufferedImage HeroPicture = ImageIO.read(new File(deck.deckCards.get(i).icon));
        cardLabel.setIcon(new ImageIcon(HeroPicture));
        cardLabel.setBounds(900, 530, 75, 110);
        panel.add(cardLabel);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                panel.remove(cardLabel);
                frame.repaint();
                frame.revalidate();
                timer.cancel();
            }
        }, 2000);
        deck.deckCards.remove(i);
    }

    private void inHandCard(JPanel cardPanel, ArrayList<Card> inMapCards, JPanel battlePanel1, Document eventDoc, JLabel manaText, ArrayList<Card> inBattleCards) throws IOException {
        cardPanel.removeAll();
        for (int i = 0; i < inMapCards.size(); i++) {
            CardButton inHandCardButton = new CardButton(inMapCards.get(i));
            int finalI = i;
            inHandCardButton.addActionListener(e -> {
                if (mana >= inMapCards.get(finalI).mana && (inBattleCards.size() <= 7 || !inMapCards.get(finalI).getClass().getName().equals("Minion"))) {
                    cardPanel.remove(inHandCardButton);
                    if (inMapCards.get(finalI).getClass().getName().equals("org.stuff.cards.Minion")) {
                        inBattleCards.add(inMapCards.get(finalI));
                        battlePanel1.add(inHandCardButton);
                    }
                    mana -= inMapCards.get(finalI).mana;
                    try {
                        eventDoc.insertString(eventDoc.getLength(), p.getUserName() + " played " + inMapCards.get(finalI).name + "\n", null);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    manaText.setText("your mana is : \n" + mana);
                    try {
                        login.body(p.getUserName(), p.getUserName(), " played " + inMapCards.get(finalI).name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    inMapCards.remove(finalI);
                    try {
                        inHandCard(cardPanel, inMapCards, battlePanel1, eventDoc, manaText, inBattleCards);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    frame.repaint();
                    frame.revalidate();
                } else {
                    frame.repaint();
                    frame.revalidate();
                }
            });
            cardPanel.add(inHandCardButton);
        }
    }

    private ArrayList<Card> shuffleCard(int n) {
        ArrayList<Card> cards = new ArrayList<>();
        Random random = new Random();
        while (n > 0 && deck.deckCards.size() != 0) {
            int i = random.nextInt(deck.deckCards.size());
            cards.add(deck.deckCards.get(i));
            deck.deckCards.remove(i);
            n--;
        }
        return cards;
    }
}
