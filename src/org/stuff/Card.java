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
    public String description;
    public String specialFor;
    public String icon;
    public int rarity;
    public int used = 0;

    public Card(String name, int cost, int mana, String description, String specialFor, String icon, int rarity) {
        this.name = name;
        this.cost = cost;
        this.mana = mana;
        this.description = description;
        this.specialFor = specialFor;
        this.icon = icon;
        this.rarity = rarity;
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
