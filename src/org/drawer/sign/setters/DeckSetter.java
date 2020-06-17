package org.drawer.sign.setters;

import org.stuff.Card;
import org.stuff.Deck;
import org.stuff.Hero;

import java.util.ArrayList;

public class DeckSetter {
    public static ArrayList<Deck> deckSetter(ArrayList<Hero> heros, ArrayList<Card> cards) {
        ArrayList<Deck> deck = new ArrayList<>();
        for (int i = 0; i < heros.size(); i++) {
            ArrayList<Card> currentCards = new ArrayList<>();
            for (int j = 0; j < cards.size()&&(j<30); j++) {
                if (cards.get(j).specialFor.equals("All")||cards.get(j).specialFor.equals(heros.get(i).name)) {
                    currentCards.add(cards.get(j));
                }
            }
            deck.add(new Deck(heros.get(i).name,heros.get(i), currentCards));
        }
        return deck;
    }

}
