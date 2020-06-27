package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class SilverHandRecruit extends Minion {
    public SilverHandRecruit() {
        super("SilverHandRecruit",3,1,1,1,"","All","images\\SilverHandRecruit.png",2);
    }
    @Override
    public Card getClone() {
        return new SilverHandRecruit();
    }
}
