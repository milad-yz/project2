package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class WolfRider extends Minion {
    @Override
    public Card getClone() {
        return new WolfRider();
    }

    public WolfRider() {
        super("WolfRider", 7, 3, 3, 1, "charge", "All", "images\\wolfrider.png", 2,1);
    }
    //Card wolfrider = new Card("Wolfrider", 7, 3, 3, 1, "null", "charge", "All", "images\\wolfrider.png", 2);
    //finish
}
