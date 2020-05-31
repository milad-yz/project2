package org.drawer.rooms;

import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Buy {
    private JFrame frame;
    private Player p;
    private String massage;

    public Buy(JFrame frame, Player p,String massage) {
        this.frame = frame;
        this.p = p;
        this.massage=massage;
        try {
            draw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw() throws IOException {
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(null);
        buyPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel massagelabel = new JLabel(massage);
        massagelabel.setBounds(500, 600, 400, 30);
        massagelabel.setForeground(Color.red);
        buyPanel.add(massagelabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        buyPanel.add(backButton);
        backButton.addActionListener(e -> {
            try {
                login.body(p.getUserName(), "back button", "went to shop");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.remove(buyPanel);
            Drawer.getInstance().shop();
        });
        //
        JLabel buylabel = new JLabel("your diamond is = " + p.diamonds + " and cards that you can buy:");
        buylabel.setBounds(500, 0, 400, 30);
        buyPanel.add(buylabel);
        //
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        //
        int j = 0;
        for (int i = 0; i < p.allcards.size(); i++) {
            boolean flag = true;
            for (int k = 0; k < p.currentcards.size(); k++) {
                if (p.allcards.get(i).name.equals(p.currentcards.get(k).name))
                    flag = false;
            }
            if (flag) {
                BufferedImage myPicture = ImageIO.read(new File(p.allcards.get(i).icon));
                JButton buyButton = new JButton();
                buyButton.setBounds((j % 6) * 150 + 50, (j / 6) * 200 + 100, 80, 100);
                buyButton.setIcon(new ImageIcon(myPicture));
                temp.add(buyButton);
                int finalI = i;
                buyButton.addActionListener(e -> {
                    frame.remove(buyPanel);
                    try {
                        login.body(p.getUserName(), "card show", "want to buy" + p.allcards.get(finalI).name);
                        Drawer.getInstance().buyCardShow(p.allcards.get(finalI), 1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                j++;
            }
        }
        //
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(50, 100, 850, 400);
        buyPanel.add(scrollableTextArea);
        //
        frame.add(buyPanel);
        frame.repaint();
        frame.revalidate();
    }
}
