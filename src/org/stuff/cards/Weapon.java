package org.stuff.cards;

import org.stuff.Card;

public class Weapon extends Card {
    private int damage;
    private int defence;
    public Weapon(String name, int cost, int mana,int damage,int defence, String description, String specialFor, String icon, int rarity) {
        super(name, cost, mana, description, specialFor, icon, rarity);
        this.damage=damage;
        this.defence=defence;
    }

    @Override
    public Card getClone() {
        return new Weapon(this.name,this.cost,this.mana,this.damage,this.defence,this.description,this.specialFor,this.icon,this.rarity);
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "damage=" + damage +
                ", defence=" + defence +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", mana=" + mana +
                ", description='" + description + '\'' +
                ", specialFor='" + specialFor + '\'' +
                ", icon='" + icon + '\'' +
                ", rarity=" + rarity +
                ", used=" + used +
                '}';
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }
}
