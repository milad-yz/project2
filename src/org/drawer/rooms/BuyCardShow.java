package org.drawer.rooms;

import org.drawer.mainParts.collections.Collection;
import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;

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
        JLabel information1 = new JLabel("mana: " + card.mana + " damage: " + card.damage + " health: " + card.health);
        information1.setBounds(500, 200, 500, 100);
        information1.setFont(new Font("serif", Font.BOLD, 15));
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
                    p.update(p);
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
                p.currentcards.add(card);
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
                p.update(p);
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
