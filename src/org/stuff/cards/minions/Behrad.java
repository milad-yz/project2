package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class Behrad extends Minion {
    public Behrad() {
        super("Behrad", 5, 1, 3, 1, "every turn its damage +1/+1", "PALADIN", "images\\Behrad.png", 3);
    }

    @Override
    public Card getClone() {
        return new Behrad();
    }

    @Override
    public void endTurnNotify() {
        setDamage(getDamage()+1);
    }
    //Card behrad = new Card("behrad", 5, 1, 3, 1, "+1damagepermap", "every turn its damage +1 damage", "PALADIN", "images\\behrad.jpg", 3);
    //finish
}
