package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;
import org.stuff.cards.minions.BloodFenRaptor;

public class BookOfSpecters extends Spell {
    public BookOfSpecters() {
        super("BookOfSpecters", 2, 2, "draw 3 cards discard any spells drawn", "All", "images\\BookOfSpecters.png", 2);
    }

    @Override
    public Card getClone() {
        return new BookOfSpecters();
    }
    //Card bookOfSpecters = new Card("BookOfSpecters", 2, 2, "", "draw 3 cards discard any spells drawn", "All", "images\\bookofspecters.png", 2);
}
