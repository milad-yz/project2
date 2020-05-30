package org.drawer.mainParts.collections;

import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;
import org.stuff.Deck;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
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
                deck("");
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
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(null);
        cardsPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel cardsLabel = new JLabel("this is all cards");
        cardsLabel.setBounds(500, 0, 400, 30);
        cardsPanel.add(cardsLabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        cardsPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(cardsPanel);
            try {
                login.body(p.getUserName(), "back buton", "went to collection");
                collection();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JPanel temp = new JPanel(new GridBagLayout());
        //
        for (int i = 0; i < p.allcards.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < p.currentcards.size(); j++) {
                if (p.currentcards.get(j).name.equals(p.allcards.get(i).name)) {
                    flag = false;
                    break;
                }
            }
            if (flag && (searchCard == null || searchCard == p.allcards.get(i)) && (filterCards == 1 || filterCards == 3) && (p.allcards.get(i).mana == mana || mana == 20) && (p.allcards.get(i).specialFor.equals(Hero) || Hero.equals(""))) {
                BufferedImage myPicture = ImageIO.read(new File(p.allcards.get(i).icon));
                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                ColorConvertOp ccop = new ColorConvertOp(cs, null);
                myPicture = ccop.filter(myPicture, null);
                //
                JButton cardButton = new JButton();
                cardButton.setIcon(new ImageIcon(myPicture));
                cardButton.setBounds((i % 4) * 150, (i / 4) * 200, 75, 100);
                temp.add(cardButton);
                int finalI = i;
                cardButton.addActionListener(e -> {
                    frame.remove(cardsPanel);
                    try {
                        login.body(p.getUserName(), "buy", "want to buy" + p.allcards.get(finalI).name);
                        p.update(p);
                        Drawer.getInstance().buyCardShow(p.allcards.get(finalI), "", 2);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            } else if (!flag && (searchCard == null || searchCard == p.allcards.get(i)) && (filterCards == 1 || filterCards == 2) && (p.allcards.get(i).mana == mana || mana == 20) && (p.allcards.get(i).specialFor.equals(Hero) || Hero.equals(""))) {
                BufferedImage myPicture = ImageIO.read(new File(p.allcards.get(i).icon));
                JButton cardButton = new JButton();
                cardButton.setIcon(new ImageIcon(myPicture));
                cardButton.setBounds((i % 4) * 150, (i / 4) * 200, 75, 100);
                temp.add(cardButton);
                int finalI = i;
                cardButton.addActionListener(e -> {
                    frame.remove(cardsPanel);
                    try {
                        login.body(p.getUserName(), "sell", "want to sell" + p.allcards.get(finalI).name);
                        p.update(p);
                        Drawer.getInstance().sellCardShow(p.allcards.get(finalI), "", 2);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
        //
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(50, 50, 800, 300);
        cardsPanel.add(scrollableTextArea);
        //
        JPanel temp2 = new JPanel(new GridBagLayout());
        for (int i = 0; i < 11; i++) {
            JButton manaButton = new JButton(String.valueOf(i));
            manaButton.setBounds(0, 0, 10, 20);
            int finalI = i;
            manaButton.addActionListener(e -> {
                frame.remove(cardsPanel);
                try {
                    cards(Hero, finalI, filterCards, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            temp2.add(manaButton);
        }
        JButton manaButton = new JButton("All");
        manaButton.setBounds(0, 0, 10, 20);
        manaButton.addActionListener(e -> {
            frame.remove(cardsPanel);
            try {
                cards(Hero, 20, filterCards, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        temp2.add(manaButton);
        //
        JScrollPane scrollableMana = new JScrollPane(temp2);
        scrollableMana.setBounds(50, 500, 800, 100);
        cardsPanel.add(scrollableMana);
        //
        for (int i = 0; i < p.heros.size(); i++) {
            JButton HeroButton = new JButton(p.heros.get(i).name);
            HeroButton.setBounds(900, i * 30 + 100, 100, 30);
            int finalI = i;
            HeroButton.addActionListener(e -> {
                frame.remove(cardsPanel);
                try {
                    cards(p.heros.get(finalI).name, mana, filterCards, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            cardsPanel.add(HeroButton);
        }
        JButton filterCardsButton1 = new JButton("have or not");
        filterCardsButton1.setBounds(900, 500, 100, 30);
        filterCardsButton1.addActionListener(e -> {
            frame.remove(cardsPanel);
            try {
                cards(Hero, mana, 1, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cardsPanel.add(filterCardsButton1);
        //
        JButton filterCardsButton2 = new JButton("have");
        filterCardsButton2.setBounds(900, 530, 100, 30);
        filterCardsButton2.addActionListener(e -> {
            frame.remove(cardsPanel);
            try {
                cards(Hero, mana, 2, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cardsPanel.add(filterCardsButton2);
        //
        JButton filterCardsButton3 = new JButton("not have");
        filterCardsButton3.setBounds(900, 560, 100, 30);
        filterCardsButton3.addActionListener(e -> {
            frame.remove(cardsPanel);
            try {
                cards(Hero, mana, 3, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cardsPanel.add(filterCardsButton3);
        //
        JTextField searchtext = new JTextField();
        searchtext.setBounds(450, 400, 100, 30);
        cardsPanel.add(searchtext);
        //
        JButton searchButton = new JButton("search");
        searchButton.setBounds(550, 400, 100, 30);
        searchButton.addActionListener(e -> {
            frame.remove(cardsPanel);
            try {
                cards(Hero, mana, 1, textFinder(searchtext.getText(),p.allcards));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cardsPanel.add(searchButton);
        //
        frame.add(cardsPanel);
        frame.repaint();
        frame.revalidate();
    }

    private Card textFinder(String name,ArrayList<Card>cards) {
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

    private void deck(String massage) {
        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(null);
        deckPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel decklabel = new JLabel("this is all deck");
        decklabel.setBounds(500, 0, 400, 30);
        deckPanel.add(decklabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        deckPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(deckPanel);
            try {
                login.body(p.getUserName(), "back buuton", "went to collection");
                collection();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton newdDeckButton = new JButton("new deck");
        newdDeckButton.setBounds(600, 630, 100, 30);
        deckPanel.add(newdDeckButton);
        newdDeckButton.addActionListener(e -> {
            frame.remove(deckPanel);
            try {
                login.body(p.getUserName(), "sing new deck", "went to sign new deck");
                signNewDeck();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JPanel temp = new JPanel(new GridBagLayout());
        temp.setBounds(20, 40, 900, 2000);
        //
        for (int i = 0; i < p.playerDeck.size(); i++) {
            JButton deckButton = new JButton(p.playerDeck.get(i).name);
            deckButton.setBounds((i % 4) * 150, (i / 4) * 200, 150, 200);
            int finalI = i;
            deckButton.addActionListener(e -> {
                frame.remove(deckPanel);
                try {
                    login.body(p.getUserName(), "deck show", "went to deck show room for" + p.playerDeck.get(finalI).name);
                    deckShow(p.playerDeck.get(finalI),null,20,"");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            temp.add(deckButton);
        }
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(50, 50, 800, 200);
        deckPanel.add(scrollableTextArea);
        //
        ButtonGroup buttonGroup = new ButtonGroup();
        ArrayList<JRadioButton> arb = new ArrayList<>();
        for (int i = 0; i < p.playerDeck.size(); i++) {
            JRadioButton radioButton = new JRadioButton(p.playerDeck.get(i).name);
            radioButton.setBounds((i % 5) * 150 + 150, 400, 100, 30);
            buttonGroup.add(radioButton);
            deckPanel.add(radioButton);
            arb.add(radioButton);
        }
        //
        JButton chageDeckButton = new JButton("change current deck");
        chageDeckButton.setBounds(500, 450, 200, 30);
        chageDeckButton.setFont(new Font("serif", Font.BOLD, 14));
        deckPanel.add(chageDeckButton);
        chageDeckButton.addActionListener(e -> {
            int i = 0;
            for (i = 0; i < arb.size(); i++) {
                if (arb.get(i).isSelected())
                    break;
            }
            p.currentDeck = p.playerDeck.get(i);
            try {
                login.body(p.getUserName(), "change current deck", "currnetn deck:" + p.playerDeck.get(i).name);
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JLabel successlabel = new JLabel("you have changed your main deck successfully");
            successlabel.setBounds(500, 500, 300, 30);
            successlabel.setFont(new Font("serif", Font.BOLD, 14));
            deckPanel.add(successlabel);
        });
        //
        frame.add(deckPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void deckShow(Deck deck,Card searchCard,int filterCards,String Hero) throws IOException {
        ArrayList<Card> tempCardArray = purifyCards(deck.deckCards);
        //
        JPanel deckShowPanel = new JPanel();
        deckShowPanel.setLayout(null);
        deckShowPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel deckShowLabel = new JLabel(deck.name + ":" + " and its hero is" + deck.deckHero.name);
        deckShowLabel.setBounds(400, 0, 500, 30);
        deckShowPanel.add(deckShowLabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        deckShowPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(deckShowPanel);
            try {
                login.body(p.getUserName(), "back button", "went to deck room");
                deck("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton removeButton = new JButton("remove this deck");
        removeButton.setBounds(700, 630, 200, 30);
        deckShowPanel.add(removeButton);
        removeButton.addActionListener(e -> {
            frame.remove(deckShowPanel);
            p.playerDeck.remove(deck);
            if (p.currentDeck.name.equals(deck.name))
                p.currentDeck = null;
            try {
                login.body(p.getUserName(), "remove deck", "remove deck:" + deck.name);
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            deck("");
        });
        //
        JPanel temp = new JPanel(new GridBagLayout());
        temp.setBounds(20, 40, 900, 2000);
        //
        for (int i = 0; i < tempCardArray.size(); i++) {
            if((searchCard==null||searchCard.name.equals(tempCardArray.get(i).name))&&(tempCardArray.get(i).mana==filterCards||filterCards==20)&&(Hero.equals("")||Hero.equals(tempCardArray.get(i).specialFor))) {
                BufferedImage myPicture = ImageIO.read(new File(tempCardArray.get(i).icon));
                JButton cardButton = new JButton();
                cardButton.setIcon(new ImageIcon(myPicture));
                cardButton.setBounds((i % 4) * 150, (i / 4) * 200, 150, 200);
                cardButton.setText(numberInArray(tempCardArray.get(i), deck.deckCards) + "x");
                temp.add(cardButton);
                int finalI = i;
                cardButton.addActionListener(e -> {
                    frame.remove(deckShowPanel);
                    try {
                        login.body(p.getUserName(), "card show", "went to card show for" + tempCardArray.get(finalI).name);
                        cardShow(tempCardArray.get(finalI), 1, deck);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(150, 120, 800, 180);
        deckShowPanel.add(scrollableTextArea);
        //
        JLabel removeCardLabel = new JLabel("card to remove");
        removeCardLabel.setBounds(10, 120, 150, 30);
        deckShowPanel.add(removeCardLabel);
        //
        JPanel temp2 = new JPanel(new GridBagLayout());
        temp2.setBounds(20, 40, 900, 2000);
        //
        for (int i = 0; i < p.currentcards.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < deck.deckCards.size(); j++) {
                if (deck.deckCards.get(j).name.equals(p.currentcards.get(i).name)&&numberInArray(deck.deckCards.get(j),deck.deckCards)>=2) {
                    flag = false;
                    break;
                }
            }
            if (flag && (p.currentcards.get(i).specialFor.equals("All") || deck.deckHero.name.equals(p.currentcards.get(i).specialFor))) {
                if((searchCard==null||searchCard.name.equals(p.currentcards.get(i).name))&&(filterCards==20||p.currentcards.get(i).mana==filterCards)&&(Hero.equals("")||Hero.equals(p.currentcards.get(i).specialFor))) {
                    BufferedImage myPicture = ImageIO.read(new File(p.currentcards.get(i).icon));
                    JButton cardButton = new JButton((2 - numberInArray(p.currentcards.get(i), deck.deckCards)) + "x");
                    cardButton.setIcon(new ImageIcon(myPicture));
                    cardButton.setBounds((i % 4) * 150, (i / 4) * 200, 150, 200);
                    temp2.add(cardButton);
                    int finalI = i;
                    cardButton.addActionListener(e -> {
                        frame.remove(deckShowPanel);
                        try {
                            login.body(p.getUserName(), "card show", "went to card show room for:" + p.currentcards.get(finalI).name);
                            cardShow(p.currentcards.get(finalI), 2, deck);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            }
        }
        //
        JScrollPane scrollableTextArea2 = new JScrollPane(temp2);
        scrollableTextArea2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea2.setBounds(150, 310, 800, 180);
        deckShowPanel.add(scrollableTextArea2);
        //
        JLabel addCardLabel = new JLabel("card to add");
        addCardLabel.setBounds(10, 310, 150, 30);
        deckShowPanel.add(addCardLabel);
        //
        JLabel HeroLabel = new JLabel();
        BufferedImage HeroPicture = ImageIO.read(new File(deck.deckHero.icon));
        HeroLabel.setIcon(new ImageIcon(HeroPicture));
        HeroLabel.setBounds(20,0 , 75, 110);
        deckShowPanel.add(HeroLabel);
        //
        JTextField searchText = new JTextField();
        searchText.setBounds(50, 600, 100, 30);
        deckShowPanel.add(searchText);
        //
        JButton searchButton = new JButton("search");
        searchButton.setBounds(150, 600, 100, 30);
        searchButton.addActionListener(e -> {
            frame.remove(deckShowPanel);
            ArrayList<Card>tempCards=new ArrayList<>();
            try {
                deckShow(deck,textFinder(searchText.getText(),p.allcards),20,"");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        deckShowPanel.add(searchButton);
        //
        JPanel temp3 = new JPanel(new GridBagLayout());
        for (int i = 0; i < 11; i++) {
            JButton manaButton = new JButton(String.valueOf(i));
            manaButton.setBounds(0, 0, 10, 20);
            int finalI = i;
            manaButton.addActionListener(e -> {
                frame.remove(deckShowPanel);
                try {
                    deckShow(deck,null,finalI,"");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            temp3.add(manaButton);
        }
        JButton manaButton = new JButton("All");
        manaButton.setBounds(0, 0, 10, 20);
        manaButton.addActionListener(e -> {
            frame.remove(deckShowPanel);
            try {
                deckShow(deck,null,20,"");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        temp3.add(manaButton);
        JScrollPane scrollableMana = new JScrollPane(temp3);
        scrollableMana.setBounds(50, 500, 800, 50);
        deckShowPanel.add(scrollableMana);
        //
        JButton HeroButton=new JButton(deck.deckHero.name);
        HeroButton.setBounds(1000,50,100,30);
        deckShowPanel.add(HeroButton);
        HeroButton.addActionListener(e->{
            frame.remove(deckShowPanel);
            try {
                deckShow(deck,null,20,deck.deckHero.name);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton NaturalButton=new JButton("Natural");
        NaturalButton.setBounds(1000,100,100,30);
        deckShowPanel.add(NaturalButton);
        NaturalButton.addActionListener(e->{
            frame.remove(deckShowPanel);
            try {
                deckShow(deck,null,20,"All");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton AllButton=new JButton("All");
        AllButton.setBounds(1000,150,100,30);
        deckShowPanel.add(AllButton);
        AllButton.addActionListener(e->{
            frame.remove(deckShowPanel);
            try {
                deckShow(deck,null,20,"");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(deckShowPanel);
        frame.repaint();
        frame.revalidate();
    }

    //return cards that dont have same cards
    private ArrayList<Card> purifyCards(ArrayList<Card> cards) {
        ArrayList<Card> tempCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < tempCards.size(); j++) {
                if (tempCards.get(j).name.equals(cards.get(i).name)) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                tempCards.add(cards.get(i));
        }
        return tempCards;
    }

    //return number of one card in array
    private int numberInArray(Card card, ArrayList<Card> cards) {
        int counter = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (card.name.equals(cards.get(i).name))
                counter++;
        }
        return counter;
    }

    private void signNewDeck() {
        JPanel SignNewDeckPanel = new JPanel();
        SignNewDeckPanel.setLayout(null);
        SignNewDeckPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel createlabel = new JLabel("create deck");
        createlabel.setBounds(500, 0, 400, 30);
        SignNewDeckPanel.add(createlabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        SignNewDeckPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(SignNewDeckPanel);
            try {
                login.body(p.getUserName(), "back button", "went to deck room");
                deck("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        ButtonGroup buttonGroup = new ButtonGroup();
        ArrayList<JRadioButton> arb = new ArrayList<>();
        for (int i = 0; i < p.heros.size(); i++) {
            JRadioButton radioButton = new JRadioButton(p.heros.get(i).name);
            radioButton.setBounds((i % 5) * 150 + 150, 450, 100, 30);
            buttonGroup.add(radioButton);
            SignNewDeckPanel.add(radioButton);
            arb.add(radioButton);
        }
        //
        JLabel decknamelabel = new JLabel("deck name:");
        decknamelabel.setBounds(450, 350, 100, 30);
        SignNewDeckPanel.add(decknamelabel);
        //
        JTextField usernametext = new JTextField();
        usernametext.setBounds(550, 350, 100, 30);
        SignNewDeckPanel.add(usernametext);
        //
        JButton createButton = new JButton("create");
        createButton.setBounds(600, 630, 100, 30);
        SignNewDeckPanel.add(createButton);
        createButton.addActionListener(e -> {
            frame.remove(SignNewDeckPanel);
            int i = 0;
            for (i = 0; i < arb.size(); i++) {
                if (arb.get(i).isSelected()) {
                    break;
                }
            }
            Deck deck = new Deck(usernametext.getText(), p.heros.get(i));
            try {
                login.body(p.getUserName(), "create new deck", "have created " + deck.name + "to deck collection");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            p.playerDeck.add(deck);
            try {
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                deckShow(p.playerDeck.get(p.playerDeck.size() - 1),null,20,"");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(SignNewDeckPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void cardShow(Card card, int n, Deck deck) throws IOException {
        JPanel cardShowPanel = new JPanel();
        cardShowPanel.setLayout(null);
        cardShowPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel cardShowlabel = new JLabel(card.name + ":");
        cardShowlabel.setBounds(500, 0, 400, 30);
        cardShowPanel.add(cardShowlabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        cardShowPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(cardShowPanel);
            if (n == 1) {
                try {
                    login.body(p.getUserName(), "back button", "went to deck show");
                    deckShow(deck,null,20,"");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (n == 2) {
                try {
                    login.body(p.getUserName(), "back button", "went to add room");
                    deckShow(deck,null,20,"");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //
        JLabel information1 = new JLabel("mana: " + card.mana + " damage: " + card.damage + " health: " + card.health);
        information1.setBounds(500, 200, 500, 100);
        information1.setFont(new Font("serif", Font.BOLD, 15));
        cardShowPanel.add(information1);
        //
        JLabel information2 = new JLabel("description: " + card.description);
        information2.setBounds(500, 300, 600, 100);
        information2.setFont(new Font("serif", Font.BOLD, 15));
        cardShowPanel.add(information2);
        //
        JLabel information3 = new JLabel("special for: " + card.specialFor + " rarity: " + card.rarity);
        information3.setBounds(500, 400, 600, 100);
        information3.setFont(new Font("serif", Font.BOLD, 15));
        cardShowPanel.add(information3);
        //
        BufferedImage myPicture = ImageIO.read(new File(card.icon));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(20, 20, 150, 200);
        cardShowPanel.add(picLabel);
        //
        if (n == 1) {
            JButton removeButton = new JButton("remove");
            removeButton.setBounds(600, 630, 100, 30);
            cardShowPanel.add(removeButton);
            removeButton.addActionListener(e -> {
                try {
                    login.body(p.getUserName(), card.name, "remove the card from " + deck.name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.remove(cardShowPanel);
                deck.deckCards.remove(card);
                try {
                    p.update(p);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    deckShow(deck,null,20,"");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (n == 2) {
            JButton addButton = new JButton("add");
            addButton.setBounds(600, 630, 100, 30);
            cardShowPanel.add(addButton);
            addButton.addActionListener(e -> {
                try {
                    login.body(p.getUserName(), card.name, "add this card to" + deck.name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.remove(cardShowPanel);
                deck.deckCards.add(card);
                try {
                    p.update(p);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    deckShow(deck,null,20,"");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        frame.add(cardShowPanel);
        frame.repaint();
        frame.revalidate();
    }
}
