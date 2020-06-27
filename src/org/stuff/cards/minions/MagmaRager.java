package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class MagmaRager extends Minion {
    public MagmaRager() {
        super("MagmaRager", 9, 3, 5, 1, "", "All", "images\\MagmaRager.png", 2);
    }

    @Override
    public Card getClone() {
        return new MagmaRager();
    }
    //Card magmarager = new Card("MagmaRager", 9, 3, 5, 1, "", "", "All", "images\\magmarager.png", 2);
    //finish
}
