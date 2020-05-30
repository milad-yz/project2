package org.drawer.mainParts;

import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;

import javax.swing.*;
import java.io.IOException;

public class Setting {
    private JFrame frame;
    private Player p;

    public Setting(JFrame frame, Player p) {
        this.frame = frame;
        this.p = p;
        draw();
    }

    private void draw() {
        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(null);
        settingPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel settingLabel = new JLabel("setting");
        settingLabel.setBounds(500, 0, 400, 30);
        settingPanel.add(settingLabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        settingPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(settingPanel);
            try {
                login.body(p.getUserName(), "back button", "went to enter room");
                Drawer.getInstance().Enter();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(settingPanel);
        frame.repaint();
        frame.revalidate();
    }
}
