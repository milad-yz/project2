package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class Shahzad extends Spell {
    public Shahzad() {
        super("Shahzad", 3, 3, "give all minions +2 health", "PRIEST", "images\\shahzad.jpg", 2);
    }

    @Override
    public Card getClone() {
        return new Shahzad();
    }
    //Card shahzad = new Card("shahzad", 3, 3, "give all minions +2 health", "give all minions +2 health", "PRIEST", "images\\shahzad.jpg", 2);
}
