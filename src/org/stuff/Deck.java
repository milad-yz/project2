package org.stuff;

import java.util.ArrayList;

public class Deck {
    public String name;
    public Hero deckHero;
    public ArrayList<Card> deckCards;
    public int allGame=0;
    public int winGame=0;
    Deck(String name,Hero deckHero, ArrayList<Card> deckCards) {
        this.name=name;
        this.deckCards = deckCards;
        this.deckHero = deckHero;
    }

    public Deck(String name, Hero hero){
        this.name=name;
        deckCards=new ArrayList<>();
        this.deckHero=hero;
    }
    public static ArrayList<Deck> Decksetter(ArrayList<Hero> heros, ArrayList<Card> cards) {
        ArrayList<Deck> deck = new ArrayList<>();
        for (int i = 0; i < heros.size(); i++) {
            ArrayList<Card> currentCards = new ArrayList<>();
            for (int j = 0; j < cards.size()&& j<15; j++) {
                if (cards.get(j).specialFor.equals("All")) {
                    currentCards.add(cards.get(j));
                }
            }
            for (int j = 0; j < heros.get(i).HeroCards.size(); j++) {
                currentCards.add(heros.get(i).HeroCards.get(j));
            }
            deck.add(new Deck(heros.get(i).name,heros.get(i), currentCards));
        }
        return deck;
    }

    public void update() {

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
            if (deckCards.get(i).useage >= maxUsage)
                maxUsage = deckCards.get(i).useage;
        }
        //rarity
        for (int i = 0; i < deckCards.size(); i++) {
            if (deckCards.get(i).useage == maxUsage) {
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
            if(bestCards.get(i).isminion==1)
                temp.add(bestCards.get(i));
        }
        //
        return bestCards.get(0);
    }
}
