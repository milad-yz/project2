package org.drawer.mainParts;

import org.drawer.Drawer;
import org.drawer.sign.FirstQuestion;
import org.fileWorks.login;
import org.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Enter {
    private JFrame frame;
    private Player p;

    public Enter(JFrame frame, Player p) {
        this.frame = frame;
        this.p = p;
        draw();
    }

    private void draw() {
        JPanel EnterPanel = new JPanel();
        EnterPanel.setLayout(null);
        EnterPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel EnterRoom = new JLabel("Menu:");
        EnterRoom.setBounds(500, 0, 100, 30);
        EnterPanel.add(EnterRoom);
        //
        JButton PlayButton = new JButton("Play");
        PlayButton.setBounds(500, 80, 100, 30);
        EnterPanel.add(PlayButton);
        PlayButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "play", "wants to play");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                Drawer.getInstance().play();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton ShopButton = new JButton("Shop");
        ShopButton.setBounds(500, 160, 100, 30);
        EnterPanel.add(ShopButton);
        ShopButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "shop", "went to the shop");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Drawer.getInstance().shop();
        });
        //
        JButton StatusButton = new JButton("Status");
        StatusButton.setBounds(500, 240, 100, 30);
        EnterPanel.add(StatusButton);
        StatusButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "Status", "went to status");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Drawer.getInstance(p).status();
        });
        //
        JButton CollectionButton = new JButton("Collection");
        CollectionButton.setBounds(500, 320, 100, 30);
        EnterPanel.add(CollectionButton);
        CollectionButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "Collection", "went to Collection");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                Drawer.getInstance().collection();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        //
        JButton SettingButton = new JButton("Setting");
        SettingButton.setBounds(500, 400, 100, 30);
        EnterPanel.add(SettingButton);
        SettingButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "Setting", "went to Setting");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Drawer.getInstance().setting();
        });
        //
        JButton signOutButton = new JButton("Sing out");
        signOutButton.setBounds(500, 480, 100, 30);
        EnterPanel.add(signOutButton);
        signOutButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "exit", "exit");
                p.update();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.setVisible(false);
            FirstQuestion.getInstance().draw();
        });
        //
        JButton deleteButton = new JButton("delete account");
        deleteButton.setBounds(500, 560, 100, 30);
        deleteButton.setFont(new Font("Verdana", Font.PLAIN, 8));
        EnterPanel.add(deleteButton);
        deleteButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "want to delete", "");
                p.update();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Drawer.getInstance().deletePlayer();
        });
        //
        frame.add(EnterPanel);
        frame.repaint();
        frame.revalidate();
    }
}
