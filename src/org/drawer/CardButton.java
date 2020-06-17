package org.drawer;

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
    public CardButton(Card card){
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(card.icon));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIcon(new ImageIcon(myPicture));
        setLayout(new GridBagLayout());
        if(card.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")){
            Minion tempCard=(Minion)card;
            setText(" " + card.mana + " " + tempCard.getDamage() + " " + tempCard.getHealth());
        }else if(card.getClass().getSuperclass().getName().equals("org.stuff.cards.Spell")){
            Spell tempCard=(Spell) card;
            setText(" " + card.mana  );
        }else if(card.getClass().getSuperclass().getName().equals("org.stuff.cards.QuestAndReward")){
            QuestAndReward tempCard=(QuestAndReward) card;
            setText(" " + card.mana );
        }else{
            Weapon tempCard=(Weapon) card;
            setText(" " + card.mana + " " + tempCard.getDamage() + " " + tempCard.getDefence());
        }
    }
}
