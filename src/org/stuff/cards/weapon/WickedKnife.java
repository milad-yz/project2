package org.stuff.cards.weapon;

import org.stuff.Card;
import org.stuff.cards.Weapon;

public class WickedKnife extends Weapon {
    public WickedKnife() {
        super("WickedKnife", 5, 1, 1, 2,"","All", "images\\wickedknife.png", 1);
    }

    @Override
    public Card getClone() {
        return new WickedKnife();
    }
    //Card wickedKnife = new Card("WickedKnife", 1, 1, 1, 2, "images\\wickedknife.png", 1);
}
