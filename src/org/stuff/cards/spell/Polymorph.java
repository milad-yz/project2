package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class Polymorph extends Spell {

    public Polymorph() {
        super("Polymorph", 4, 4, "transfer a minion into a sheep", "MAGE", "images\\Polymorph.png", 2);
    }
    //Card polymorph = new Card("Polymorph", 4, 4, "convert-sheep", "convert an enemy minion to sheep", "MAGE", "images\\polymorph.png", 2);
    //finish

    @Override
    public Card getClone() {
        return new Polymorph();
    }
}
