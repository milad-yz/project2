package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class Locust extends Minion {
    public Locust() {
        super("Locust",3,1,1,1,"rush","All","images\\Locust.png",1,1);
    }

    @Override
    public Card getClone() {
        return new Locust();
    }
    //finish
}
