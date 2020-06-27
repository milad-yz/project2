package org.stuff.heros;

import org.stuff.Hero;
import org.stuff.cards.minions.DreadScale;
import org.stuff.cards.minions.Emad;

public class Warlock extends Hero {
    public Warlock() {
        super("WARLOCK", 0,  35,  2, "images\\Warlock.png", "images\\warlockPower.png",new Emad(),new DreadScale());
    }

    @Override
    public Hero getClone() {
        return new Warlock();
    }
    //Hero WARLOCK = new Hero("WARLOCK", 0, WarlockCards, 35, "improve minion", 2, "images\\warlock.jpg", "images\\warlockPower.png");
}
