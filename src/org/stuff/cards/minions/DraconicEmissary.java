package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class DraconicEmissary extends Minion {
    public DraconicEmissary() {
        super("DraconicEmissary",18,6,6,6,"","All","images\\DraconicEmissary.png",1);
    }

    @Override
    public Card getClone() {
        return new DraconicEmissary();
    }
    //finish
}
