package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class SwampKingDread extends Minion {
    public SwampKingDread() {
        super("SwampKingDread",25,7,9,9,"after your opponent palys a minion , attack it","All","images\\SwampKingDread.png",4);
    }

    @Override
    public Card getClone() {
        return new SwampKingDread();
    }
}
