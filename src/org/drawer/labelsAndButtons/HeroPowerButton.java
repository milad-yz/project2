package org.drawer.labelsAndButtons;

import org.stuff.Hero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeroPowerButton extends JButton {
    public HeroPowerButton(Hero hero){
        setBounds(0,0,30,40);
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(hero.HeroPowerIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g = myPicture.getGraphics();
        g.setFont(new Font("Impact", Font.BOLD, 12));
        g.setColor(Color.RED);
        g.drawString(hero.mana+ "", 15, 27);
        setIcon(new ImageIcon(myPicture));
    }
}
