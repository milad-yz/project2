package org.drawer.mainParts;

import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Status {
    private JFrame frame;
    private Player p;

    public Status(JFrame frame, Player p) {
        this.frame = frame;
        this.p = p;
        draw();
    }

    private void draw() {
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(null);
        statusPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel statuslabel = new JLabel("this is your status:");
        statuslabel.setBounds(500, 0, 400, 30);
        statusPanel.add(statuslabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        statusPanel.add(backButton);
        backButton.addActionListener(e -> {
            try {
                login.body(p.getUserName(), "back buton", "went to menu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.remove(statusPanel);
            Drawer.getInstance().Enter();
        });
        //
        JPanel temp = new JPanel();
        temp.setLayout(null);
        temp.setBounds(0, 0, 1200, 2000);
        for (int i = 0; i < p.playerDeck.size() && i < 10; i++) {
            float c = 0;
            if (p.playerDeck.get(i).allGame == 0)
                c = 0;
            else
                c = p.playerDeck.get(i).winGame / p.playerDeck.get(i).allGame;
            JTextPane deckText = new JTextPane();
            deckText.setText("name: " + p.playerDeck.get(i).name + "\nwin/game: " + c
                    + "\nwin: " + p.playerDeck.get(i).winGame + "\ngame: " + p.playerDeck.get(i).allGame
                    + "\naverage mana: " + p.playerDeck.get(i).getAverageMana() + "\nHero: " + p.playerDeck.get(i).deckHero.name
                    + "\nbest card: " + p.playerDeck.get(i).getBestCard().name);
            deckText.setBackground(Color.CYAN);
            deckText.setForeground(Color.RED);
            deckText.setBounds((i % 5) * 220 + 10, (i / 5) * 220 + 30, 210, 210);
            temp.add(deckText);
        }
        //
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(0, 0, 1180, 700);
        statusPanel.add(scrollableTextArea);
        //
        frame.add(statusPanel);
        frame.repaint();
        frame.revalidate();
    }
}
