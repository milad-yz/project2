package org.stuff.cards.minions;

import org.stuff.Card;
import org.stuff.cards.Minion;

public class SecurityRover extends Minion {
    public SecurityRover() {
        super("SecurityRover", 14, 6, 2, 6,  "whenever this minion take damage summon a 2/3 Mech with taunt", "All", "images\\SecurityRover.png", 2);
    }

    @Override
    public Card getClone() {
        return new SecurityRover();
    }
    //Card securityRover = new Card("SecurityRover", 14, 6, 2, 6, "", "whenever this minion take damage summon a 2/3 Meck with taunt", "All", "images\\securityrover.png", 2);
}
