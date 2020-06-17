package org.drawer.sign;

import com.google.gson.Gson;
import org.drawer.Drawer;
import org.drawer.sign.setters.CardSetter;
import org.drawer.sign.setters.HeroSetter;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.cards.Minion;

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
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(450, 50, 100, 30);
        SingInPanel.add(userLabel);
        //
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(450, 150, 100, 30);
        SingInPanel.add(passLabel);
        //
        JTextField usernameText = new JTextField();
        usernameText.setBounds(550, 50, 100, 30);
        SingInPanel.add(usernameText);
        //
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(550, 150, 100, 30);
        SingInPanel.add(passwordText);
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
            String UN = usernameText.getText();
            String PW = passwordText.getText();
            boolean c = false;
            while (fileReader.hasNext()) {
                Player p2 = new Gson().fromJson(fileReader.nextLine(), Player.class);
                if (UN.equals(p2.getUserName()) && PW.equals(p2.getPassWord())) {
                    fileReader.close();
                    c = true;
                    p = p2;
                    p.allCards= CardSetter.arrayCardCaster(p.allCards);
                    p.currentCards=CardSetter.arrayCardCaster(p.currentCards);
                    p.currentDeck.deckCards=CardSetter.arrayCardCaster(p.currentDeck.deckCards);
                    for (int i = 0; i < p.playerDeck.size(); i++) {
                        p.playerDeck.get(i).deckCards=CardSetter.arrayCardCaster(p.playerDeck.get(i).deckCards);
                    }
                    for (int i = 0; i < p.heros.size(); i++) {
                        p.heros.set(i, HeroSetter.heroCaster(p.heros.get(i)));
                    }
                    for (int i = 0; i < p.playerDeck.size(); i++) {
                        p.playerDeck.get(i).deckHero=HeroSetter.heroCaster(p.playerDeck.get(i).deckHero);
                    }
                    p.currentDeck.deckHero=HeroSetter.heroCaster(p.currentDeck.deckHero);
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
