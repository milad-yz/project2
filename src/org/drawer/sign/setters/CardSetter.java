package org.drawer.sign.setters;

import org.stuff.Card;
import org.stuff.cards.Minion;
import org.stuff.cards.QuestAndReward;
import org.stuff.cards.Spell;
import org.stuff.cards.Weapon;
import org.stuff.cards.minions.*;
import org.stuff.cards.questAndReward.*;
import org.stuff.cards.spell.*;
import org.stuff.cards.weapon.*;

import java.util.ArrayList;

public class CardSetter {
    public static ArrayList<Card> allCardsSetter() {
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(minion());
        allCards.addAll(spell());
        allCards.addAll(questAndReward());
        allCards.addAll(weapon());
        return allCards;
    }

    private static ArrayList<Weapon> weapon() {
        ArrayList<Weapon> weapons = new ArrayList<>();
        weapons.add(new GearBlade());
        weapons.add(new HeavyAxe());
        weapons.add(new WickedKnife());
        return weapons;
    }

    private static ArrayList<QuestAndReward> questAndReward() {
        ArrayList<QuestAndReward> questAndRewards = new ArrayList<>();
        questAndRewards.add(new LearnDraconic());
        questAndRewards.add(new StrengthInNumbers());
        return questAndRewards;
    }

    private static ArrayList<Spell> spell() {
        ArrayList<Spell> spells = new ArrayList<>();
        spells.add(new BookOfSpecters());
        spells.add(new FireBall());
        spells.add(new Foad());
        spells.add(new FriendlySmith());
        spells.add(new GnomishArmyKnife());
        spells.add(new HellFire());
        spells.add(new Milad());
        spells.add(new PharaohsBlessing());
        spells.add(new Polymorph());
        spells.add(new ReleaseTheRaptors());
        spells.add(new Shahzad());
        spells.add(new Sprint());
        spells.add(new SwarmOfLocusts());
        return spells;
    }

    private static ArrayList<Minion> minion() {
        ArrayList<Minion> minions = new ArrayList<>();
        minions.add(new AzureDrake());
        minions.add(new BatterHead());
        minions.add(new Behrad());
        minions.add(new BloodFenRaptor());
        minions.add(new CrazedAlchemist());
        minions.add(new CurioCollector());
        minions.add(new DreadScale());
        minions.add(new Emad());
        minions.add(new HighPriestAmet());
        minions.add(new LootHoarder());
        minions.add(new MagmaRager());
        minions.add(new RaidLeader());
        minions.add(new RavagingGhoul());
        minions.add(new Sathrovarr());
        minions.add(new SecurityRover());
        minions.add(new SilentKnight());
        minions.add(new TombWarden());
        minions.add(new VoidWalker());
        minions.add(new WolfRider());
        return minions;
    }

    public static ArrayList<Card> currentCardsSetter() {
        ArrayList<Card> currentCards = new ArrayList<>();
        currentCards.addAll(allCardsSetter());
        for (int i = 0; i < 10; i++) {
            int randomInt = (int) (Math.random() * (currentCards.size()));
            currentCards.remove(randomInt);
        }
        return currentCards;
    }

    public static ArrayList<Card> arrayCardCaster(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            cards.set(i,cardCaster(cards.get(i)));
        }
        return cards;
    }

    public static Card cardCaster(Card card) {
        switch (card.name) {
            case "AzureDrake":
                return new AzureDrake();
            case "BatterHead":
                return new BatterHead();
            case "Behrad":
                return new Behrad();
            case "BloodFenRaptor":
                return new BloodFenRaptor();
            case "CrazedAlchemist":
                return new CrazedAlchemist();
            case "CurioCollector":
                return new CurioCollector();
            case "DreadScale":
                return new DreadScale();
            case "Emad":
                return new Emad();
            case "HighPriestAmet":
                return new HighPriestAmet();
            case "LootHoarder":
                return new LootHoarder();
            case "MagmaRager":
                return new MagmaRager();
            case "RaidLeader":
                return new RaidLeader();
            case "RavagingGhoul":
                return new RavagingGhoul();
            case "Sathrovarr":
                return new Sathrovarr();
            case "SecurityRover":
                return new SecurityRover();
            case "SilentKnight":
                return new SilentKnight();
            case "TombWarden":
                return new TombWarden();
            case "VoidWalker":
                return new VoidWalker();
            case "WolfRider":
                return new WolfRider();
            case "BookOfSpecters":
                return new BookOfSpecters();
            case "FireBall":
                return new FireBall();
            case "Foad":
                return new Foad();
            case "FriendlySmith":
                return new FriendlySmith();
            case "GnomishArmyKnife":
                return new GnomishArmyKnife();
            case "HellFire":
                return new HellFire();
            case "Milad":
                return new Milad();
            case "PharaohsBlessing":
                return new PharaohsBlessing();
            case "Polymorph":
                return new Polymorph();
            case "ReleaseTheRaptors":
                return new ReleaseTheRaptors();
            case "Shahzad":
                return new Shahzad();
            case "Sprint":
                return new Sprint();
            case "SwarmOfLocusts":
                return new SwarmOfLocusts();
            case "LearnDraconic":
                return new LearnDraconic();
            case "StrengthInNumbers":
                return new StrengthInNumbers();
            case "GearBlade":
                return new GearBlade();
            case "HeavyAxe":
                return new HeavyAxe();
            case "WickedKnife":
                return new WickedKnife();
        }
        return null;
    }
}
