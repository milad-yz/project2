package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class HighPriestAmet extends Minion {
    public HighPriestAmet() {
        super("HighPriestAmet", 13, 4, 2, 7, "whenever summon a minion set its health equal to this minion's", "PRIEST", "images\\highpriestamet.png", 3);
    }

    @Override
    public Card getClone() {
        return new HighPriestAmet();
    }
//Card highPriestAmet = new Card("highPriestAmet", 13, 4, 2, 7, "when summon a minion change its health to his health", "when summon a minion change its health to his health", "PRIEST", "images\\highpriestamet.png", 3);
}
