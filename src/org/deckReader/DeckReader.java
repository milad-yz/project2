package org.deckReader;

import com.google.gson.Gson;
import org.drawer.sign.setters.CardSetter;
import org.stuff.Card;
import org.stuff.cards.QuestAndReward;
import org.stuff.cards.minions.*;
import org.stuff.cards.questAndReward.LearnDraconic;
import org.stuff.cards.questAndReward.StrengthInNumbers;
import org.stuff.cards.spell.*;
import org.stuff.cards.weapon.GearBlade;
import org.stuff.cards.weapon.HeavyAxe;
import org.stuff.cards.weapon.WickedKnife;

import java.io.*;
import java.util.ArrayList;

public class DeckReader {
    private DeckWriter deckWriter;
    private Card rewardCard;

    public DeckReader() throws IOException {
        init();
    }

    private void init() throws IOException {
        File inputFile = new File("DeckReader.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            deckWriter = new Gson().fromJson(trimmedLine, DeckWriter.class);
        }
        reader.close();
    }

    public ArrayList<Card> friendCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < deckWriter.friend.size(); i++) {
            String cardName = deckWriter.friend.get(i);
            if (cardName.contains("-")) {
                QuestAndReward questAndReward= (QuestAndReward) cardFounder(cardName.substring(0, cardName.indexOf("-")));
                questAndReward.rewardCard=cardFounder(cardName.substring(cardName.indexOf(":")+1,cardName.length()));
                cards.add(questAndReward);
            } else
                cards.add(cardFounder(cardName));
        }
        return cards;
    }

    public ArrayList<Card> enemyCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < deckWriter.enemy.size(); i++) {
            String cardName = deckWriter.enemy.get(i);
            if (cardName.contains("-")) {
                QuestAndReward questAndReward= (QuestAndReward) cardFounder(cardName.substring(0, cardName.indexOf("-")));
                questAndReward.rewardCard=cardFounder(cardName.substring(cardName.indexOf(":")+1,cardName.length()));
                cards.add(questAndReward);
            } else
                cards.add(cardFounder(cardName));
        }
        return cards;
    }

    private Card cardFounder(String cardName) {
        switch (cardName) {
            case "AzureDrake":
                return new AzureDrake();
            case "DraconicEmissary":
                return new DraconicEmissary();
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
            case "Duel!":
                return new Duel();
            case "Overflow":
                return new Overflow();
        }
        return null;
    }
}
