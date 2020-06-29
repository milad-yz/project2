package org.drawer.battle;

import org.drawer.battle.functions.*;
import org.drawer.labelsAndButtons.*;
import org.stuff.Card;
import org.stuff.Deck;
import org.stuff.Hero;
import org.stuff.cards.Minion;
import org.stuff.cards.QuestAndReward;
import org.stuff.cards.Spell;
import org.stuff.cards.Weapon;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.Semaphore;

public class PlayerDisplay implements Runnable {
    private JPanel handPanel;
    private JPanel battlePanel;
    public ArrayList<Card> handCards;
    public HashMap<Integer, Minion> battleCards;
    private Minion hand2battleMinion;
    private int n = 1;
    private int mana;
    private Semaphore displaySemaphore = new Semaphore(1);
    private Document eventDoc;
    private JLabel manaText;
    public Hero hero;
    private JPanel heroPanel;
    private Battle battle;
    private Minion enemyCard;
    public Deck deck;
    private int m = 0;
    public ArrayList<SideQuestCounter> sideQuestCounters = new ArrayList<>();
    public JPanel questAndRewardPanel;

    public PlayerDisplay(JPanel handPanel, JPanel battlePanel, ArrayList<Card> handCards, HashMap<Integer, Minion> battleCards, int mana, Document eventDoc, JLabel manaText, JPanel heroPanel, Battle battle, Deck deck,JPanel questAndRewardPanel) {
        this.handPanel = handPanel;
        this.battlePanel = battlePanel;
        this.handCards = handCards;
        this.battleCards = battleCards;
        this.mana = mana;
        this.heroPanel = heroPanel;
        this.eventDoc = eventDoc;
        this.manaText = manaText;
        this.hero = deck.deckHero;
        this.deck = deck;
        this.battle = battle;
        this.questAndRewardPanel=questAndRewardPanel;
        Thread thread = new Thread(this);
        thread.start();
    }

    //n=1
    private void battlePanelNormal() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) == null) {
                CardLabel cardLabel = new CardLabel(battleCards.get(i));
                cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                battlePanel.add(cardLabel);
            } else {
                if (battleCards.get(i).rush > 0) {
                    Minion minion = battleCards.get(i);
                    MinionButton cardButton = new MinionButton(minion);
                    cardButton.setBounds(10 + 90 * i, 10, 75, 100);
                    cardButton.addActionListener(e -> {
                        battle.wants2attack(minion);
                        minion.rush -= 1;
                        battle.whoseTurn().setN(5);
                        displaySemaphore.release();
                    });
                    battlePanel.add(cardButton);
                } else {
                    CardLabel cardLabel = new CardLabel(battleCards.get(i));
                    cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                    battlePanel.add(cardLabel);
                }
            }
        }
    }

    private void handPanelNormal() {
        handPanel.removeAll();
        for (int i = 0; i < handCards.size(); i++) {
            CardButton cardButton = new CardButton(handCards.get(i));
            Card card = handCards.get(i);
            cardButton.addActionListener(e -> {
                if (mana >= card.mana) {
                    switch (card.getClass().getSuperclass().getName()) {
                        case "org.stuff.cards.Minion":
                            if (haveEmptyPlace(battleCards)) {
                                hand2battleMinion = (Minion) card;
                                setN(2);
                            }
                            break;
                        case "org.stuff.cards.Spell":
                            mana -= card.mana;
                            manaOnSpellCounterSideQuest(card.mana);
                            handCards.remove(card);
                            BattleHandler.spellHandler(battle, (Spell) card);
                            break;
                        case "org.stuff.cards.Weapon":
                            mana -= card.mana;
                            handCards.remove(card);
                            BattleHandler.WeaponHandler(battle, (Weapon) card);
                            break;
                        default:
                            mana -= card.mana;
                            handCards.remove(card);
                            BattleHandler.QuestAndRewardHandler(battle, (QuestAndReward) card);
                            break;
                    }
                    displaySemaphore.release();
                    try {
                        if (!card.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion"))
                            eventDoc.insertString(eventDoc.getLength(), hero.name + " played " + card.name + "\n", null);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            handPanel.add(cardButton);
        }
    }

    private void heroPanelNormal() {
        heroPanel.removeAll();
        HeroButton heroButton = new HeroButton(hero);
        HeroPowerButton heroPowerButton = new HeroPowerButton(hero);
        heroButton.setBounds(0, 0, 110, 120);
        heroButton.addActionListener(e -> {
            if (hero.damage > 0 && hero.rush > 0) {
                battle.whoseTurn().setN(5);
                battle.whoseNotTurn().setN(10);
            } else {
                battle.whoseTurn().setN(1);
                battle.whoseNotTurn().setN(3);
            }
            battle.semaphoreNotify();
        });
        heroPowerButton.setBounds(110, 0, 40, 50);
        heroPowerButton.addActionListener(e -> {
            if (hero.heroPowerRush > 0 && mana >= hero.mana) {
                BattleHandler.heroPowerHandler(battle, hero);
            } else {
                setN(1);
            }
            battle.semaphoreNotify();
        });
        heroPanel.add(heroButton);
        heroPanel.add(heroPowerButton);
    }

    //n=2
    private void battlePanelPlanting() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) == null) {
                CardButton cardButton = new CardButton(battleCards.get(i));
                cardButton.setBounds(10 + 90 * i, 10, 75, 100);
                int finalI = i;
                cardButton.addActionListener(e -> {
                    setN(1);
                    battleCards.put(finalI, hand2battleMinion);
                    mana -= hand2battleMinion.mana;
                    manaOnMinionCounterSideQuest(hand2battleMinion.mana);
                    handCards.remove(hand2battleMinion);
                    BattleFunctions.animatedCard(battle.mapPanel,finalI,battle.playerTurn,hand2battleMinion);
                    BattleHandler.battleCryHandler(this, battle, hand2battleMinion);
                    try {
                        eventDoc.insertString(eventDoc.getLength(), hero.name + " played " + hand2battleMinion.name + "\n", null);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    displaySemaphore.release();
                });
                battlePanel.add(cardButton);
            } else {
                CardLabel cardLabel = new CardLabel(battleCards.get(i));
                cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                battlePanel.add(cardLabel);
            }
        }
    }

    private void handPanelPlanting() {
        handPanel.removeAll();
        for (int i = 0; i < handCards.size(); i++) {
            CardButton cardButton = new CardButton(handCards.get(i));
            Card card = handCards.get(i);
            cardButton.addActionListener(e -> {
                setN(1);
                if (mana >= card.mana) {
                    switch (card.getClass().getSuperclass().getName()) {
                        case "org.stuff.cards.Minion":
                            hand2battleMinion = (Minion) card;
                            setN(2);
                            break;
                        case "org.stuff.cards.Spell":
                            mana -= card.mana;
                            handCards.remove(card);
                            break;
                        case "org.stuff.cards.Weapon":
                            mana -= card.mana;
                            handCards.remove(card);
                            break;
                        default:
                            mana -= card.mana;
                            handCards.remove(card);
                            break;
                    }
                    displaySemaphore.release();
                    try {
                        eventDoc.insertString(eventDoc.getLength(), hero.name + " played " + card.name + "\n", null);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    manaText.setText("your mana is :" + mana);
                }
            });
            handPanel.add(cardButton);
        }
    }

    private void heroPanelPlanting() {
        heroPanelNormal();
    }

    //n=3
    private void battlePanelEnemyTurn() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            CardLabel cardLabel = new CardLabel(battleCards.get(i));
            cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
            battlePanel.add(cardLabel);
        }
    }

    private void handPanelEnemyTurn() {
        handPanel.removeAll();
        for (int i = 0; i < handCards.size(); i++) {
            CardLabel labelButton = new CardLabel(handCards.get(i));
            handPanel.add(labelButton);
        }
    }

    private void heroPanelEnemyTurn() {
        heroPanelNormal();
    }

    //n=4
    private void battlePanelEnemyAttack() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) != null) {
                if (!haveTaunt() || battleCards.get(i).haveTaunt) {
                    Minion minion = battleCards.get(i);
                    MinionButton cardButton = new MinionButton(minion);
                    cardButton.setBounds(10 + 90 * i, 10, 75, 100);
                    cardButton.addActionListener(e -> {
                        BattleHandler.damagedStuffHandler(battle, battle.whoseNotTurn(), minion);
                        BattleHandler.damagedStuffHandler(battle, battle.whoseTurn(), enemyCard);
                        enemyCard.attack(minion);
                        if (enemyCard.haveLifeSteal)
                            battle.whoseTurn().hero.health += Math.min(enemyCard.getDamage(), minion.getHealth());
                        enemyCard = null;
                        setN(3);
                        battle.enemySetN(1);
                        battle.semaphoreNotify();
                    });
                    battlePanel.add(cardButton);
                } else {
                    CardLabel cardLabel = new CardLabel(battleCards.get(i));
                    cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                    battlePanel.add(cardLabel);
                }
            } else {
                CardLabel cardLabel = new CardLabel(battleCards.get(i));
                cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                battlePanel.add(cardLabel);
            }
        }
    }

    private void handPanelEnemyAttack() {
        handPanelEnemyTurn();
    }

    private void heroPanelEnemyAttack() {
        heroPanel.removeAll();
        HeroButton heroButton = new HeroButton(hero);
        HeroPowerButton heroPowerButton = new HeroPowerButton(hero);
        heroButton.setBounds(0, 0, 110, 120);
        heroPowerButton.setBounds(110, 0, 40, 50);
        heroButton.addActionListener(e -> {
            if (!haveTaunt()) {
                enemyCard.attack(hero);
                enemyCard = null;
                setN(3);
                battle.enemySetN(1);
                battle.semaphoreNotify();
            }
        });
        heroPanel.add(heroButton);
        heroPanel.add(heroPowerButton);
    }

    //n=5
    private void battlePanelAttack() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            CardLabel cardLabel = new CardLabel(battleCards.get(i));
            cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
            battlePanel.add(cardLabel);
        }
    }

    private void handPanelAttack() {
        handPanelNormal();
    }

    private void heroButtonAttack() {
        heroPanelNormal();
    }

    //n=6  //m=1 swap damage and health
    private void battlePanelAllButton() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) == null) {
                CardLabel cardLabel = new CardLabel(battleCards.get(i));
                cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                battlePanel.add(cardLabel);
            } else {
                Minion minion = battleCards.get(i);
                MinionButton cardButton = new MinionButton(minion);
                cardButton.setBounds(10 + 90 * i, 10, 75, 100);
                cardButton.addActionListener(e -> {
                    switch (m) {
                        case 1:
                            BattleCryFunction.swapDamageAndHealthAction(minion);
                            break;
                    }
                    battle.whoseTurn().setN(1);
                    battle.whoseNotTurn().setN(3);
                    battle.semaphoreNotify();
                });
                battlePanel.add(cardButton);
            }
        }
    }

    private void handPanelAllButton() {
        handPanelEnemyTurn();
    }

    private void heroPanelAllButton() {
        heroPanelNormal();
    }
    //n=7 m=1 copy a minion to deck and hand  //m=2 army knife   // m=3 blessing  // m=4 priestPower

    private void battlePanelSpellFriendly() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) == null) {
                CardLabel cardLabel = new CardLabel(battleCards.get(i));
                cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                battlePanel.add(cardLabel);
            } else {
                Minion minion = battleCards.get(i);
                MinionButton cardButton = new MinionButton(minion);
                cardButton.setBounds(10 + 90 * i, 10, 75, 100);
                cardButton.addActionListener(e -> {
                    switch (m) {
                        case 1:
                            BattleCryFunction.copy2deck2handAction(this, minion);
                            setN(1);
                            break;
                        case 2:
                            BattleSpellFunctions.ArmyKnifeAction(battle, minion);
                            setN(1);
                            break;
                        case 3:
                            BattleSpellFunctions.blessingAction(battle, minion);
                            break;
                        case 4:
                            HeroFunction.priestPowerAction(battle,minion);
                            break;
                    }
                    battle.whoseTurn().setN(1);
                    battle.whoseNotTurn().setN(3);
                    battle.semaphoreNotify();
                });
                battlePanel.add(cardButton);
            }
        }
    }

    private void handPanelSpellFriendly() {
        handPanelEnemyTurn();
    }

    private void heroPanelSpellFriendly() {
        heroPanel.removeAll();
        HeroButton heroButton = new HeroButton(hero);
        HeroPowerButton heroPowerButton = new HeroPowerButton(hero);
        heroButton.setBounds(0, 0, 110, 120);
        heroButton.addActionListener(e -> {
            switch (m){
                case 4:
                    HeroFunction.priestPowerAction(battle,hero);
                    break;
            }
            battle.semaphoreNotify();
        });
        heroPowerButton.setBounds(110, 0, 40, 50);
        heroPowerButton.addActionListener(e -> {
            if (hero.heroPowerRush > 0 && mana >= hero.mana) {
                BattleHandler.heroPowerHandler(battle, hero);
            } else {
                setN(1);
            }
            battle.semaphoreNotify();
        });
        heroPanel.add(heroButton);
        heroPanel.add(heroPowerButton);
    }

    //n=8
    private void battlePanelSpellWeOnEnemy() {
        battlePanelEnemyTurn();
    }

    private void handPanelSpellWeOnEnemy() {
        handPanelNormal();
    }

    private void heroPanelSpellWeOnEnemy() {
        heroPanelNormal();
    }

    //n=9  //m=1 fire ball  //m=2 take minion //m=3 convert2sheep
    private void battlePanelSpellEnemyOnUs() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) == null) {
                CardLabel cardLabel = new CardLabel(battleCards.get(i));
                cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                battlePanel.add(cardLabel);
            } else {
                Minion minion = battleCards.get(i);
                MinionButton cardButton = new MinionButton(minion);
                cardButton.setBounds(10 + 90 * i, 10, 75, 100);
                cardButton.addActionListener(e -> {
                    switch (m) {
                        case 1:
                            BattleSpellFunctions.dealDamageAction(battle, minion, 6);
                            break;
                        case 2:
                            BattleSpellFunctions.takeMinionAction(battle, minion);
                            break;
                        case 3:
                            BattleSpellFunctions.convert2SheepAction(battle, minion);
                            break;
                    }
                    battle.whoseNotTurn().setN(1);
                    battle.whoseNotTurn().setN(3);
                });
                battlePanel.add(cardButton);
            }
        }
    }

    private void handPanelSpellEnemyOnUs() {
        handPanelEnemyTurn();
    }

    private void heroPanelSpellEnemyOnUs() {
        heroPanel.removeAll();
        HeroButton heroButton = new HeroButton(hero);
        HeroPowerButton heroPowerButton = new HeroPowerButton(hero);
        heroButton.setBounds(0, 0, 110, 120);
        heroPowerButton.setBounds(110, 0, 40, 50);
        heroButton.addActionListener(e -> {
            battle.whoseTurn().setN(1);
            battle.whoseNotTurn().setN(3);
            switch (m) {
                case 1:
                    BattleSpellFunctions.dealDamageAction(battle, hero, 6);
                    break;
            }
        });
        heroPanel.add(heroButton);
        heroPanel.add(heroPowerButton);
    }

    //n=10
    private void battlePanelEnemyHeroAttack() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) == null) {
                CardLabel cardLabel = new CardLabel(battleCards.get(i));
                cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                battlePanel.add(cardLabel);
            } else {
                if (!haveTaunt() || battleCards.get(i).haveTaunt) {
                    Minion minion = battleCards.get(i);
                    MinionButton cardButton = new MinionButton(minion);
                    cardButton.setBounds(10 + 90 * i, 10, 75, 100);
                    cardButton.addActionListener(e -> {
                        BattleHandler.damagedStuffHandler(battle, this, minion);
                        minion.setHealth(minion.getHealth() - battle.whoseTurn().hero.damage);
                        battle.whoseTurn().hero.health -= minion.getDamage();
                        battle.whoseTurn().hero.rush -= 1;
                        battle.whoseTurn().setN(1);
                        battle.whoseNotTurn().setN(3);
                        battle.semaphoreNotify();
                    });
                    battlePanel.add(cardButton);
                } else {
                    CardLabel cardLabel = new CardLabel(battleCards.get(i));
                    cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                    battlePanel.add(cardLabel);
                }
            }
        }
    }

    private void handPanelEnemyHeroAttack() {
        handPanelEnemyTurn();
    }

    private void heroPanelEnemyHeroAttack() {
        heroPanel.removeAll();
        HeroButton heroButton = new HeroButton(hero);
        HeroPowerButton heroPowerButton = new HeroPowerButton(hero);
        heroButton.setBounds(0, 0, 110, 120);
        heroPowerButton.setBounds(110, 0, 40, 50);
        heroButton.addActionListener(e -> {
            if(haveTaunt()) {
                HeroFunction.heroAttack(battle);
            }else{
                battle.whoseTurn().setN(1);
                battle.whoseNotTurn().setN(3);
            }
        });
        heroPanel.add(heroButton);
        heroPanel.add(heroPowerButton);
    }
    //n=11 m=1 mage attack
    private void battlePanelEnemyHeroPowerAttack() {
        battlePanel.removeAll();
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) == null) {
                CardLabel cardLabel = new CardLabel(battleCards.get(i));
                cardLabel.setBounds(10 + 90 * i, 10, 75, 100);
                battlePanel.add(cardLabel);
            } else {
                Minion minion = battleCards.get(i);
                MinionButton cardButton = new MinionButton(minion);
                cardButton.setBounds(10 + 90 * i, 10, 75, 100);
                cardButton.addActionListener(e -> {
                    switch (m) {
                        case 1:
                        HeroFunction.magePowerAction(battle, minion);
                        break;
                    }
                });
                battlePanel.add(cardButton);
            }
        }
    }

    private void handPanelEnemyHeroPowerAttack() {
        handPanelEnemyTurn();
    }

    private void heroPanelEnemyHeroPowerAttack() {
        heroPanel.removeAll();
        HeroButton heroButton = new HeroButton(hero);
        HeroPowerButton heroPowerButton = new HeroPowerButton(hero);
        heroButton.setBounds(0, 0, 110, 120);
        heroPowerButton.setBounds(110, 0, 40, 50);
        heroButton.addActionListener(e -> {
            switch (m){
                case 1:
                    HeroFunction.magePowerAction(battle,hero);
                    break;
            }
        });
        heroPanel.add(heroButton);
        heroPanel.add(heroPowerButton);
    }

    public void setM(int m) {
        this.m = m;
        semaphoreNotify();
    }

    public void setN(int n) {
        this.n = n;
        battle.semaphoreNotify();
    }

    //n=1 your turn normal  // n=2 planting // n=3 enemyTurn //n=4 enemy attack // n=5 attack // n=6 allBattleCardButton //n=7 spell on friendly
    //n=8 spell we on enemy  //n=9 spell enemy on us // n=10 enemy hero attack // n=11 hero power attack
    public void render() {
        try {
            displaySemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (n == 1) {
            handPanelNormal();
            battlePanelNormal();
            heroPanelNormal();
        } else if (n == 2) {
            handPanelPlanting();
            battlePanelPlanting();
            heroPanelPlanting();
        } else if (n == 3) {
            handPanelEnemyTurn();
            battlePanelEnemyTurn();
            heroPanelEnemyTurn();
        } else if (n == 4) {
            handPanelEnemyAttack();
            battlePanelEnemyAttack();
            heroPanelEnemyAttack();
        } else if (n == 5) {
            handPanelAttack();
            battlePanelAttack();
            heroButtonAttack();
        } else if (n == 6) {
            handPanelAllButton();
            battlePanelAllButton();
            heroPanelAllButton();
        } else if (n == 7) {
            handPanelSpellFriendly();
            battlePanelSpellFriendly();
            heroPanelSpellFriendly();
        } else if (n == 8) {
            handPanelSpellWeOnEnemy();
            battlePanelSpellWeOnEnemy();
            heroPanelSpellWeOnEnemy();
        } else if (n == 9) {
            handPanelSpellEnemyOnUs();
            battlePanelSpellEnemyOnUs();
            heroPanelSpellEnemyOnUs();
        } else if (n == 10) {
            handPanelEnemyHeroAttack();
            battlePanelEnemyHeroAttack();
            heroPanelEnemyHeroAttack();
        }else if(n==11){
            handPanelEnemyHeroPowerAttack();
            battlePanelEnemyHeroPowerAttack();
            heroPanelEnemyHeroPowerAttack();
        }
        isAlive();
        manaText.setText("your mana is :" + mana);
    }

    private void isAlive() {
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) != null && battleCards.get(i).getHealth() <= 0) {
                semaphoreNotify();
                BattleHandler.deathRattleHandler(this, battle, battleCards.get(i));
                battleCards.put(i, null);
            }
        }
        if (hero.health <= 0)
            battle.finishTheGame();
    }

    public void justShowCard(JFrame frame, Deck deck, JPanel panel) throws IOException {
        Random random = new Random();
        int i = random.nextInt(deck.deckCards.size());
        JLabel cardLabel = new JLabel();
        BufferedImage HeroPicture = ImageIO.read(new File(deck.deckCards.get(i).icon));
        cardLabel.setIcon(new ImageIcon(HeroPicture));
        cardLabel.setBounds(900, 530, 75, 110);
        panel.add(cardLabel);
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                panel.remove(cardLabel);
                frame.repaint();
                frame.revalidate();
                timer.cancel();
            }
        }, 2000);
        deck.deckCards.remove(i);
    }


    @Override
    public void run() {
        while (true) {
            render();
        }
    }

    private boolean haveEmptyPlace(HashMap<Integer, Minion> hashMap) {
        for (int i = 0; i < 7; i++) {
            if (hashMap.get(i) == null)
                return true;
        }
        return false;
    }

    public void semaphoreNotify() {
        displaySemaphore.release();
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void enemyAttack(Minion minion) {
        enemyCard = minion;
    }

    public int getMana() {
        return mana;
    }

    public void endTurnNotify() {
        BattleHandler.endTurnHandler(battle, this);
        battle.semaphoreNotify();
    }

    private void manaOnMinionCounterSideQuest(int mana) {
        for (int i = 0; i < sideQuestCounters.size(); i++) {
            sideQuestCounters.get(i).drawRewardMinion(mana);
        }
    }
    private void manaOnSpellCounterSideQuest(int mana) {
        for (int i = 0; i < sideQuestCounters.size(); i++) {
            sideQuestCounters.get(i).drawRewardSpell(mana);
        }
    }
    private boolean haveTaunt() {
        for (int i = 0; i < 7; i++)
            if (battleCards.get(i) != null)
                if (battleCards.get(i).haveTaunt)
                    return true;
        return false;

    }
}
