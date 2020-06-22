package org.stuff.cards.questAndReward;

import org.stuff.Card;
import org.stuff.cards.QuestAndReward;

public class StrengthInNumbers extends QuestAndReward {
    public StrengthInNumbers() {
        super("StrengthInNumbers", 1, 1, "sideQuest:spend 10 mana on minions...reward:summon a minion from your deck", "All", "images\\strengthinnumbers.png", 2);
    }

    @Override
    public Card getClone() {
        return new StrengthInNumbers();
    }
    //Card strengthInNumbers = new Card("StrengthInNumbers", 1, 1, "", "sideQuest:spend 10 mana on a minion...reward:summon a minion from your deck", "All", "images\\strengthinnumbers.png", 2);
    //finish
}
