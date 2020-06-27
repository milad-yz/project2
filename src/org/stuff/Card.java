package org.stuff;

public class Card extends Stuff{
    public int cost;
    public String description;
    public String specialFor;
    public int rarity;
    public int used = 0;
    public final int finalMana;

    public Card(String name, int cost, int mana, String description, String specialFor, String icon, int rarity) {
        super(name,icon,mana);
        this.cost = cost;
        this.description = description;
        this.specialFor = specialFor;
        this.rarity = rarity;
        this.finalMana = mana;
    }
    public Card getClone(){
        System.out.println(this.getClass().getSuperclass().getName());
        return new Card(this.name,this.cost,this.mana,this.description,this.specialFor,this.icon,this.rarity);
    }

    @Override
    public String toString() {
        return "Card{" +
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

    public void coming2battle() {

    }

}
