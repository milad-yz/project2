package org.drawer.ShopRooms;

import org.drawer.mainParts.collections.Collection;
import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;
import org.stuff.cards.Minion;
import org.stuff.cards.QuestAndReward;
import org.stuff.cards.Spell;
import org.stuff.cards.Weapon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BuyCardShow {
    private JFrame frame;
    private Player p;
    private int n;
    private Card card;

    public BuyCardShow(JFrame frame, Player p, int n, Card card) {
        this.frame = frame;
        this.p = p;
        this.n = n;
        this.card = card;
        try {
            draw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw() throws IOException {
        JPanel buyCardShowPanel = new JPanel();
        buyCardShowPanel.setLayout(null);
        buyCardShowPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel buyLabel = new JLabel("your diamond is = " + p.diamonds);
        buyLabel.setBounds(500, 0, 400, 30);
        buyCardShowPanel.add(buyLabel);
        //
        JLabel information1=new JLabel();
        information1.setBounds(500, 200, 500, 100);
        information1.setFont(new Font("serif", Font.BOLD, 15));
        if(card.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")) {
            Minion tempCard=(Minion)card;
            information1.setText("mana: " + card.mana + " damage: " + tempCard.getDamage() + " health: " + tempCard.getHealth());
        }else if(card.getClass().getSuperclass().getName().equals("org.stuff.cards.Spell")){
            Spell tempCard=(Spell)card;
            information1.setText("mana: " + card.mana);
        }else if(card.getClass().getSuperclass().getName().equals("org.stuff.cards.QuestAndReward")){
            QuestAndReward tempCard=(QuestAndReward) card;
            information1.setText("mana: " + card.mana);
        }else{
            Weapon tempCard=(Weapon) card;
            information1.setText("mana: " + card.mana + " damage: " + tempCard.getDamage() + " defence: " + tempCard.getDefence());
        }
        buyCardShowPanel.add(information1);
        //
        JLabel information2 = new JLabel("description: " + card.description);
        information2.setBounds(500, 300, 600, 100);
        information2.setFont(new Font("serif", Font.BOLD, 15));
        buyCardShowPanel.add(information2);
        //
        JLabel information3 = new JLabel("special for: " + card.specialFor + " rarity: " + card.rarity);
        information3.setBounds(500, 400, 600, 100);
        information3.setFont(new Font("serif", Font.BOLD, 15));
        buyCardShowPanel.add(information3);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        buyCardShowPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(buyCardShowPanel);
            if (n == 1) {
                try {
                    login.body(p.getUserName(), "back button", "went to buy room");
                    Drawer.getInstance().buy("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (n == 2) {
                try {
                    login.body(p.getUserName(), "back button", "went to card room");
                    Collection c1 = new Collection(frame,p);
                    p.update();
                    c1.cards("", 20, 1, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //
        BufferedImage myPicture = ImageIO.read(new File(card.icon));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(20, 20, 150, 200);
        buyCardShowPanel.add(picLabel);
        //
        JButton buyButton = new JButton("buy: " + card.name + " for:" + card.cost);
        buyButton.setBounds(650, 630, 200, 30);
        buyCardShowPanel.add(buyButton);
        buyButton.addActionListener(e -> {
            frame.remove(buyCardShowPanel);
            if (p.diamonds >= card.cost) {
                p.diamonds -= card.cost;
                p.currentCards.add(card);
                try {
                    login.body(p.getUserName(), card.name, "have bought this card");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(frame, "you have bought this card successfully", "buy", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "sorry you dont have enough diamonds", "buy", JOptionPane.INFORMATION_MESSAGE);
            }
            if (n == 1) {
                Drawer.getInstance().buy("");
            } else if (n == 2) {
                try {
                    Collection c1 = new Collection(frame,p);
                    c1.cards("", 20, 1, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                p.update();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(buyCardShowPanel);
        frame.repaint();
        frame.revalidate();
    }
}
