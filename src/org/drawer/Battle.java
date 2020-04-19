package org.drawer;

import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;
import org.stuff.Deck;

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
    public Player p;
    public Deck deck;
    public JFrame frame = new JFrame("Battle");
    public int deck2battlePerturn = 1;
    public int mana;
    public int firstMana = 2;
    public int perTurnHeroPower = 1;
    static int counter = 0;
    public Battle(Player p) {
        this.p = p;
        this.deck = p.currentDeck;
        this.mana = firstMana;
        frame.setVisible(true);
        frame.setSize(1200, 700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void passiveInfo() throws IOException {
        login.body(p.getUserName(), "play", "just want to play");
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
                try {
                    map(button.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                deck2battlePerturn = 2;
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
                    if (deck.deckCards.get(i).isminion == 1)
                        deck.deckCards.get(i).damage += 1;
                }
                break;
            case "minionHealth":
                for (int i = 0; i < deck.deckCards.size(); i++) {
                    if (deck.deckCards.get(i).isminion == 1)
                        deck.deckCards.get(i).health += 1;
                }
                break;
        }
    }

    private void map(String passiveInfo) throws IOException {
        JPanel mapPanel = new JPanel(null);
        mapPanel.setBounds(0, 0, 1200, 700);
        //
        JPanel cardPanel = new JPanel(new GridBagLayout());
        //
        JPanel battlepanel1 = new JPanel(new GridBagLayout());
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
        mapPanel.add(eventText);
        //
        JScrollPane battlepanel1Scroll = new JScrollPane(battlepanel1);
        battlepanel1Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        battlepanel1Scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        battlepanel1Scroll.setBounds(0, 350, 900, 150);
        battlepanel1Scroll.setBackground(Color.GRAY);
        mapPanel.add(battlepanel1Scroll);
        //
        ArrayList<Card> inMapCards = shuffleCard(3);
        ArrayList<Card> inBattleCards = new ArrayList<>();
        inHandCard(cardPanel, inMapCards, battlepanel1, eventDoc, manaText, inBattleCards);
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
        HeroButton.setText(deck.deckHero.health + " " + deck.deckHero.heroDefence);
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
            if (inMapCards.size() + deck2battlePerturn <= 12)
                inMapCards.addAll(shuffleCard(deck2battlePerturn));
            else {
                try {
                    justShowCard(mapPanel);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                inHandCard(cardPanel, inMapCards, battlepanel1, eventDoc, manaText, inBattleCards);
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
            frame.setVisible(false);
            Drawer d1 = new Drawer(p);
            d1.Enter();
        });
        mapPanel.add(endGameButton);
        //
        frame.add(mapPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void justShowCard(JPanel panel) throws IOException {
        Random random = new Random();
        int i = random.nextInt(deck.deckCards.size());
        JLabel cardlabel = new JLabel();
        BufferedImage HeroPicture = ImageIO.read(new File(deck.deckCards.get(i).icon));
        cardlabel.setIcon(new ImageIcon(HeroPicture));
        cardlabel.setBounds(900, 530, 75, 110);
        panel.add(cardlabel);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                panel.remove(cardlabel);
                frame.repaint();
                frame.revalidate();
                timer.cancel();
            }
        }, 2000);
        deck.deckCards.remove(i);
    }

    private void inHandCard(JPanel cardPanel, ArrayList<Card> inMapCards, JPanel battlepanel1, Document eventDoc, JLabel manaText, ArrayList<Card> inBattleCards) throws IOException {
        cardPanel.removeAll();
        for (int i = 0; i < inMapCards.size(); i++) {
            JButton inHandCardButton = new JButton();
            BufferedImage myPicture = ImageIO.read(new File(inMapCards.get(i).icon));
            inHandCardButton.setIcon(new ImageIcon(myPicture));
            inHandCardButton.setText(inMapCards.get(i).mana + " " + inMapCards.get(i).damage + " " + inMapCards.get(i).health);
            inHandCardButton.setLayout(new GridBagLayout());
            int finalI = i;
            inHandCardButton.addActionListener(e -> {
                if (mana >= inMapCards.get(finalI).mana && (inBattleCards.size() <= 7 || inMapCards.get(finalI).isminion != 1)) {
                    cardPanel.remove(inHandCardButton);
                    if (inMapCards.get(finalI).isminion == 1) {
                        inBattleCards.add(inMapCards.get(finalI));
                        battlepanel1.add(inHandCardButton);
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
                        inHandCard(cardPanel, inMapCards, battlepanel1, eventDoc, manaText, inBattleCards);
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
