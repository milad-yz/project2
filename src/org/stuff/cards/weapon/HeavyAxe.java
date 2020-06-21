package org.stuff.cards.weapon;

import org.stuff.Card;
import org.stuff.cards.Weapon;

public class HeavyAxe extends Weapon {
    public HeavyAxe() {
        super("HeavyAxe", 5, 1, 1, 3,"","All", "images\\heavyaxe.png", 1);
    }

    @Override
    public Card getClone() {
        return new HeavyAxe();
    }
    //Card heavyaxe = new Card("HeavyAxe", 1, 1, 1, 3, "images\\heavyaxe.png", 1);
}
