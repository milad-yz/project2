package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class Overflow extends Spell {
    public Overflow() {
        super("Overflow",7,7,"Restore 5 health to all characters , Draw 5 cards","All","images\\Overflow.png",4);
    }

    @Override
    public Card getClone() {
        return new Overflow();
    }
}
