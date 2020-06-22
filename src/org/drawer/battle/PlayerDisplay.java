package org.drawer.battle;

import org.drawer.battle.functions.BattleCryFunction;
import org.drawer.battle.functions.BattleSpellFunctions;
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
    public ArrayList<SideQuestCounter> sideQuestCounters=new ArrayList<>();

    public PlayerDisplay(JPanel handPanel, JPanel battlePanel, ArrayList<Card> handCards, HashMap<Integer, Minion> battleCards, int mana, Document eventDoc, JLabel manaText, Hero hero, JPanel heroPanel, Battle battle, Deck deck) {
        this.handPanel = handPanel;
        this.battlePanel = battlePanel;
        this.handCards = handCards;
        this.battleCards = battleCards;
        this.mana = mana;
        this.heroPanel = heroPanel;
        this.eventDoc = eventDoc;
        this.manaText = manaText;
        this.hero = hero;
        this.deck = deck;
        this.battle = battle;
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
                            manaCounterSideQuest(card.mana);
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
                            BattleHandler.QuestAndRewardHandler(battle ,(QuestAndReward) card);
                            break;
                    }
                    displaySemaphore.release();
                    try {
                        eventDoc.insertString(eventDoc.getLength(), hero.name + " played " + card.name + "\n", null);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            handPanel.add(cardButton);
        }
    }

    private void heroButtonNormal() {
        heroPanel.removeAll();
        HeroButton heroButton = new HeroButton(hero);
        HeroPowerButton heroPowerButton = new HeroPowerButton(hero);
        heroButton.setBounds(0, 0, 110, 120);
        heroButton.addActionListener(e -> {
            if (hero.damage > 0 && hero.rush>0) {
                battle.whoseTurn().setN(5);
                battle.whoseNotTurn().setN(10);
                battle.semaphoreNotify();
            }
        });
        heroPowerButton.setBounds(110, 0, 40, 50);
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
                    manaCounterSideQuest(hand2battleMinion.mana);
                    handCards.remove(hand2battleMinion);
                    BattleHandler.battleCryHandler(this, battle, hand2battleMinion);
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

    private void heroButtonPlanting() {
        heroButtonNormal();
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

    private void heroButtonEnemyTurn() {
        heroButtonNormal();
    }

    //n=4
    private void battlePanelEnemyAttack() {
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
                    enemyCard.attack(minion);
                    enemyCard = null;
                    setN(3);
                    battle.enemySetN(1);
                    battle.semaphoreNotify();
                });
                battlePanel.add(cardButton);
            }
        }
    }

    private void handPanelEnemyAttack() {
        handPanelEnemyTurn();
    }

    private void heroButtonEnemyAttack() {
        heroPanel.removeAll();
        HeroButton heroButton = new HeroButton(hero);
        HeroPowerButton heroPowerButton = new HeroPowerButton(hero);
        heroButton.setBounds(0, 0, 110, 120);
        heroPowerButton.setBounds(110, 0, 40, 50);
        heroButton.addActionListener(e -> {
            enemyCard.attack(hero);
            enemyCard = null;
            setN(3);
            battle.enemySetN(1);
            battle.semaphoreNotify();
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
        handPanelEnemyTurn();
    }

    private void heroButtonAttack() {
        heroButtonNormal();
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
        heroButtonNormal();
    }
    //n=7 m=1 copy a minion to deck and hand

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
        heroButtonNormal();
    }

    //n=8
    private void battlePanelSpellWeOnEnemy() {
        battlePanelEnemyTurn();
    }

    private void handPanelSpellWeOnEnemy() {
        handPanelNormal();
    }

    private void heroPanelSpellWeOnEnemy() {
        heroButtonNormal();
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
                Minion minion = battleCards.get(i);
                MinionButton cardButton = new MinionButton(minion);
                cardButton.setBounds(10 + 90 * i, 10, 75, 100);
                cardButton.addActionListener(e -> {
                    minion.setHealth(minion.getHealth()-battle.whoseTurn().hero.damage);
                    battle.whoseTurn().hero.health-=minion.getDamage();
                    battle.whoseTurn().hero.rush-=1;
                    battle.whoseNotTurn().setN(1);
                    battle.whoseNotTurn().setN(3);
                    battle.semaphoreNotify();
                });
                battlePanel.add(cardButton);
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
            battle.whoseNotTurn().hero.health-=battle.whoseTurn().hero.damage;
            battle.whoseTurn().hero.health-=battle.whoseNotTurn().hero.damage;
            battle.whoseTurn().hero.rush-=1;
            battle.whoseTurn().setN(1);
            battle.whoseNotTurn().setN(3);
            battle.semaphoreNotify();
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
    //n=8 spell we on enemy  //n=9 spell enemy on us
    public void render() {
        try {
            displaySemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (n == 1) {
            handPanelNormal();
            battlePanelNormal();
            heroButtonNormal();
        } else if (n == 2) {
            handPanelPlanting();
            battlePanelPlanting();
            heroButtonPlanting();
        } else if (n == 3) {
            handPanelEnemyTurn();
            battlePanelEnemyTurn();
            heroButtonEnemyTurn();
        } else if (n == 4) {
            handPanelEnemyAttack();
            battlePanelEnemyAttack();
            heroButtonEnemyAttack();
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

    public void endTurnNotify() {
        for (int i = 0; i < 7; i++) {
            if (battleCards.get(i) != null) {
                battleCards.get(i).rush = 1;
                semaphoreNotify();
            }
        }
        hero.rush=1;
    }

    private void manaCounterSideQuest(int mana) {
        for (int i = 0; i < sideQuestCounters.size(); i++) {
            sideQuestCounters.get(i).drawReward(mana);
        }
    }
}
