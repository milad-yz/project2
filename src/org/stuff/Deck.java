package org.stuff;

import java.util.ArrayList;

public class Deck {
    public String name;
    public Hero deckHero;
    public ArrayList<Card> deckCards;
    public int allGame=0;
    public int winGame=0;
    public Deck(String name, Hero deckHero, ArrayList<Card> deckCards) {
        this.name=name;
        this.deckCards = deckCards;
        this.deckHero = deckHero;
    }

    public Deck(String name, Hero hero){
        this.name=name;
        deckCards=new ArrayList<>();
        this.deckHero=hero;
    }


    public float getAverageMana() {
        int counter=0;
        for (int i = 0; i < deckCards.size(); i++) {
            counter+=deckCards.get(i).mana;
        }
        return counter/deckCards.size();
    }

    public Card getBestCard() {
        ArrayList<Card> bestCards = new ArrayList<>();
        int maxUsage = 0;
        //per usage
        for (int i = 0; i < deckCards.size(); i++) {
            if (deckCards.get(i).used >= maxUsage)
                maxUsage = deckCards.get(i).used;
        }
        //rarity
        for (int i = 0; i < deckCards.size(); i++) {
            if (deckCards.get(i).used == maxUsage) {
                boolean flag = true;
                for (int j = 0; j < bestCards.size(); j++) {
                    if (bestCards.get(j).rarity > deckCards.get(i).rarity)
                        flag = false;
                }
                if (flag) {
                    bestCards.add(deckCards.get(i));
                    ArrayList<Card> temp = new ArrayList<>();
                    for (int j = 0; j < bestCards.size(); j++) {
                        if (bestCards.get(j).rarity == deckCards.get(i).rarity)
                            temp.add(bestCards.get(j));
                    }
                    bestCards = temp;
                }
            }
        }
        if (bestCards.size() == 1)
            return bestCards.get(0);
        //mana
        int maxMana = 0;
        for (int i = 0; i < bestCards.size(); i++) {
            if (bestCards.get(i).mana > maxMana)
                maxMana = bestCards.get(i).mana;
        }
        ArrayList<Card>temp=new ArrayList<>();
        for (int i = 0; i < bestCards.size(); i++) {
            if(bestCards.get(i).mana==maxMana){
                temp.add(bestCards.get(i));
            }
        }
        bestCards=temp;
        if (bestCards.size() == 1)
            return bestCards.get(0);
        //minions are better
        temp=new ArrayList<>();
        for (int i = 0; i < bestCards.size(); i++) {
            if(bestCards.get(i).getClass().getName().equals("Minion"))
                temp.add(bestCards.get(i));
        }
        //
        return bestCards.get(0);
    }
}
