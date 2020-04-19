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
    public int diamonds = 10;
    public ArrayList<Card> currentcards;
    public ArrayList<Card> allcards;
    public ArrayList<Hero> heros;
    public ArrayList<Deck> playerDeck;
    public Deck currentDeck;
    public Player(String userName, String passWord, ArrayList<Card> currentcards, ArrayList<Card> allcards, ArrayList<Hero> heros, ArrayList<Deck> playerDeck, Deck currentDeck) {
        this.UserName = userName;
        this.PassWord = passWord;
        this.currentcards = currentcards;
        this.allcards = allcards;
        this.heros = heros;
        this.playerDeck = playerDeck;
        this.currentDeck = currentDeck;
    }

    public void getCurrentCards() {
        System.out.println("heres are your currentCards:");
        for (int i = 0; i < currentcards.size(); i++) {
            System.out.println(currentcards.get(i).name);
        }
    }

    public void getAllCards() {
        System.out.println("heres are your total Cards:");
        for (int i = 0; i < allcards.size(); i++) {
            System.out.println(allcards.get(i).name);
        }
    }

    public void getheros() {
        for (int i = 0; i < heros.size(); i++) {
            System.out.println(heros.get(i).name);
        }
    }

    public void store() {
        for (int i = 0; i < allcards.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < currentcards.size(); j++) {
                if (allcards.get(i).name.equals(currentcards.get(j).name))
                    flag = false;
            }
            if (flag)
                System.out.println(new Gson().toJson(allcards.get(i)));
        }
    }

    public void buyUpdate(Card card) {
        for (int i = 0; i < playerDeck.size(); i++) {
            if (card.name.equals("All") || card.specialFor.equals(playerDeck.get(i).deckHero.name))
                playerDeck.get(i).deckCards.add(card);
        }
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
        File tempfile = new File("TempFile.txt");
        File file = new File("Players.txt");
        file.delete();
        tempfile.renameTo(file);
    }

    public void update(Player p) throws IOException {
        File inputFile = new File("Players.txt");
        File tempFile = new File("TempFilePlayer.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            Player p2 = new Gson().fromJson(trimmedLine, Player.class);
            if (p2.getUserName().equals(p.getUserName())) {
                writer.write(new Gson().toJson(p) + System.getProperty("line.separator"));
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
