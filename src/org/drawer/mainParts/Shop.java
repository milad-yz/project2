package org.drawer.mainParts;

import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;

import javax.swing.*;
import java.io.IOException;

public class Shop {
    private JFrame frame;
    private Player p;

    public Shop(JFrame frame, Player p) {
        this.frame = frame;
        this.p = p;
        draw();
    }

    private void draw() {
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(null);
        shopPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel shopLabel = new JLabel("what do you want?");
        shopLabel.setBounds(500, 0, 200, 30);
        shopPanel.add(shopLabel);
        //
        JButton buyButton = new JButton("buy");
        buyButton.setBounds(500, 100, 100, 30);
        shopPanel.add(buyButton);
        buyButton.addActionListener(e -> {
            frame.remove(shopPanel);
            try {
                login.body(p.getUserName(), "buy room", "went to buy room");
                Drawer.getInstance().buy("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton sellButton = new JButton("sell");
        sellButton.setBounds(500, 200, 100, 30);
        shopPanel.add(sellButton);
        sellButton.addActionListener(e -> {
            frame.remove(shopPanel);
            try {
                login.body(p.getUserName(), "sell room", "went to sell room");
                Drawer.getInstance().sell("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 300, 100, 30);
        shopPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(shopPanel);
            try {
                login.body(p.getUserName(), "back button", "went to menu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Drawer.getInstance().Enter();
        });
        //
        frame.add(shopPanel);
        frame.repaint();
        frame.revalidate();
    }
}
