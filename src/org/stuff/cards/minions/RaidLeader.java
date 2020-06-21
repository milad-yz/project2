package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class RaidLeader extends Minion {
    public RaidLeader() {
        super("RaidLeader", 7, 3, 2, 2,  "your other minions have +1 attack", "All", "images\\raidleader.png", 2);
    }

    @Override
    public Card getClone() {
        return new RaidLeader();
    }
    //Card raidleader = new Card("RaidLeader", 7, 3, 2, 2, "improve minion", "give a friendly minion 1 damage an 1 health", "All", "images\\raidleader.png", 2);
}
