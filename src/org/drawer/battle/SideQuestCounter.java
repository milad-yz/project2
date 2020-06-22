package org.drawer.battle;

import org.drawer.battle.functions.BattleSpellFunctions;
import org.stuff.cards.minions.DraconicEmissary;

public class SideQuestCounter {
    private int spellManaCounter = 0;
    private int minionManaCounter = 0;
    private boolean isSpellManaCounting = false;
    private boolean isMinionManaCounting = false;
    private PlayerDisplay playerDisplay;
    private Battle battle;
    private int n;

    public SideQuestCounter(PlayerDisplay playerDisplay, Battle battle, String counted, int n) {
        this.playerDisplay = playerDisplay;
        this.battle = battle;
        this.n = n;
        if (counted.equals("Spell"))
            isSpellManaCounting = true;
        else if (counted.equals("Minion"))
            isMinionManaCounting = true;
    }

    public void drawReward(int mana) {
        if (isSpellManaCounting) {
            spellManaCounter += mana;
            if (spellManaCounter >= n) {
                BattleSpellFunctions.summon(battle, new DraconicEmissary(), 1);
                spellManaCounter = 0;
                isSpellManaCounting = false;
                playerDisplay.sideQuestCounters.remove(this);
            }
        } else if (isMinionManaCounting) {
            minionManaCounter+=mana;
            if (minionManaCounter >= n) {
                BattleSpellFunctions.summonFromDeck(battle, 1);
                minionManaCounter = 0;
                isMinionManaCounting = false;
                playerDisplay.sideQuestCounters.remove(this);
            }
        }
    }
}
