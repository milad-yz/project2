package org.drawer.sign;

import com.google.gson.Gson;
import org.drawer.Drawer;
import org.fileWorks.login;
import org.player.Player;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SignIn {
    private JFrame frame;
    private Player p;

    public SignIn(JFrame frame) {
        this.frame = frame;
        draw();
    }

    private void draw() {
        JPanel SingInPanel = new JPanel();
        SingInPanel.setLayout(null);
        SingInPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel SingInRoom = new JLabel("Sing In Room:");
        SingInRoom.setBounds(500, 0, 100, 30);
        SingInPanel.add(SingInRoom);
        //
        JLabel userlabel = new JLabel("Username:");
        userlabel.setBounds(450, 50, 100, 30);
        SingInPanel.add(userlabel);
        //
        JLabel passlabel = new JLabel("Password:");
        passlabel.setBounds(450, 150, 100, 30);
        SingInPanel.add(passlabel);
        //
        JTextField usernametext = new JTextField();
        usernametext.setBounds(550, 50, 100, 30);
        SingInPanel.add(usernametext);
        //
        JPasswordField passwordtext = new JPasswordField();
        passwordtext.setBounds(550, 150, 100, 30);
        SingInPanel.add(passwordtext);
        //
        JButton loginButton = new JButton("Sing In");
        loginButton.setBounds(650, 250, 100, 30);
        SingInPanel.add(loginButton);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 250, 100, 30);
        SingInPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(SingInPanel);
            FirstQuestion.getInstance().draw();
        });
        //
        frame.add(SingInPanel);
        frame.repaint();
        frame.revalidate();
        loginButton.addActionListener(e -> {
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(new FileReader("Players.txt"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            String UN = usernametext.getText();
            String PW = passwordtext.getText();
            boolean c = false;
            while (fileReader.hasNext()) {
                Player p2 = new Gson().fromJson(fileReader.nextLine(), Player.class);
                if (UN.equals(p2.getUserName()) && PW.equals(p2.getPassWord())) {
                    fileReader.close();
                    c = true;
                    p = p2;
                    break;
                }
            }
            if (c) {
                try {
                    login.body(UN, "signIn", "ap2020");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.remove(SingInPanel);
                frame.setVisible(false);
                Drawer.getInstance(p).Enter();
            } else {
                JOptionPane.showMessageDialog(frame, "Sorry .... wrong username or password .... please try again", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
