package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class Milad extends Spell {

    public Milad() {
        super("Milad", 3, 3, "give your hero 5 health", "MAGE","images\\Milad.png", 3);
    }

    @Override
    public Card getClone() {
        return new Milad();
    }
    //Card milad = new Card("milad", 3, 3, "hero-hp+5", "give your hero 5 health", "MAGE", "images\\milad.jpg", 3);
    //finish
}
