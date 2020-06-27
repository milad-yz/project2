package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class RavagingGhoul extends Minion {
    public RavagingGhoul() {
        super("RavagingGhoul", 9, 3, 3, 3, "deal one damage to all enemy minions", "All", "images\\RavagingGhoul.png", 3);
    }

    @Override
    public Card getClone() {
        return new RavagingGhoul();
    }
    //Card ravagingGhoul = new Card("RavaginGhoul", 9, 3, 3, 3, "per-damage1toall", "deal one damage to all enemy minions", "All", "images\\ravagingghoul.png", 3);
    //finish
}
