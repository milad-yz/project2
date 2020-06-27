package org.drawer.labelsAndButtons;

import org.stuff.Hero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeroButton extends JButton {
    private Hero hero;
    public HeroButton(Hero hero){
        this.hero=hero;
        setBounds(0,0,75,100);
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(hero.icon));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g = myPicture.getGraphics();
        g.setFont(new Font("Impact", Font.BOLD, 12));
        g.setColor(Color.black);
        g.drawString(hero.health + "", 58, 70);
        setIcon(new ImageIcon(myPicture));
        if( hero.durability>0) {
            g.drawString(hero.durability + "", 35, 70);
            g.drawString(hero.damage + "", 7, 70);
        }
    }
}
