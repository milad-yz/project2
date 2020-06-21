package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class CurioCollector extends Minion {
    public CurioCollector() {
        super("CurioCollector", 13, 5, 4, 4,  "when ever you draw a card gain 1/1", "All", "images\\curiocollector.png", 2);
    }

    @Override
    public Card getClone() {
        return new CurioCollector();
    }
//Card curioCollector = new Card("CurioCollector", 13, 5, 4, 4, "", "when ever you draw a card gain 1/1", "All", "images\\curiocollector.png", 2);
}
