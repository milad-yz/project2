package org.stuff;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

public class Hero {
    public String name;
    public int damage;
    public int health ;
    public ArrayList<Card> HeroCards;
    public int mana;
    public String icon;
    public String HeroPowerIcon;
    public Hero(String name, int damage, int health, int mana, String icon, String HeroPowerIcon,Card card1,Card card2) {
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.mana = mana;
        this.icon = icon;
        this.HeroPowerIcon=HeroPowerIcon;
        HeroCards=new ArrayList<>();
        HeroCards.add(card1);
        HeroCards.add(card2);
    }
}

