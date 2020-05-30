package org.drawer;

import org.drawer.battle.Battle;
import org.drawer.mainParts.*;
import org.drawer.mainParts.collections.Collection;
import org.drawer.rooms.Buy;
import org.drawer.rooms.BuyCardShow;
import org.drawer.rooms.Sell;
import org.drawer.rooms.SellCardShow;
import org.player.Player;
import org.stuff.Card;

import javax.swing.*;
import java.io.IOException;

public class Drawer {

    private JFrame frame = new JFrame("hearthStone");
    private Player p;
    private static Drawer single_instance = null;

    private Drawer(Player p) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setLayout(null);
        this.p = p;
    }

    private Drawer() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setLayout(null);
    }

    public static Drawer getInstance(Player p) {
        single_instance = new Drawer(p);
        return single_instance;
    }

    public static Drawer getInstance() {
        if (single_instance == null)
            single_instance = new Drawer();
        return single_instance;
    }

    public void Enter() {
        new Enter(frame, p);
    }

    public void play() throws IOException {
        Battle b1;
        if (p.currentDeck == null) {
            JOptionPane.showMessageDialog(frame, "You have no current deck please first choose your main deck next come and play", "null deck", JOptionPane.INFORMATION_MESSAGE);
            collection();
        } else {
            for (int i = 0; i < p.currentDeck.deckCards.size(); i++) {
                p.currentDeck.deckCards.get(i).useage++;
            }
            p.update(p);
            frame.setVisible(false);
            b1 = new Battle(p);
            b1.passiveInfo();
        }

    }

    public void shop() {
        new Shop(frame, p);
    }

    public void buy(String massage) {
        new Buy(frame, p, massage);
    }

    public void buyCardShow(Card card, String massage, int n) {
        new BuyCardShow(frame, p, massage, n, card);
    }

    public void sellCardShow(Card card, String massage, int n) {
        new SellCardShow(frame, p, card, massage, n);
    }

    public void sell(String massage) {
        new Sell(frame, p, massage);
    }

    public void status() {
        new Status(frame, p);
    }

    public void collection() throws IOException {
        p.update(p);
        Collection c1 = new Collection(frame,p);
        c1.collection();
    }

    public void setting() {
        new Setting(frame, p);
    }
}
