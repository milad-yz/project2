package org.drawer.battle.functions;

import org.drawer.battle.Battle;
import org.drawer.battle.PassiveInfo;
import org.drawer.battle.PlayerDisplay;
import org.stuff.cards.Minion;
import org.stuff.cards.QuestAndReward;
import org.stuff.cards.minions.DraconicEmissary;

import javax.swing.*;
import java.util.Random;

public class SideQuestCounter implements Runnable {
    private int spellManaCounter = 0;
    private int minionManaCounter = 0;
    private boolean isSpellManaCounting = false;
    private boolean isMinionManaCounting = false;
    private PlayerDisplay playerDisplay;
    private Battle battle;
    private int n;
    private JPanel questAndRewardPanel;
    private JProgressBar jProgressBar = new JProgressBar();
    private boolean isRunning = true;
    private QuestAndReward questAndReward;
    public SideQuestCounter(PlayerDisplay playerDisplay, Battle battle, String counted, int n, JPanel questAndRewardPanel, QuestAndReward questAndReward) {
        this.playerDisplay = playerDisplay;
        this.battle = battle;
        this.questAndReward=questAndReward;
        this.n = n;
        this.questAndRewardPanel = questAndRewardPanel;
        if (counted.equals("Spell")) {
            isSpellManaCounting = true;
            jProgressBar.setString("Learn Draconic");
        } else if (counted.equals("Minion")) {
            isMinionManaCounting = true;
            jProgressBar.setString("Strength In Number");
        }
        jProgressBar.setStringPainted(true);
        jProgressBar.setValue(0);
        jProgressBar.setMaximum(n);
        questAndRewardPanel.add(jProgressBar);
        Thread thread = new Thread(this);
        thread.start();
    }

    public void drawRewardMinion(int mana) {
          if (isMinionManaCounting) {
            minionManaCounter += mana;
            if (minionManaCounter >= n) {
                summonFromDeck();
                minionManaCounter = 0;
                isMinionManaCounting = false;
                questAndRewardPanel.remove(jProgressBar);
                playerDisplay.sideQuestCounters.remove(this);
                isRunning = false;
            }
        }
    }

    private void summonFromDeck() {
        if(PassiveInfo.isDeckReader&&questAndReward.rewardCard!=null){
            Minion minion = null;
            for (int i = 0; i < playerDisplay.deck.deckCards.size(); i++) {
                if(playerDisplay.deck.deckCards.get(i).name.equals(questAndReward.rewardCard.name)){
                    minion= (Minion) playerDisplay.deck.deckCards.get(i);
                    break;
                }
            }
            if(minion!=null){
                for (int k = 0; k < 7; k++) {
                    if (playerDisplay.battleCards.get(k) == null) {
                        playerDisplay.deck.deckCards.remove(minion);
                        playerDisplay.battleCards.put(k, minion);
                        BattleFunctions.summonNotify(battle, playerDisplay, minion);
                        break;
                    }
                }
            }
        }else{
            BattleSpellFunctions.summonFromDeck(battle,battle.whoseTurn(),1);
        }
    }

    public void drawRewardSpell(int mana) {
        if (isSpellManaCounting) {
            spellManaCounter += mana;
            if (spellManaCounter >= n) {
                BattleSpellFunctions.summon(battle, playerDisplay, new DraconicEmissary(), 1);
                spellManaCounter = 0;
                isSpellManaCounting = false;
                questAndRewardPanel.remove(jProgressBar);
                playerDisplay.sideQuestCounters.remove(this);
                isRunning = false;
            }
        }
    }


    @Override
    public void run() {
        while (isRunning) {
            if (isSpellManaCounting)
                jProgressBar.setValue(spellManaCounter);
            if (isMinionManaCounting)
                jProgressBar.setValue(minionManaCounter);
            questAndRewardPanel.repaint();
        }
    }

}
