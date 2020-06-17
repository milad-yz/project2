package org.drawer.sign;

import org.drawer.Drawer;
import org.drawer.mainParts.collections.DeckShowRoom;
import org.drawer.sign.setters.CardSetter;
import org.drawer.sign.setters.DeckSetter;
import org.drawer.sign.setters.HeroSetter;
import org.fileWorks.FileWorks;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;
import org.stuff.Deck;
import org.stuff.Hero;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class SignUp {
    private JFrame frame;
    private Player p;

    public SignUp(JFrame frame) {
        this.frame = frame;
        draw();
    }

    private void draw() {
        JPanel SingUpPanel = new JPanel();
        SingUpPanel.setLayout(null);
        SingUpPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel SingUpRoom = new JLabel("Sing Up Room:");
        SingUpRoom.setBounds(500, 0, 100, 30);
        SingUpPanel.add(SingUpRoom);
        //
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(450, 50, 100, 30);
        SingUpPanel.add(userLabel);
        //
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(450, 150, 100, 30);
        SingUpPanel.add(passLabel);
        //
        JTextField usernameText = new JTextField();
        usernameText.setBounds(550, 50, 100, 30);
        SingUpPanel.add(usernameText);
        //
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(550, 150, 100, 30);
        SingUpPanel.add(passwordText);
        //
        JButton loginButton = new JButton("Sing Up");
        loginButton.setBounds(650, 250, 100, 30);
        SingUpPanel.add(loginButton);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 250, 100, 30);
        SingUpPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(SingUpPanel);
            FirstQuestion.getInstance().draw();
        });
        //
        frame.add(SingUpPanel);
        frame.repaint();
        frame.revalidate();
        loginButton.addActionListener(e -> {
            String UN = usernameText.getText();
            String PW = passwordText.getText();
            try {
                if (FileWorks.twoEqualsUserName("Players.txt", UN)) {
                    JOptionPane.showMessageDialog(frame, "we have the same username please try another username", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ArrayList<Hero> heros = HeroSetter.heroSetter();
                    ArrayList<Card> currentCards = CardSetter.currentCardsSetter();
                    ArrayList<Card> allCard = CardSetter.allCardsSetter();
                    ArrayList<Deck> playerDeck = DeckSetter.deckSetter(heros, currentCards);
                    p = new Player(UN, PW, currentCards, allCard, heros, playerDeck, playerDeck.get(0));
                    FileWorks.fileWrite("Players.txt", p);
                    login.createLog(UN, PW);
                    frame.remove(SingUpPanel);
                    frame.setVisible(false);
                    Drawer.getInstance(p).Enter();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
