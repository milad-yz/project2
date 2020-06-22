package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class Sheep extends Minion {
    public Sheep() {
        super("Sheep",3,1,1,1,"","All","images\\Sheep.png",1);
    }

    @Override
    public Card getClone() {
        return new Sheep();
    }
    //finish
}
