package org.stuff;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

public class Hero {
    public String name;
    public int damage;
    public int health = 30;
    public ArrayList<Card> HeroCards;
    public int mana;
    public String feature;
    public String icon;
    public int heroDefence=0;
    public String HeroPowerIcon;
    public Hero(String name, int damage, ArrayList<Card> HeroCards, int health, String feature, int mana, String icon, String HeroPowerIcon) {
        this.name = name;
        this.damage = damage;
        this.HeroCards = HeroCards;
        this.health = health;
        this.feature = feature;
        this.mana = mana;
        this.icon = icon;
        this.HeroPowerIcon=HeroPowerIcon;
    }
    public static ArrayList<Hero> heroSetter() throws IOException {
        ArrayList<Hero> heroes = new ArrayList<>();
        Scanner fileReader = new Scanner(new FileReader("Heros.txt"));
        while (fileReader.hasNext()) {
            Hero hero = new Gson().fromJson(fileReader.nextLine(), Hero.class);
            heroes.add(hero);
        }
        fileReader.close();
        return heroes;
    }
}

