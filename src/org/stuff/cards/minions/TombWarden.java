package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class TombWarden extends Minion {
    public TombWarden() {
        super("TombWarden", 17, 8, 3, 6, " taunt, battle cry: summon a copy of itself", "All", "images\\TombWarden.png", 2);
        this.haveTaunt=true;
    }

    @Override
    public Card getClone() {
        return new TombWarden();
    }
    //Card tombwarden = new Card("TmbWarden", 17, 8, 3, 6, "", "summon a copy of itself", "All", "images\\tombwarden.png", 2);
}
