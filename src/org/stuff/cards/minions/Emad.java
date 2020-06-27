package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class Emad extends Minion {
    public Emad() {
        super("Emad", 16, 5, 8, 3, "", "WARLOCK", "images\\Emad.png", 3);
    }

    @Override
    public Card getClone() {
        return new Emad();
    }
    //Card emad = new Card("emad", 16, 5, 8, 3, "", "", "WARLOCK", "images\\emad.jpg", 3);
    //finish
}
