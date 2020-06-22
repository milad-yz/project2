package org.stuff.cards;

import org.stuff.Card;
import org.stuff.Hero;
import org.stuff.Stuff;

public class Minion extends Card {
    private int damage;
    private int health;
    public int rush=0;
    public boolean haveTaunt=false;
    public Minion(String name, int cost, int mana,int damage,int health, String description, String specialFor, String icon, int rarity) {
        super(name, cost, mana, description, specialFor, icon, rarity);
        this.damage=damage;
        this.health=health;
    }
    public Minion(String name, int cost, int mana,int damage,int health, String description, String specialFor, String icon, int rarity,int rush) {
        super(name, cost, mana, description, specialFor, icon, rarity);
        this.damage=damage;
        this.health=health;
        this.rush=rush;
    }
    public void attack(Stuff enemyStuff){
        if(enemyStuff.getClass().getSuperclass().getName().equals("org.stuff.Hero")){
            Hero hero=(Hero)enemyStuff;
            hero.health-=this.damage;
        }else if(enemyStuff.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")){
            Minion minion=(Minion)enemyStuff;
            minion.health-=this.damage;
            this.health-=minion.damage;
        }else{
            System.out.println("gand zadi");
        }
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
    @Override
    public Card getClone(){
        return new Minion(this.name,this.cost,this.mana,this.damage,this.health,this.description,this.specialFor,this.icon,this.rarity);
    }
    public void SetN(int ourN,int  enemyN){}
}
