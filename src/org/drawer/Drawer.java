package org.drawer;

import org.drawer.battle.PassiveInfo;
import org.drawer.mainParts.*;
import org.drawer.mainParts.collections.Collection;
import org.drawer.ShopRooms.Buy;
import org.drawer.ShopRooms.BuyCardShow;
import org.drawer.ShopRooms.Sell;
import org.drawer.ShopRooms.SellCardShow;
import org.drawer.sign.DeletePlayer;
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
        if (p.currentDeck == null||p.currentDeck.deckCards.size()<15) {
            JOptionPane.showMessageDialog(frame, "You have no current deck or your deck's size is less than 15 please first choose/complete your main deck then come and play", "null deck", JOptionPane.INFORMATION_MESSAGE);
            collection();
        } else {
            for (int i = 0; i < p.currentDeck.deckCards.size(); i++) {
                p.currentDeck.deckCards.get(i).used++;
            }
            p.update();
            new PassiveInfo(p,frame);
        }

    }

    public void shop() {
        new Shop(frame, p);
    }

    public void buy(String massage) {
        new Buy(frame, p, massage);
    }

    public void buyCardShow(Card card, int n) {
        new BuyCardShow(frame, p, n, card);
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
        p.update();
        Collection c1 = new Collection(frame, p);
        c1.collection();
    }

    public void setting() {
        new Setting(frame, p);
    }

    public void deletePlayer(){
        new DeletePlayer(frame,p);
    }
}
