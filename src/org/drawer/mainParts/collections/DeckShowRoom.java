package org.drawer.mainParts.collections;

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

public class DeckShowRoom {
    private static DeckShowRoom single_instance = null;
    private JFrame frame;
    private Player p;
    private Collection collection;
    private DeckShowRoom(JFrame frame, Player p, Collection collection) {
        this.frame = frame;
        this.p = p;
        this.collection = collection;
    }
    public static DeckShowRoom getInstance(JFrame frame, Player p,Collection collection) {
        if (single_instance == null)
            single_instance = new DeckShowRoom(frame,p,collection);
        return single_instance;
    }
    public void deck(){
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
                collection.collection();
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
    private void deckShow(Deck deck, Card searchCard, int filterCards, String Hero) throws IOException {
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
                deck();
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
            deck();
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
                deckShow(deck,collection.textFinder(searchText.getText(),p.allcards),20,"");
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
                deck();
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