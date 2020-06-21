package org.drawer.labelsAndButtons;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class MinionButton extends CardButton {
    private Minion minion;
    public MinionButton(Minion minion) {
        super(minion);
        this.minion=minion;
    }
}
