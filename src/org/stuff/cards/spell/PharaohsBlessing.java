package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class PharaohsBlessing extends Spell {
    public PharaohsBlessing() {
        super("PharaohsBlessing", 6, 6,  "give a minion 4/4 divide shield with taunt", "All", "images\\pharaohsblessing.png", 2);
    }

    @Override
    public Card getClone() {
        return new PharaohsBlessing();
    }
    //Card pharaohsBlessing = new Card("PharaohsBlessing", 6, 6, "", "give a minion 4/4 divide shield with taunt", "All", "images\\pharaohsblessing.png", 2);
}
