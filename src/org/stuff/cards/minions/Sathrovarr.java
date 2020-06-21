package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class Sathrovarr extends Minion {
    public Sathrovarr() {
        super("Sathrovarr", 19, 9, 5, 5,  "choose a friendly minion and copy it to your deck battlefield and hand", "All", "images\\sathrovarr.png", 2);
    }

    @Override
    public Card getClone() {
        return new Sathrovarr();
    }
    //Card Sathrovarr = new Card("Sathrovarr", 19, 9, 5, 5, "", "choose a friendly minion and copy it to your deck battlefield and hand", "All", "images\\sathrovarr.png", 2);
    //finish
}
