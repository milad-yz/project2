package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class VoidWalker extends Minion {
    public VoidWalker() {
        super("VoidWalker", 5, 1, 1, 3,  "taunt", "All", "images\\VoidWalker.png", 2);
        this.haveTaunt=true;
    }

    @Override
    public Card getClone() {
        return new VoidWalker();
    }
    //Card voidwalker = new Card("VoidWalker", 5, 1, 1, 3, "", "", "All", "images\\voidwalker.png", 2);
}
