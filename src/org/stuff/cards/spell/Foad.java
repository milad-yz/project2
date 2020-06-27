package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class Foad extends Spell {
    public Foad() {
        super("Foad", 2, 2, "take a minion of your enemy", "ROGUE", "images\\Foad.png", 2);
    }

    @Override
    public Card getClone() {
        return new Foad();
    }
    //Card foad = new Card("foad", 2, 2, "take a minion of your enemy", "ROGUE", "images\\foad.jpg", 2);
    //finish
}
