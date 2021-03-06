package org.stuff.cards;

import org.stuff.Card;

public class Spell extends Card {

    public Spell(String name, int cost, int mana, String description, String specialFor, String icon, int rarity) {
        super(name, cost, mana, description, specialFor, icon, rarity);
    }

    @Override
    public Card getClone() {
        return new Spell(this.name,this.cost,this.mana,this.description,this.specialFor,this.icon,this.rarity);
    }

    @Override
    public String toString() {
        return "Spell{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", mana=" + mana +
                ", description='" + description + '\'' +
                ", specialFor='" + specialFor + '\'' +
                ", icon='" + icon + '\'' +
                ", rarity=" + rarity +
                ", used=" + used +
                '}';
    }
}
