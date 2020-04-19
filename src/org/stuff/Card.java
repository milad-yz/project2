package org.stuff;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Card {
    public String name;
    public int cost;
    public int mana;
    public int health;
    public int damage;
    public String feature;
    public String description;
    public String specialFor;
    public int isminion;
    public String icon;
    public int rarity;
    public int useage = 0;
    public int number = 1;

    public Card(String name, int cost, int mana, int damage, int health, String feature, String description, String specialFor, String icon, int rarity) {
        this.name = name;
        this.mana = mana;
        this.health = health;
        this.damage = damage;
        this.feature = feature;
        this.description = description;
        this.specialFor = specialFor;
        this.cost = cost;
        this.icon = icon;
        isminion = 1;
        this.rarity = rarity;
    }

    public Card(String name, int cost, int mana, String feature, String description, String specialFor, String icon, int rarity) {
        this.feature = feature;
        this.mana = mana;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.specialFor = specialFor;
        this.icon = icon;
        isminion = 2;
        this.rarity = rarity;
    }

    public Card(String name, int cost, int mana, int damage, int health, String icon, int rarity) {
        this.name = name;
        this.cost = cost;
        this.mana = mana;
        this.damage = damage;
        this.health = health;
        this.icon = icon;
        this.rarity = rarity;
        isminion = 3;
        specialFor = "All";
        description = "";
    }

    public static ArrayList<Card> currentCardsSetter() throws IOException {
        ArrayList<Card> cards = new ArrayList<>();
        File myObj = new File("CurrentCards.txt");
        myObj.createNewFile();
        Scanner fileReader = new Scanner(new FileReader("CurrentCards.txt"));
        while (fileReader.hasNext()) {
            Card card = new Gson().fromJson(fileReader.nextLine(), Card.class);
            cards.add(card);
        }
        fileReader.close();
        return cards;
    }

    public static ArrayList<Card> allCardsSetter() throws IOException {
        File myObj = new File("AllCards.txt");
        myObj.createNewFile();
        ArrayList<Card> cards = new ArrayList<>();
        Scanner fileReader = new Scanner(new FileReader("AllCards.txt"));
        while (fileReader.hasNext()) {
            Card card = new Gson().fromJson(fileReader.nextLine(), Card.class);
            cards.add(card);
        }
        fileReader.close();
        return cards;
    }

    public void firstInBattle() {
    }

    public void duringBattle() {
    }
}
