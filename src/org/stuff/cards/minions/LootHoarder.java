package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class LootHoarder extends Minion {

    public LootHoarder() {
        super("LootHoarder", 5, 2, 2, 1, "Draw a card", "All", "images\\loothoarder.jpg", 3);
    }

    @Override
    public Card getClone() {
        return new LootHoarder();
    }
    //Card loothoarder = new Card("LootHoarder", 5, 2, 2, 1, "draw1card", "Draw a card", "All", "images\\loothoarder.jpg", 3);
    //finish
}
