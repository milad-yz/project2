package org.stuff.cards.spell;

import org.stuff.Card;
import org.stuff.cards.Spell;

public class GnomishArmyKnife extends Spell {
    public GnomishArmyKnife() {
        super("GnomishArmyKnife", 5, 5, "give a minion charge poisonous windFury lifeSteal divideShield taunt", "PALADIN", "images\\gnomisharmyknife.png", 3);
    }

    @Override
    public Card getClone() {
        return new GnomishArmyKnife();
    }
    //Card gnomishArmyKnife = new Card("gnomishArmyKnife", 5, 5, "give charge poisonous windfury lifesteal divideshield taunrt", "give a minion charge poisonous windFury lifeSteal divideShield taunt", "PALADIN", "images\\gnomisharmyknife.png", 3);
}
