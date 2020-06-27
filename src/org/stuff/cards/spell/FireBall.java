package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class FireBall extends Spell {
    public FireBall() {
        super("FireBall", 4, 4, "deal 6 damage", "All", "images\\FireBall.png", 2);
    }

    @Override
    public Card getClone() {
        return new FireBall();
    }
    //Card fireball = new Card("FireBall", 4, 4, "5damage", "5 damage to target", "All", "images\\fireball.png", 2);
    //finish
}
