package org.drawer.battle;

import org.drawer.Drawer;
import org.drawer.battle.functions.BattleCryFunction;
import org.drawer.battle.functions.TimerCounter;
import org.drawer.labelsAndButtons.CardButton;
import org.player.Player;
import org.stuff.Card;
import org.stuff.Deck;
import org.stuff.cards.Minion;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Battle implements Runnable {
    private Deck deck;
    private Deck deck2;
    private int deck2battlePerTurn = 1;
    private int mana;
    private int firstMana = 2;
    public int perTurnHeroPower = 1;
    private static int counter = 0;
    private HashMap<Integer, Minion> battleCards = new HashMap<>(7);
    private HashMap<Integer, Minion> battleCards2 = new HashMap<>(7);
    private ArrayList<Card> handCards = new ArrayList<>();
    private ArrayList<Card> handCards2 = new ArrayList<>();
    public JPanel mapPanel;
    public JFrame frame;
    private PlayerDisplay playerDisplay;
    private PlayerDisplay playerDisplay2;
    public boolean playerTurn = true;
    private JLabel deckSize = new JLabel();
    private JLabel manaText = new JLabel();
    private TimerCounter timerCounter;

    public Battle(JFrame frame, Deck deck, Deck deck2, int deck2battlePerTurn, int mana, int perTurnHeroPower, int firstMana) {
        this.deck = deck;
        this.deck2 = deck2;
        this.deck2battlePerTurn = deck2battlePerTurn;
        this.mana = mana;
        this.frame = frame;
        this.perTurnHeroPower = perTurnHeroPower;
        this.firstMana = firstMana;
        Thread thread = new Thread(this);
        thread.start();
    }

    private void init() {
        mapPanel = new JPanel(null);
        mapPanel.setBounds(0, 0, 1200, 700);
        //
        JPanel handPanel = new JPanel(new GridBagLayout());
        JScrollPane handScroll = new JScrollPane(handPanel);
        handScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        handScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        handScroll.setBounds(230, 530, 500, 130);
        handScroll.setOpaque(false);
        handPanel.setOpaque(false);
        mapPanel.add(handScroll);
        //
        JPanel handPanel2 = new JPanel(new GridBagLayout());
        JScrollPane handScroll2 = new JScrollPane(handPanel2);
        handScroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        handScroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        handScroll2.setBounds(230, 0, 500, 130);
        handScroll2.setOpaque(false);
        handPanel2.setOpaque(false);
        mapPanel.add(handScroll2);
        //
        JPanel battlePanel = new JPanel(null);
        battlePanel.setBounds(0, 0, 770, 120);
        JScrollPane battleScroll = new JScrollPane(battlePanel);
        battleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        battleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        battleScroll.setBounds(100, 330, 700, 120);
        battleScroll.setOpaque(false);
        battlePanel.setOpaque(false);
        mapPanel.add(battleScroll);
        //
        JPanel battlePanel2 = new JPanel(null);
        battlePanel2.setBounds(0, 0, 770, 120);
        JScrollPane battleScroll2 = new JScrollPane(battlePanel2);
        battleScroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        battleScroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        battleScroll2.setBounds(100, 200, 700, 120);
        battleScroll2.setOpaque(false);
        battlePanel2.setOpaque(false);
        mapPanel.add(battleScroll2);
        //mana text
        manaText.setText("your mana is : \n" + mana);
        manaText.setBounds(750, 530, 150, 110);
        manaText.setBackground(Color.CYAN);
        mapPanel.add(manaText);
        //event text
        JTextArea eventText = new JTextArea();
        Document eventDoc = eventText.getDocument();
        eventText.setBackground(Color.GREEN);
        eventText.setBounds(900, 200, 200, 300);
        //
        JScrollPane textScroll = new JScrollPane(eventText);
        textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textScroll.setBounds(900, 300, 200, 300);
        textScroll.setBackground(Color.YELLOW);
        mapPanel.add(textScroll);
        //
        JPanel heroPanel = new JPanel(null);
        heroPanel.setBounds(0, 0, 150, 120);
        JScrollPane heroScroll = new JScrollPane(heroPanel);
        heroScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        heroScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        heroScroll.setBounds(0, 530, 150, 120);
        mapPanel.add(heroScroll);
        // quest nad reward panel
        JPanel questAndRewardPanel = new JPanel(null);
        questAndRewardPanel.setLayout(new BoxLayout(questAndRewardPanel, BoxLayout.Y_AXIS));
        questAndRewardPanel.setBounds(0, 0, 200, 120);
        JScrollPane questAndRewardScroll = new JScrollPane(questAndRewardPanel);
        questAndRewardScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questAndRewardScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        questAndRewardScroll.setBounds(900, 140, 200, 120);
        mapPanel.add(questAndRewardScroll);
        //
        JPanel heroPanel2 = new JPanel(null);
        heroPanel2.setBounds(0, 0, 110, 120);
        JScrollPane heroScroll2 = new JScrollPane(heroPanel2);
        heroScroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        heroScroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        heroScroll2.setBounds(0, 20, 150, 120);
        mapPanel.add(heroScroll2);
        //
        handCards = BattleCryFunction.shuffleCard(3, deck);
        handCards2 =BattleCryFunction.shuffleCard(3, deck2);
        chooseYourCard();
        for (int i = 0; i < 7; i++) {
            battleCards.put(i, null);
            battleCards2.put(i, null);
        }
        playerDisplay = new PlayerDisplay(handPanel, battlePanel, handCards, battleCards, mana, eventDoc, manaText, heroPanel, this, deck, questAndRewardPanel);
        playerDisplay2 = new PlayerDisplay(handPanel2, battlePanel2, handCards2, battleCards2, 100, eventDoc, new JLabel(), heroPanel2, this, deck2, questAndRewardPanel);
        playerDisplay.setN(1);
        playerDisplay2.setN(3);
        //
        deckSize.setText("your deck size is : \n" + deck.deckCards.size());
        deckSize.setBounds(950, 100, 200, 30);
        mapPanel.add(deckSize);
        //
        JButton endTurnButton = new JButton("end turn");
        endTurnButton.setBounds(950, 60, 150, 30);
        endTurnButton.addActionListener(e -> {
            endTurn();
        });
        mapPanel.add(endTurnButton);
        //
        JButton endGameButton = new JButton("end Game");
        endGameButton.setBounds(950, 20, 150, 30);
        endGameButton.addActionListener(e -> {
            counter = 0;
            frame.remove(mapPanel);
            Drawer.getInstance().Enter();
        });
        mapPanel.add(endGameButton);
        //
        timerCounter = new TimerCounter(mapPanel, this);
        frame.add(mapPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void changeTurn() {
        if (playerTurn) {
            playerTurn = false;
            playerDisplay2.setN(1);
            playerDisplay.setN(3);
            playerDisplay.endTurnNotify();
        } else {
            playerTurn = true;
            playerDisplay2.setN(3);
            playerDisplay.setN(1);
            playerDisplay2.endTurnNotify();
        }
        semaphoreNotify();
    }

    public void semaphoreNotify() {
        playerDisplay.semaphoreNotify();
        playerDisplay2.semaphoreNotify();
    }

    public void chooseYourCard() {
        JPanel chooseYourCardPanel = new JPanel(new GridBagLayout());
        for (int i = 0; i < handCards.size(); i++) {
            CardButton cardButton = new CardButton(handCards.get(i));
            Card card = handCards.get(i);
            cardButton.addActionListener(e -> {
                deck.deckCards.add(card);
                chooseYourCardPanel.remove(cardButton);
                handCards.remove(card);
                handCards.addAll(BattleCryFunction.shuffleCard(1, deck));
                chooseYourCardPanel.repaint();
            });
            chooseYourCardPanel.add(cardButton);
        }
        JOptionPane.showMessageDialog(frame, chooseYourCardPanel, "choose your card", JOptionPane.PLAIN_MESSAGE);
    }

    public void wants2attack(Minion minion) {
        if (playerTurn) {
            playerDisplay.setN(5);
            playerDisplay2.setN(4);
            playerDisplay2.enemyAttack(minion);
        } else {
            playerDisplay.setN(4);
            playerDisplay2.setN(5);
            playerDisplay.enemyAttack(minion);
        }
        playerDisplay.semaphoreNotify();
        playerDisplay2.semaphoreNotify();
    }

    @Override
    public void run() {
        init();

        boolean running = true;
        while (running) {
            frame.repaint();
            frame.revalidate();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void enemySetN(int n) {
        if (playerTurn)
            playerDisplay.setN(n);
        else
            playerDisplay2.setN(n);
    }

    public void finishTheGame() {
        JOptionPane.showMessageDialog(frame, "Game finished", "Game finished", JOptionPane.INFORMATION_MESSAGE);
        frame.remove(mapPanel);
        Drawer.getInstance().Enter();
    }

    public PlayerDisplay whoseTurn() {
        if (playerTurn)
            return playerDisplay;
        else
            return playerDisplay2;
    }

    public PlayerDisplay whoseNotTurn() {
        if (playerTurn)
            return playerDisplay2;
        else
            return playerDisplay;
    }

    public void endTurn() {
        timerCounter.reset();
        if (!playerTurn) {
            counter++;
            if (counter < 10)
                mana = counter + firstMana;
            else
                mana = 10;
            if (mana > 10)
                mana = 10;
            playerDisplay.setMana(mana);
            manaText.setText("your mana is : \n" + mana);
            BattleCryFunction.drawCards(playerDisplay, this, deck2battlePerTurn);
            deckSize.setText("your deck size is : \n" + deck.deckCards.size());
        } else {
            BattleCryFunction.drawCards(playerDisplay2, this, 1);
        }
        changeTurn();
        frame.repaint();
        frame.revalidate();
    }
}
