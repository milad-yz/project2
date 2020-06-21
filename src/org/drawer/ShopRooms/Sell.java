package org.drawer.ShopRooms;

import org.drawer.labelsAndButtons.CardButton;
import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Sell {
    private JFrame frame;
    private Player p;
    private String massage;

    public Sell(JFrame frame, Player p, String massage) {
        this.frame = frame;
        this.p = p;
        this.massage = massage;
        try {
            draw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw() throws IOException {
        JPanel sellPanel = new JPanel();
        sellPanel.setLayout(null);
        sellPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel massagelabel = new JLabel(massage);
        massagelabel.setBounds(500, 600, 400, 30);
        massagelabel.setForeground(Color.red);
        sellPanel.add(massagelabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        sellPanel.add(backButton);
        backButton.addActionListener(e -> {
            try {
                login.body(p.getUserName(), "back button", "went to shop");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.remove(sellPanel);
            Drawer.getInstance().shop();
        });
        //
        JLabel sellLabel = new JLabel("your diamond is = " + p.diamonds + " and cards that you can sell:");
        sellLabel.setBounds(500, 0, 400, 30);
        sellPanel.add(sellLabel);
        //
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        //
        int j = 0;
        for (int i = 0; i < p.currentCards.size(); i++) {
            if (p.currentCards.get(i).specialFor.equals("All")) {
                CardButton sellButton = new CardButton(p.currentCards.get(i));
                temp.add(sellButton);
                int finalI = i;
                sellButton.addActionListener(e -> {
                    frame.remove(sellPanel);
                    try {
                        login.body(p.getUserName(), "card show", "want to sell" + p.currentCards.get(finalI).name);
                        Drawer.getInstance().sellCardShow(p.currentCards.get(finalI), "", 1);
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
        sellPanel.add(scrollableTextArea);
        //
        frame.add(sellPanel);
        frame.repaint();
        frame.revalidate();
    }
}
