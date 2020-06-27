package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class SilentKnight extends Minion {
    @Override
    public Card getClone() {
        return new SilentKnight();
    }

    public SilentKnight() {
        super("SilentKnight", 7, 3, 2, 2, "divide shield", "All", "images\\SilentKnight.png", 2);
        this.haveShield=true;
    }
    //Card SilentKnight = new Card("SilentKnight", 7, 3, 2, 2, "make a shield", "make a shield", "All", "images\\silentknight.jpg", 2);
}
