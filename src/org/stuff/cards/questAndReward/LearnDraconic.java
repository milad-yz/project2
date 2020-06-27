package org.stuff.cards.questAndReward;

import org.stuff.Card;
import org.stuff.cards.QuestAndReward;

public class LearnDraconic extends QuestAndReward {
    public LearnDraconic() {
        super("LearnDraconic", 1, 1,  "SideQuest: spend 8 mana on spells...reward: summon a 6/6 dragon", "All", "images\\LearnDraconic.png", 2);
    }

    @Override
    public Card getClone() {
        return new LearnDraconic();
    }
//Card learnDraconic = new Card("LearnDraconic", 1, 1, "", "SideQuest: spend 8 mana on spells...reward: summon a 6/6 dragon", "All", "images\\learndraconic.png", 2);
    //finish
}
