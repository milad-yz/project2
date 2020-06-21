package org.stuff.heros;

import org.stuff.Hero;
import org.stuff.cards.spell.Foad;
import org.stuff.cards.spell.FriendlySmith;

public class Rogue extends Hero {
    public Rogue() {
        super("ROGUE", 1, 30, 3, "images\\rogue.png", "images\\roguePower.png",new Foad(),new FriendlySmith());
    }

    @Override
    public Hero getClone() {
        return new Rogue();
    }
    //Hero ROGUE = new Hero("ROGUE", 1, SmithCard, 30, "specialCard-2", 3, "images\\rogue.png", "images\\roguePower.png");
}
