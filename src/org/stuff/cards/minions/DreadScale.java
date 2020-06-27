package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class DreadScale extends Minion {

    public DreadScale() {
        super("DreadScale", 8, 3, 4, 2, "at the end of your turn deal 1 damage to all minions", "WARLOCK", "images\\DreadScale.png", 2);
    }

    @Override
    public Card getClone() {
        return new DreadScale();
    }
    //Card Dreadscale = new Card("Dreadscale", 8, 3, 4, 2, "per-damage1toall", "at the end of your turn deal 1 damage to all minions", "WARLOCK", "images\\dreadscale.png", 2);
    //finish
}
