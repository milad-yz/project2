package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class FriendlySmith extends Spell {
    public FriendlySmith() {
        super("FriendlySmith", 1, 1, "Discover a weapon from any class. add it to your adventure deck with +2/+2", "ROGUE", "images\\FriendlySmith.png", 2);
    }

    @Override
    public Card getClone() {
        return new FriendlySmith();
    }
    //Card FriendlySmith = new Card("Friendly Smith", 1, 1, "draw a weapon", "draw a weapon for your hero", "ROGUE", "images\\friendlysmith.png", 2);
    //finish
}
