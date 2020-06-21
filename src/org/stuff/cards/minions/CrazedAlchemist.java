package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class CrazedAlchemist extends Minion {
    public CrazedAlchemist() {
        super("CrazedAlchemist", 6, 2, 2, 2, "swap the attack and health of a minion", "All", "images\\crazedalchemist.png", 2);
    }

    @Override
    public Card getClone() {
        return new CrazedAlchemist();
    }
    //Card CrazedAlchemist = new Card("CrazedAlchemist", 6, 2, 2, 2, "swap damage health", "swap the attack and health of a minion", "All", "images\\crazedalchemist.png", 2);
    //finish
}
