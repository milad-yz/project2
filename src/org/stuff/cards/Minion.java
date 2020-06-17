package org.stuff.cards;

import org.stuff.Card;
import org.stuff.Hero;

public class Minion extends Card {
    private int damage;
    private int health;
    public Minion(String name, int cost, int mana,int damage,int health, String description, String specialFor, String icon, int rarity) {
        super(name, cost, mana, description, specialFor, icon, rarity);
        this.damage=damage;
        this.health=health;
    }
    public void attack(Minion enemyMinion){
    }
    public void attack(Hero hero){
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return "Minion{" +
                "damage=" + damage +
                ", health=" + health +
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
}
