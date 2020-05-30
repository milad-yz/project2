package org.drawer.sign;

import org.drawer.Drawer;
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
        JLabel userlabel = new JLabel("Username:");
        userlabel.setBounds(450, 50, 100, 30);
        SingUpPanel.add(userlabel);
        //
        JLabel passlabel = new JLabel("Password:");
        passlabel.setBounds(450, 150, 100, 30);
        SingUpPanel.add(passlabel);
        //
        JTextField usernametext = new JTextField();
        usernametext.setBounds(550, 50, 100, 30);
        SingUpPanel.add(usernametext);
        //
        JPasswordField passwordtext = new JPasswordField();
        passwordtext.setBounds(550, 150, 100, 30);
        SingUpPanel.add(passwordtext);
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
            String UN = usernametext.getText();
            String PW = passwordtext.getText();
            try {
                if (FileWorks.twoEqualsUserName("Players.txt", UN)) {
                    JOptionPane.showMessageDialog(frame, "we have the same username please try another username", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ArrayList<Hero> heros = Hero.heroSetter();
                    ArrayList<Card> currentCards = Card.currentCardsSetter();
                    ArrayList<Card> allCard = Card.allCardsSetter();
                    ArrayList<Deck> playerdeck = Deck.DeckSetter(heros, currentCards);
                    p = new Player(UN, PW, currentCards, allCard, heros, playerdeck, playerdeck.get(0));
                    FileWorks.filewrite("Players.txt", p);
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
