package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class BloodFenRaptor extends Minion {
    public BloodFenRaptor() {
        super("BloodFenRaptor", 7, 2, 3, 2, "", "All", "images\\BloodfenRaptor.png", 1);
    }

    @Override
    public Card getClone() {
        return new BloodFenRaptor();
    }
    //Card bloodfenRaptor = new Card("BloodfenRaptor", 7, 2, 3, 2, "", "", "All", "images\\bloodfenraptor.png", 1);
    //finish
}
