package org.stuff;

import org.stuff.cards.Minion;

import java.util.ArrayList;

public class Hero extends Stuff{
    public int damage;
    public int health;
    public ArrayList<Card> HeroCards;
    public String HeroPowerIcon;
    public int durability =0;
    public int rush=1;
    public int heroPowerRush=1;
    public Hero(String name, int damage, int health, int mana, String icon, String HeroPowerIcon,Card card1,Card card2) {
        super(name,icon,mana);
        this.damage = damage;
        this.health = health;
        this.HeroPowerIcon=HeroPowerIcon;
        HeroCards=new ArrayList<>();
        HeroCards.add(card1);
        HeroCards.add(card2);
    }
    public Hero getClone(){
        return new Hero(this.name,this.damage,this.health,this.mana,this.icon,this.HeroPowerIcon,this.HeroCards.get(0).getClone(),this.HeroCards.get(1).getClone());
    }

}

