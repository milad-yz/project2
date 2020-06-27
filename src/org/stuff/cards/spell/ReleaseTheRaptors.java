package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class ReleaseTheRaptors extends Spell {
    public ReleaseTheRaptors() {
        super("ReleaseTheRaptors", 7, 7,  "summon three raptors", "All", "images\\ReleaseTheRaptors.png", 2);
    }

    @Override
    public Card getClone() {
        return new ReleaseTheRaptors();
    }
    //Card releaseraptors = new Card("ReleaseTheRaptors", 7, 7, "release3raptors", "release three raptors", "All", "images\\releasetheraptors.jpg", 2);
    //finish
}
