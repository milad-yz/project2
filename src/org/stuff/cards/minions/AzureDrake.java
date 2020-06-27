package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class AzureDrake extends Minion {
    public AzureDrake() {
        super("AzureDrake", 13, 5, 4, 4, "Draw a card", "All", "images\\AzureDrake.png", 2);
    }
    //Card AzureDrake = new Card("AzureDrake", 13, 5, 4, 4, "draw1card", "Draw a card", "All", "images\\azuredrake.png", 2);
    //finish
    @Override
    public Card getClone() {
        return new AzureDrake();
    }

}
