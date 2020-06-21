package org.stuff.cards.weapon;

import org.stuff.Card;
import org.stuff.cards.Weapon;

public class GearBlade extends Weapon {
    @Override
    public Card getClone() {
        return new GearBlade();
    }

    public GearBlade() {
        super("GearBlade", 2, 2, 2, 3,"","All", "images\\gearblade.png", 1);
    }
    //Card gearBlade = new Card("GearBlade", 2, 2, 2, 3, "images\\gearblade.png", 1);
}
