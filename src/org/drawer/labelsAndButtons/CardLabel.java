package org.drawer.labelsAndButtons;

import org.stuff.Card;
import org.stuff.cards.Minion;
import org.stuff.cards.Weapon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CardLabel extends JLabel {
    public CardLabel(Card card) {
        setBounds(0, 0, 75, 100);
        if (card != null) {
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File(card.icon));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Graphics g = myPicture.getGraphics();
            g.setFont(new Font("Impact", Font.BOLD, 12));
            g.setColor(Color.BLUE);
            g.drawString(card.mana + "", 20, 100);
            setIcon(new ImageIcon(myPicture));
            setLayout(new GridBagLayout());
            if (card.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")) {
                Minion tempCard = (Minion) card;
                g.setColor(Color.YELLOW);
                g.drawString(tempCard.getDamage() + "", 40, 100);
                g.setColor(Color.RED);
                g.drawString(tempCard.getHealth() + "", 60, 100);
            } else if (card.getClass().getSuperclass().getName().equals("org.stuff.cards.Spell")) {
            } else if (card.getClass().getSuperclass().getName().equals("org.stuff.cards.QuestAndReward")) {
            } else {
                Weapon tempCard = (Weapon) card;
                g.setColor(Color.YELLOW);
                g.drawString(tempCard.getDamage() + "", 40, 100);
                g.setColor(Color.RED);
                g.drawString(tempCard.getDefence() + "", 60, 100);
            }
        }else{

        }
    }
}
