package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class BatterHead extends Minion {
    public BatterHead() {
        super("BatterHead", 23, 8, 3, 12, "after killing a minion it may attack again", "All", "images\\batterhead.png", 4);
    }

    @Override
    public Card getClone() {
        return new BatterHead();
    }
    //Card batterhead = new Card("BatterHead", 23, 8, 3, 12, "after killing may attack", "after killing a minion it may attack again", "All", "images\\batterhead.png", 4);

}
