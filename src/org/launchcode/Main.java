package org.launchcode;

import org.drawer.Drawer;
import org.stuff.Card;
import org.stuff.Hero;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Hero> Heros = new ArrayList<>();
        /*
        {
            Card polymorph = new Card("Polymorph", 4, 4, "convert-sheep", "convert an enemy minion to sheep", "MAGE", "images\\polymorph.png", 2);
            Card milad = new Card("milad", 3, 3, "hero-hp+5", "give your hero 5 health", "MAGE", "images\\milad.jpg", 3);
            ArrayList<Card> MAGEcards = new ArrayList<>();
            MAGEcards.add(polymorph);
            MAGEcards.add(milad);
            Hero MAGE = new Hero("MAGE", 0, MAGEcards, 30, "spell-2", 2, "images\\mage.png", "images\\magePower.png");
            //
            Card foad = new Card("foad", 2, 2, "take-minion", "take a minion of your enemy", "ROGUE", "images\\foad.jpg", 2);
            Card FriendlySmith = new Card("Friendly Smith", 1, 1, "draw a weapon", "draw a weapon for your hero", "ROGUE", "images\\friendlysmith.png", 2);
            ArrayList<Card> SmithCard = new ArrayList<>();
            SmithCard.add(FriendlySmith);
            SmithCard.add(foad);
            Hero ROGUE = new Hero("ROGUE", 1, SmithCard, 30, "specialCard-2", 3, "images\\rogue.png", "images\\roguePower.png");
            //
            Card emad = new Card("emad", 16, 5, 8, 3, "", "very powerfull minion", "WARLOCK", "images\\emad.jpg", 3);
            Card Dreadscale = new Card("Dreadscale", 8, 3, 4, 2, "per-damage1toall", "at the end of your turn deal 1 damage to all minions", "WARLOCK", "images\\dreadscale.png", 2);
            ArrayList<Card> WarlockCards = new ArrayList<>();
            WarlockCards.add(emad);
            WarlockCards.add(Dreadscale);
            Hero WARLOCK = new Hero("WARLOCK", 0, WarlockCards, 35, "improve minion", 2, "images\\warlock.jpg", "images\\warlockPower.png");
            //
            Card behrad = new Card("behrad", 5, 1, 3, 1, "+1damagepermap", "every turn its damage +1 damage", "PALADIN", "images\\behrad.jpg", 3);
            Card gnomishArmyKnife = new Card("gnomishArmyKnife", 5, 5, "give charge poisonous windfury life teal divideshield taunrt", "give charge poisonous windfury life teal divideshield taunrt", "PALADIN", "images\\gnomisharmyknife.png", 3);
            ArrayList<Card> paladinCards = new ArrayList<>();
            paladinCards.add(behrad);
            paladinCards.add(gnomishArmyKnife);
            Hero PALADIN = new Hero("PALADIN", 0, paladinCards, 30, "random a minion +1damage +1health", 2, "images\\paladin.jpg", "images\\paladinPower.jpg");
            //
            Card shahzad = new Card("shahzad", 3, 3, "give all minions +2 health", "give all minions +2 health", "PRIEST", "images\\shahzad.jpg", 2);
            Card highPriestAmet = new Card("highPriestAmet", 13, 4, 2, 7, "when summon a minion change its health to his health", "when summon a minion change its health to his health", "PRIEST", "images\\highpriestamet.png", 3);
            ArrayList<Card> priestCards = new ArrayList<>();
            priestCards.add(shahzad);
            paladinCards.add(highPriestAmet);
            Hero PRIEST = new Hero("PRIEST", 0, paladinCards, 30, "card for health work 2x", 2, "images\\priest.png", "images\\priestPower.jpg");
            //
            Card wolfrider = new Card("Wolfrider", 7, 3, 3, 1, "null", "charge", "All", "images\\wolfrider.png", 2);
            Card magmarager = new Card("MagmaRager", 9, 3, 5, 1, "", "", "All", "images\\magmarager.png", 2);
            Card ravagingGhoul = new Card("RavaginGhoul", 9, 3, 3, 3, "per-damage1toall", "deal one damage to all enemy minions", "All", "images\\ravagingghoul.png", 3);
            Card batterhead = new Card("BatterHead", 23, 8, 3, 12, "after killing may attack", "after killing a minion it may attack again", "All", "images\\batterhead.png", 4);
            Card fireball = new Card("FireBall", 4, 4, "5damage", "5 damage to target", "All", "images\\fireball.png", 2);
            Card loothoarder = new Card("LootHoarder", 5, 2, 2, 1, "draw1card", "Draw a card", "All", "images\\loothoarder.jpg", 3);
            Card CrazedAlchemist = new Card("CrazedAlchemist", 6, 2, 2, 2, "swap damage health", "swap the attack and health of a minion", "All", "images\\crazedalchemist.png", 2);
            Card AzureDrake = new Card("AzureDrake", 13, 5, 4, 4, "draw1card", "Draw a card", "All", "images\\azuredrake.png", 2);
            Card SilentKnight = new Card("SilentKnight", 7, 3, 2, 2, "make a shield", "make a shield", "All", "images\\silentknight.jpg", 2);
            Card raidleader = new Card("RaidLeader", 7, 3, 2, 2, "improve minion", "give a friendly minion 1 damage an 1 health", "All", "images\\raidleader.png", 2);
            Card bloodfenRaptor = new Card("BloodfenRaptor", 7, 2, 3, 2, "", "", "All", "images\\bloodfenraptor.png", 1);
            Card releaseraptors = new Card("ReleaseTheRaptors", 7, 7, "release3raptors", "release three raptors", "All", "images\\releasetheraptors.jpg", 2);
            Card voidwalker = new Card("VoidWalker", 5, 1, 1, 3, "", "", "All", "images\\voidwalker.png", 2);
            Card hellfire = new Card("HellFire", 4, 4, "damage3toall", "deal 3 damage to all minions", "All", "images\\hellfire.png", 2);
            Card sprint = new Card("Sprint", 5, 5, "draw 4 cards", "draw 4 cards", "All", "images\\sprint.jfif", 2);
            Card swarmOfLocusts = new Card("SwarmOfLocusts", 6, 6, "", "summon seven 1/1 locusts with rush", "All", "images\\swarmoflocusts.png", 2);
            Card pharaohsBlessing = new Card("PharaohsBlessing", 6, 6, "", "give a minion 4/4 divide shiled with taunt", "All", "images\\pharaohsblessing.png", 2);
            Card bookOfSpecters = new Card("BookOfSpecters", 2, 2, "", "draw 3 cards discard any spells drawn", "All", "images\\bookofspecters.png", 2);
            Card Sathrovarr = new Card("Sathrovarr", 19, 9, 5, 5, "", "choose a friendly minion and copy it to your deck battlefield and hand", "All", "images\\sathrovarr.png", 2);
            Card tombwarden = new Card("TmbWarden", 17, 8, 3, 6, "", "summon a copy of itself", "All", "images\\tombwarden.png", 2);
            Card securityRover = new Card("SecurityRover", 14, 6, 2, 6, "", "whenever this minion take damage summon a 2/3 Meck with taunt", "All", "images\\securityrover.png", 2);
            Card strengthInNmbers = new Card("StrengthInNumbers", 1, 1, "", "sidequest:spend 10 mana on a minion...reward:summon a minion from your deck", "All", "images\\strengthinnumbers.png", 2);
            Card learnDraconic = new Card("LearnDraconic", 1, 1, "", "sidequest: spend 8 mana on spells...reward: summon a 6/6 dragon", "All", "images\\learndraconic.png", 2);
            Card curioCollector = new Card("CurioCollector", 13, 5, 4, 4, "", "when ever you draw a card gain 1/1", "All", "images\\curiocollector.png", 2);
            Card heavyaxe = new Card("HeavyAxe", 1, 1, 1, 3, "images\\heavyaxe.png", 1);
            Card wickedKnife = new Card("WickedKnife", 1, 1, 1, 2, "images\\wickedknife.png", 1);
            Card gearBlade = new Card("GearBlade", 2, 2, 2, 3, "images\\gearblade.png", 1);
            //
            cards.add(polymorph);
            cards.add(milad);
            cards.add(foad);
            cards.add(FriendlySmith);
            cards.add(emad);
            cards.add(Dreadscale);
            cards.add(behrad);
            cards.add(gnomishArmyKnife);
            cards.add(shahzad);
            cards.add(highPriestAmet);
            cards.add(wolfrider);
            cards.add(magmarager);
            cards.add(ravagingGhoul);
            cards.add(sprint);
            cards.add(swarmOfLocusts);
            cards.add(pharaohsBlessing);
            cards.add(bookOfSpecters);
            cards.add(Sathrovarr);
            cards.add(tombwarden);
            cards.add(securityRover);
            cards.add(strengthInNmbers);
            cards.add(learnDraconic);
            cards.add(curioCollector);
            cards.add(heavyaxe);
            cards.add(wickedKnife);
            cards.add(gearBlade);
            cards.add(batterhead);
            cards.add(fireball);
            cards.add(loothoarder);
            cards.add(CrazedAlchemist);
            cards.add(AzureDrake);
            cards.add(SilentKnight);
            cards.add(raidleader);
            cards.add(bloodfenRaptor);
            cards.add(releaseraptors);
            cards.add(voidwalker);
            cards.add(hellfire);
            //
            Heros.add(MAGE);
            Heros.add(WARLOCK);
            Heros.add(ROGUE);
            Heros.add(PALADIN);
            Heros.add(PRIEST);
        }*/
        /*
        for (int i = 0; i < cards.size(); i++) {
            String fileName = "AllCards.txt";
            File myObj = new File(fileName);
            myObj.createNewFile();
            String data = new Gson().toJson(cards.get(i));
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(data + "\r\n");
            if(i<30){
                String fileName2 = "CurrentCards.txt";
                File myObj2 = new File(fileName2);
                myObj2.createNewFile();
                FileWriter fileWriter2 = new FileWriter(fileName2, true);
                fileWriter2.write(data + "\r\n");
                fileWriter2.close();
            }
            fileWriter.close();
        }
        for (int i = 0; i < Heros.size(); i++) {
            String fileName = "Heros.txt";
            File myObj = new File(fileName);
            myObj.createNewFile();
            String data = new Gson().toJson(Heros.get(i));
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(data + "\r\n");
            fileWriter.close();
        }*/
        Drawer d1=new Drawer();
        d1.firstQuestion();
    }

}
