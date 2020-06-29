package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class Duel extends Spell {
    @Override
    public Card getClone() {
        return new Duel();
    }

    public Duel() {
        super("Duel!",5,5,"summon a minion from each players deck. they fight","All","images\\Duel!.png",5);
    }
}
