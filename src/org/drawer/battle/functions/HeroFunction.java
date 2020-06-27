package org.drawer.battle.functions;

import org.drawer.battle.Battle;
import org.stuff.Hero;
import org.stuff.Stuff;
import org.stuff.cards.Minion;
import org.stuff.cards.minions.SilverHandRecruit;

import java.util.ArrayList;
import java.util.Random;

public class HeroFunction {
    public static void heroAttack(Battle battle){
        battle.whoseNotTurn().hero.health -= battle.whoseTurn().hero.damage;
        battle.whoseTurn().hero.health -= battle.whoseNotTurn().hero.damage;
        battle.whoseTurn().hero.rush -= 1;
        battle.whoseTurn().setN(1);
        battle.whoseNotTurn().setN(3);
        battle.semaphoreNotify();
    }

    public static void magePower(Battle battle) {
        battle.whoseTurn().setN(5);
        battle.whoseNotTurn().setM(1);
        battle.whoseNotTurn().setN(11);
        battle.semaphoreNotify();
    }
    public static void magePowerAction(Battle battle, Stuff stuff) {
        if(stuff.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")) {
            Minion minion=(Minion) stuff;
            minion.setHealth(minion.getHealth() - 1);
        }
        else if(stuff.getClass().getSuperclass().getName().equals("org.stuff.Hero")) {
            battle.whoseNotTurn().hero.health -= 1;
        }
        battle.whoseTurn().setMana(battle.whoseTurn().getMana()-battle.whoseTurn().hero.mana);
        battle.whoseTurn().hero.heroPowerRush -= 1;
        battle.whoseTurn().setN(1);
        battle.whoseNotTurn().setN(3);
        battle.semaphoreNotify();
    }

    public static void warlockPower(Battle battle) {
        battle.whoseTurn().hero.heroPowerRush-=1;
        battle.whoseTurn().setMana(battle.whoseTurn().getMana()-battle.whoseTurn().hero.mana);
        if(!BattleCryFunction.allNull(battle.whoseTurn().battleCards)&&Math.random()>=0.5) {
            Random random = new Random();
            ArrayList<Integer> haveMinions = new ArrayList<>();
            for (int i = 0; i < battle.whoseTurn().battleCards.size(); i++) {
                if (battle.whoseTurn().battleCards.get(i) != null)
                    haveMinions.add(i);
            }
            int whichMinion = haveMinions.get(random.nextInt(haveMinions.size()));
            battle.whoseTurn().battleCards.get(whichMinion).setHealth(battle.whoseTurn().battleCards.get(whichMinion).getHealth()+1);
            battle.whoseTurn().battleCards.get(whichMinion).setDamage(battle.whoseTurn().battleCards.get(whichMinion).getDamage()+1);
        }else{
            BattleCryFunction.drawCards(battle.whoseTurn(),battle,1);
        }
    }

    public static void priestPower(Battle battle) {
        battle.whoseTurn().setN(7);
        battle.whoseTurn().setM(4);
        battle.whoseNotTurn().setN(3);
        battle.semaphoreNotify();
    }

    public static void priestPowerAction(Battle battle, Stuff stuff) {
        battle.whoseTurn().setMana(battle.whoseTurn().getMana()-battle.whoseTurn().hero.mana);
        battle.whoseTurn().hero.heroPowerRush -= 1;
        battle.whoseTurn().setN(1);
        battle.whoseNotTurn().setN(3);
        if(stuff.getClass().getSuperclass().getName().equals("org.stuff.Hero")){
            Hero hero=(Hero)stuff;
            if(hero.health+4>=30)
                hero.health=30;
            else
                hero.health+=4;
        }else if(stuff.getClass().getSuperclass().getName().equals("org.stuff.cards.Minion")){
            Minion minion=(Minion) stuff;
            if(minion.getHealth()+4>=minion.finalHealth)
                minion.setHealth(minion.finalHealth);
            else
                minion.setHealth(minion.getHealth()+4);
        }
        battle.semaphoreNotify();
    }

    public static void stealFromEnemyDeck(Battle battle) {
        battle.whoseTurn().setMana(battle.whoseTurn().getMana()-battle.whoseTurn().hero.mana);
        battle.whoseTurn().hero.heroPowerRush -= 1;
        Random random =new Random();
        int randomNumber=random.nextInt(battle.whoseNotTurn().deck.deckCards.size());
        battle.whoseTurn().handCards.add(battle.whoseNotTurn().deck.deckCards.get(randomNumber));
        battle.whoseNotTurn().deck.deckCards.remove(randomNumber);
        battle.semaphoreNotify();
    }

    public static void paladinPower(Battle battle) {
        battle.whoseTurn().setMana(battle.whoseTurn().getMana()-battle.whoseTurn().hero.mana);
        battle.whoseTurn().hero.heroPowerRush -= 1;
        BattleSpellFunctions.summon(battle,battle.whoseNotTurn(),new SilverHandRecruit(),2);
        battle.semaphoreNotify();
    }
}
