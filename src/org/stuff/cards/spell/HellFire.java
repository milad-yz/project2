package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class HellFire extends Spell {
    public HellFire() {
        super("HellFire", 4, 4, "deal 3 damage to all characters", "All", "images\\HellFire.png", 2);
    }

    @Override
    public Card getClone() {
        return new HellFire();
    }
    //Card hellfire = new Card("HellFire", 4, 4, "damage3toall", "deal 3 damage to all characters", "All", "images\\hellfire.png", 2);
    //finish
}
