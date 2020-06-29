package org.launchcode;

import org.deckReader.DeckReader;
import org.drawer.sign.FirstQuestion;
import org.stuff.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        FirstQuestion.getInstance();
//        File inputFile = new File("DeckReader.txt");
//        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
//        writer.write(new Gson().toJson(new DeckWriter(friendSetter(),enemySetter())) + System.getProperty("line.separator"));
//        writer.flush();
//        writer.close();
//        DeckReader deckReader=new DeckReader();
//        System.out.println(deckReader.enemyCards());
    }

    private static ArrayList<String> friendSetter() {
        ArrayList<String> friend = new ArrayList<>();
        friend.addAll(Arrays.asList("LearnDraconic", "BookOfSpecters", "BloodFenRaptor", "CurioCollector", "LootHoarder", "FireBall", "GnomishArmyKnife"));
        friend.addAll(Arrays.asList("ReleaseTheRaptors", "FriendlySmith", "CurioCollector", "PharaohsBlessing", "Polymorph", "Sprint", "Milad"));
        friend.addAll(Arrays.asList("AzureDrake", "BatterHead", "CrazedAlchemist", "DraconicEmissary", "MagmaRager", "RaidLeader", "RavagingGhoul", "Sathrovarr"));
        return friend;
    }

    private static ArrayList<String> enemySetter() {
        ArrayList<String> enemy = new ArrayList<>();
        enemy.addAll(Arrays.asList("StrengthInNumbers->reward:SecurityRover", "DreadScale", "GearBlade", "SwarmOfLocusts", "TombWarden", "Sathrovarr"));
        enemy.addAll(Arrays.asList("BatterHead", "BloodFenRaptor", "CurioCollector", "RavagingGhoul", "MagmaRager", "SecurityRover"));
        return enemy;
    }
}
