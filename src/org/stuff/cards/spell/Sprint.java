package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class Sprint extends Spell {
    public Sprint() {
        super("Sprint", 5, 5, "draw 4 cards", "All", "images\\Sprint.png", 2);
    }

    @Override
    public Card getClone() {
        return new Sprint();
    }
    //Card sprint = new Card("Sprint", 5, 5, "draw 4 cards", "draw 4 cards", "All", "images\\sprint.jfif", 2);
    //finish
}
