package org.drawer.sign.setters;

import org.stuff.Hero;
import org.stuff.heros.*;

import java.util.ArrayList;

public class HeroSetter {
    public static ArrayList<Hero> heroSetter(){
        ArrayList<Hero> heros=new ArrayList<>();
        heros.add(new Mage());heros.add(new Rogue());heros.add(new Paladin());heros.add(new Priest());heros.add(new Warlock());
        return heros;
    }
    public static Hero heroCaster(Hero hero){
        switch (hero.name){
            case "MAGE":
                return new Mage();
            case "PALADIN":
                return new Paladin();
            case "PRIEST":
                return new Priest();
            case "ROGUE":
                return new Rogue();
            case "WARLOCK":
                return new Warlock();
        }
        return null;
    }
}
