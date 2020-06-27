package org.player;


import com.google.gson.Gson;
import org.stuff.Card;
import org.stuff.Deck;
import org.stuff.Hero;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String UserName;
    private String PassWord;
    public int diamonds = 100;
    public ArrayList<Card> currentCards;
    public ArrayList<Card> allCards;
    public ArrayList<Hero> heros;
    public ArrayList<Deck> playerDeck;
    public Deck currentDeck;
    public Player(String userName, String passWord, ArrayList<Card> currentCards, ArrayList<Card> allCards, ArrayList<Hero> heros, ArrayList<Deck> playerDeck, Deck currentDeck) {
        this.UserName = userName;
        this.PassWord = passWord;
        this.currentCards = currentCards;
        this.allCards = allCards;
        this.heros = heros;
        this.playerDeck = playerDeck;
        this.currentDeck = currentDeck;
    }

    public void sellUpdate(Card card) {
        for (int i = 0; i < playerDeck.size(); i++) {
            if (playerDeck.get(i).deckCards.contains(card))
                playerDeck.get(i).deckCards.remove(card);
        }
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void deletePlayer(Player p) throws IOException {
        FileWriter fileWriter = new FileWriter("TempFile.txt");
        Scanner Reader = new Scanner(new FileReader("Players.txt"));

        while (Reader.hasNext()) {
            String data = Reader.nextLine();
            Player p2 = new Gson().fromJson(data, Player.class);
            if (p.getUserName().equals(p2.getUserName())) continue;
            else {
                fileWriter.write(data + "\r\n");
            }
            fileWriter.flush();
        }
        Reader.close();
        fileWriter.close();
        File tempFile = new File("TempFile.txt");
        File file = new File("Players.txt");
        file.delete();
        tempFile.renameTo(file);
    }

    public void update() throws IOException {
        File inputFile = new File("Players.txt");
        File tempFile = new File("TempFilePlayer.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            Player p2 = new Gson().fromJson(trimmedLine, Player.class);
            if (p2.getUserName().equals(this.getUserName())) {
                writer.write(new Gson().toJson(this) + System.getProperty("line.separator"));
                continue;
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
    }

}
