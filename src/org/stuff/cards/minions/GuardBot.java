package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class GuardBot extends Minion {
    public GuardBot() {
        super("GuardBot",7,2,2,3,"taunt","All","images\\GuardBot.png",3);
        this.haveTaunt=true;
    }

    @Override
    public Card getClone() {
        return new GuardBot();
    }
}
