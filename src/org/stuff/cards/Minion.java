package org.stuff.cards;

import org.stuff.Card;
import org.stuff.Hero;
import org.stuff.Stuff;

public class Minion extends Card {
    private int damage;
    private int health;
    public final int finalDamage;
    public final int finalHealth;
    public int rush=0;
    public boolean haveTaunt=false;
    public boolean haveShield=false;
    public boolean havePoisonous=false;
    public int perTurnAttack=1;
    public boolean haveLifeSteal=false;
    public Minion(String name, int cost, int mana,int damage,int health, String description, String specialFor, String icon, int rarity) {
        super(name, cost, mana, description, specialFor, icon, rarity);
        this.damage=damage;
        this.health=health;
        this.finalDamage=damage;
        this.finalHealth=health;
    }
    public void attack(Stuff enemyStuff){
        if(enemyStuff.getClass().getSuperclass().getName().equals("org.stuff.Hero")){
            Hero hero=(Hero)enemyStuff;
            hero.health-=this.damage;
            this.health-=hero.damage;
        }else if(enemyStuff.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")){
            Minion minion=(Minion)enemyStuff;
            if(havePoisonous){
                minion.setHealth(0);
            }else {
                minion.health -= this.damage;
                this.health -= minion.damage;
            }
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
        if(this.haveShield){
            haveShield=false;
            return;
        }
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
    public void endTurnNotify(){

    }
}
