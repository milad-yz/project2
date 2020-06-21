package org.drawer.mainParts.collections;

import org.drawer.labelsAndButtons.CardButton;
import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

public class CardsShowRoom {
    private static CardsShowRoom single_instance = null;
    private JFrame frame;
    private Player p;
    private Collection collection;
    private CardsShowRoom(JFrame frame, Player p,Collection collection) {
        this.frame=frame;
        this.p=p;
        this.collection=collection;
    }

    public static CardsShowRoom getInstance(JFrame frame, Player p,Collection collection) {
        if (single_instance == null)
            single_instance = new CardsShowRoom(frame,p,collection);
        return single_instance;
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
                login.body(p.getUserName(), "back button", "went to collection");
                collection.collection();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JPanel temp = new JPanel(new GridBagLayout());
        for (int i = 0; i < p.allCards.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < p.currentCards.size(); j++) {
                if (p.currentCards.get(j).name.equals(p.allCards.get(i).name)) {
                    flag = false;
                    break;
                }
            }
            if (flag && (searchCard == null || searchCard == p.allCards.get(i)) && (filterCards == 1 || filterCards == 3) && (p.allCards.get(i).mana == mana || mana == 20) && (p.allCards.get(i).specialFor.equals(Hero) || Hero.equals(""))) {
                BufferedImage myPicture = ImageIO.read(new File(p.allCards.get(i).icon));
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
                        login.body(p.getUserName(), "buy", "want to buy" + p.allCards.get(finalI).name);
                        p.update();
                        Drawer.getInstance().buyCardShow(p.allCards.get(finalI), 2);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            } else if (!flag && (searchCard == null || searchCard == p.allCards.get(i)) && (filterCards == 1 || filterCards == 2) && (p.allCards.get(i).mana == mana || mana == 20) && (p.allCards.get(i).specialFor.equals(Hero) || Hero.equals(""))) {
                CardButton cardButton = new CardButton(p.allCards.get(i));
                temp.add(cardButton);
                int finalI = i;
                cardButton.addActionListener(e -> {
                    frame.remove(cardsPanel);
                    try {
                        login.body(p.getUserName(), "sell", "want to sell" + p.allCards.get(finalI).name);
                        p.update();
                        Drawer.getInstance().sellCardShow(p.allCards.get(finalI), "", 2);
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
        JButton allHeroButton = new JButton("All");
        allHeroButton.setBounds(900, 70, 100, 30);
        allHeroButton.addActionListener(e -> {
            frame.remove(cardsPanel);
            try {
                cards("", mana, filterCards, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cardsPanel.add(allHeroButton);
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
        JTextField searchText = new JTextField();
        searchText.setBounds(450, 400, 100, 30);
        cardsPanel.add(searchText);
        //
        JButton searchButton = new JButton("search");
        searchButton.setBounds(550, 400, 100, 30);
        searchButton.addActionListener(e -> {
            frame.remove(cardsPanel);
            try {
                cards(Hero, mana, 1, collection.textFinder(searchText.getText(),p.allCards));
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
}
