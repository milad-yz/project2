package org.drawer.labelsAndButtons;

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

public class CardButton extends JButton {
    public CardButton(Card card) {
        setBounds(0, 0, 75, 100);
        if (card != null) {
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File(card.icon));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert myPicture != null;
            Graphics g = myPicture.getGraphics();
            g.setFont(new Font("Impact", Font.BOLD, 12));
            if (card.mana == card.finalMana)
                g.setColor(Color.black);
            else
                g.setColor(Color.GRAY);
            g.drawString(card.mana + "", 8, 17);
            setIcon(new ImageIcon(myPicture));
            setLayout(new GridBagLayout());
            if (card.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")) {
                //
                Minion tempCard = (Minion) card;
                if (tempCard.haveShield)
                    setBackground(Color.YELLOW);
                if (tempCard.haveTaunt)
                    setBackground(Color.BLACK);
                if (tempCard.haveTaunt && tempCard.haveShield)
                    setBackground(Color.RED);
                //
                if(tempCard.finalDamage==tempCard.getDamage())
                    g.setColor(Color.black);
                else
                    g.setColor(Color.BLUE);
                g.drawString(tempCard.getDamage() + "", 8, 95);
                if(tempCard.finalHealth==tempCard.getHealth())
                    g.setColor(Color.black);
                else
                    g.setColor(Color.white);
                g.drawString(tempCard.getHealth() + "", 60, 95);
            } else if (card.getClass().getSuperclass().getName().equals("org.stuff.cards.Spell")) {
            } else if (card.getClass().getSuperclass().getName().equals("org.stuff.cards.QuestAndReward")) {
            } else {
            }
        } else {
            setBackground(Color.CYAN);
        }
    }
}
