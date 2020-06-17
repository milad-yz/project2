package org.drawer.sign;

import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;

import javax.swing.*;
import java.io.IOException;

public class DeletePlayer {
    private JFrame frame;
    private Player p;

    public DeletePlayer(JFrame frame, Player p) {
        this.frame = frame;
        this.p = p;
        draw();
    }

    private void draw() {
        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(null);
        deletePanel.setBounds(0, 0, 1200, 800);
        //
        JLabel deleteLabel = new JLabel("please describe why you want to delete your account");
        deleteLabel.setBounds(500, 0, 300, 30);
        deletePanel.add(deleteLabel);
        //
        JTextField complaintText = new JTextField();
        complaintText.setBounds(450, 150, 400, 200);
        deletePanel.add(complaintText);
        //
        JLabel questionLabel = new JLabel("Are you sure?");
        questionLabel.setBounds(500, 500, 300, 30);
        deletePanel.add(questionLabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 600, 100, 30);
        deletePanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(deletePanel);
            try {
                login.body(p.getUserName(), "back button", "went to menu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Drawer.getInstance().Enter();
        });
        //
        JButton yesButton = new JButton("yes");
        yesButton.setBounds(600, 600, 100, 30);
        deletePanel.add(yesButton);
        yesButton.addActionListener(e -> {
            frame.remove(deletePanel);
            try {
                p.deletePlayer(p);
                login.body(p.getUserName(), "delete player", complaintText.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        });
        //
        frame.add(deletePanel);
        frame.repaint();
        frame.revalidate();
    }
}
