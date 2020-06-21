package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class SwarmOfLocusts extends Spell {
    public SwarmOfLocusts() {
        super("SwarmOfLocusts", 6, 6, "summon seven 1/1 locusts with rush", "All", "images\\swarmoflocusts.png", 2);
    }

    @Override
    public Card getClone() {
        return new SwarmOfLocusts();
    }
    //Card swarmOfLocusts = new Card("SwarmOfLocusts", 6, 6, "", "summon seven 1/1 locusts with rush", "All", "images\\swarmoflocusts.png", 2);
}
