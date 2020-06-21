package org.stuff.heros;

import org.stuff.Hero;
import org.stuff.cards.spell.Milad;
import org.stuff.cards.spell.Polymorph;

public class Mage extends Hero {
    public Mage() {
        super("MAGE", 0, 30, 2, "images\\mage.png", "images\\magePower.png",new Milad(),new Polymorph());
    }

    @Override
    public Hero getClone() {
        return new Mage();
    }
    //Hero MAGE = new Hero("MAGE", 0, MAGEcards, 30, "spell-2", 2, "images\\mage.png", "images\\magePower.png");
}
