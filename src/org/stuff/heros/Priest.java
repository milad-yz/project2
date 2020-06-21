package org.stuff.heros;

import org.stuff.Hero;
import org.stuff.cards.minions.HighPriestAmet;
import org.stuff.cards.spell.Shahzad;

public class Priest extends Hero {
    public Priest() {
        super("PRIEST", 0,30, 2, "images\\priest.png", "images\\priestPower.jpg",new Shahzad(),new HighPriestAmet());
    }

    @Override
    public Hero getClone() {
        return new Priest();
    }
    //Hero PRIEST = new Hero("PRIEST", 0, paladinCards, 30, "card for health work 2x", 2, "images\\priest.png", "images\\priestPower.jpg");
}
