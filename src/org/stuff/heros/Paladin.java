package org.stuff.heros;

import org.stuff.Hero;
import org.stuff.cards.minions.Behrad;
import org.stuff.cards.spell.GnomishArmyKnife;

public class Paladin extends Hero {
    public Paladin() {
        super("PALADIN", 0,30, 2, "images\\paladin.jpg", "images\\paladinPower.jpg",new Behrad(),new GnomishArmyKnife());
    }
    //Hero PALADIN = new Hero("PALADIN", 0, paladinCards, 30, "random a minion +1damage +1health", 2, "images\\paladin.jpg", "images\\paladinPower.jpg");
}
