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

public class SellCardShow {
    private JFrame frame;
    private Player p;
    private Card card;
    private String massage;
    private int n;

    public SellCardShow(JFrame frame, Player p, Card card, String massage, int n) {
        this.frame = frame;
        this.p = p;
        this.card = card;
        this.massage = massage;
        this.n = n;
        try {
            draw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw() throws IOException {
        JPanel sellCardShowPanel = new JPanel();
        sellCardShowPanel.setLayout(null);
        sellCardShowPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel sellLabel = new JLabel("your diamond is = " + p.diamonds);
        sellLabel.setBounds(500, 0, 400, 30);
        sellCardShowPanel.add(sellLabel);
        //
        JLabel massagelabel = new JLabel(massage);
        massagelabel.setBounds(500, 600, 400, 30);
        massagelabel.setForeground(Color.red);
        sellCardShowPanel.add(massagelabel);
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
        sellCardShowPanel.add(information1);
        //
        JLabel information2 = new JLabel("description: " + card.description);
        information2.setBounds(500, 300, 600, 100);
        information2.setFont(new Font("serif", Font.BOLD, 15));
        sellCardShowPanel.add(information2);
        //
        JLabel information3 = new JLabel("special for: " + card.specialFor + " rarity: " + card.rarity);
        information3.setBounds(500, 400, 600, 100);
        information3.setFont(new Font("serif", Font.BOLD, 15));
        sellCardShowPanel.add(information3);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        sellCardShowPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(sellCardShowPanel);
            if (n == 1) {
                try {
                    login.body(p.getUserName(), "back button", "went to sell room");
                    Drawer.getInstance().sell("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (n == 2) {
                try {
                    login.body(p.getUserName(), "back button", "went to card room");
                    p.update();
                    Collection c1 = new Collection(frame,p);
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
        sellCardShowPanel.add(picLabel);
        //
        JButton sellButton = new JButton("sell: " + card.name + " for:" + card.cost / 2);
        sellButton.setBounds(650, 630, 200, 30);
        sellCardShowPanel.add(sellButton);
        sellButton.addActionListener(e -> {
            frame.remove(sellCardShowPanel);
            p.diamonds += card.cost / 2;
            forceSell(card);
            p.currentCards.remove(card);
            p.sellUpdate(card);
            JOptionPane.showMessageDialog(frame, "you have sold it successfully", "sell", JOptionPane.INFORMATION_MESSAGE);
            if (n == 1) {
                Drawer.getInstance().sell("");
            } else if (n == 2) {
                try {
                    Collection c1 = new Collection(frame,p);
                    c1.cards("", 20, 1, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                login.body(p.getUserName(), card.name, "have sold this card");
                p.update();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(sellCardShowPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void forceSell(Card card) {
        for (int i = 0; i < p.currentCards.size(); i++) {
            if(card.name.equals(p.currentCards.get(i).name)){
                System.out.println(p.currentCards.get(i).name);
                p.currentCards.remove(i);
                break;
            }
        }
    }
}
