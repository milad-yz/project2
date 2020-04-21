package org.drawer;

import com.google.gson.Gson;
import org.fileWorks.FileWorks;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;
import org.stuff.Deck;
import org.stuff.Hero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Drawer {

    private JFrame frame = new JFrame("hearthStone");
    Player p = null;

    Drawer(Player p) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setLayout(null);
        this.p = p;
    }

    public Drawer() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setLayout(null);
    }

    public void firstQuestion() {
        JPanel firstQuestionPanel = new JPanel();
        firstQuestionPanel.setLayout(null);
        firstQuestionPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel massage = new JLabel("already have an account?");
        massage.setBounds(400, 50, 200, 100);
        firstQuestionPanel.add(massage);
        //
        JButton yes = new JButton("YES");
        yes.setBounds(500, 150, 60, 40);
        firstQuestionPanel.add(yes);
        yes.addActionListener(e -> {
            frame.remove(firstQuestionPanel);
            SingIn();
        });
        //
        JButton no = new JButton("NO");
        no.setBounds(500, 250, 60, 40);
        firstQuestionPanel.add(no);
        no.addActionListener(e -> {
            frame.remove(firstQuestionPanel);
            SingUp();
        });
        frame.add(firstQuestionPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void SingIn() {
        JPanel SingInPanel = new JPanel();
        SingInPanel.setLayout(null);
        SingInPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel SingInRoom = new JLabel("Sing In Room:");
        SingInRoom.setBounds(500, 0, 100, 30);
        SingInPanel.add(SingInRoom);
        //
        JLabel userlabel = new JLabel("Username:");
        userlabel.setBounds(450, 50, 100, 30);
        SingInPanel.add(userlabel);
        //
        JLabel passlabel = new JLabel("Password:");
        passlabel.setBounds(450, 150, 100, 30);
        SingInPanel.add(passlabel);
        //
        JTextField usernametext = new JTextField();
        usernametext.setBounds(550, 50, 100, 30);
        SingInPanel.add(usernametext);
        //
        JPasswordField passwordtext = new JPasswordField();
        passwordtext.setBounds(550, 150, 100, 30);
        SingInPanel.add(passwordtext);
        //
        JButton loginButton = new JButton("Sing In");
        loginButton.setBounds(650, 250, 100, 30);
        SingInPanel.add(loginButton);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 250, 100, 30);
        SingInPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(SingInPanel);
            firstQuestion();
        });
        //
        frame.add(SingInPanel);
        frame.repaint();
        frame.revalidate();
        loginButton.addActionListener(e -> {
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(new FileReader("Players.txt"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            String UN = usernametext.getText();
            String PW = passwordtext.getText();
            boolean c = false;
            while (fileReader.hasNext()) {
                Player p2 = new Gson().fromJson(fileReader.nextLine(), Player.class);
                if (UN.equals(p2.getUserName()) && PW.equals(p2.getPassWord())) {
                    fileReader.close();
                    c = true;
                    p = p2;
                    break;
                }
            }
            if (c) {
                try {
                    login.body(UN, "signIn", "ap2020");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.remove(SingInPanel);
                Enter();
            } else {
                JOptionPane.showMessageDialog(frame, "Sorry .... wrong username or password .... please try again", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void SingUp() {
        JPanel SingUpPanel = new JPanel();
        SingUpPanel.setLayout(null);
        SingUpPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel SingUpRoom = new JLabel("Sing Up Room:");
        SingUpRoom.setBounds(500, 0, 100, 30);
        SingUpPanel.add(SingUpRoom);
        //
        JLabel userlabel = new JLabel("Username:");
        userlabel.setBounds(450, 50, 100, 30);
        SingUpPanel.add(userlabel);
        //
        JLabel passlabel = new JLabel("Password:");
        passlabel.setBounds(450, 150, 100, 30);
        SingUpPanel.add(passlabel);
        //
        JTextField usernametext = new JTextField();
        usernametext.setBounds(550, 50, 100, 30);
        SingUpPanel.add(usernametext);
        //
        JPasswordField passwordtext = new JPasswordField();
        passwordtext.setBounds(550, 150, 100, 30);
        SingUpPanel.add(passwordtext);
        //
        JButton loginButton = new JButton("Sing Up");
        loginButton.setBounds(650, 250, 100, 30);
        SingUpPanel.add(loginButton);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 250, 100, 30);
        SingUpPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(SingUpPanel);
            firstQuestion();
        });
        //
        frame.add(SingUpPanel);
        frame.repaint();
        frame.revalidate();
        loginButton.addActionListener(e -> {
            String UN = usernametext.getText();
            String PW = passwordtext.getText();
            try {
                if (FileWorks.twoEqualsUserName("Players.txt", UN)) {
                    JOptionPane.showMessageDialog(frame, "we have the same username please try another username", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ArrayList<Hero> heros = Hero.heroSetter();
                    ArrayList<Card> currentCards = Card.currentCardsSetter();
                    ArrayList<Card> allCard = Card.allCardsSetter();
                    ArrayList<Deck> playerdeck = Deck.DeckSetter(heros, currentCards);
                    p = new Player(UN, PW, currentCards, allCard, heros, playerdeck, playerdeck.get(0));
                    FileWorks.filewrite("Players.txt", p);
                    login.createLog(UN, PW);
                    frame.remove(SingUpPanel);
                    Enter();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void Enter() {
        JPanel EnterPanel = new JPanel();
        EnterPanel.setLayout(null);
        EnterPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel EnterRoom = new JLabel("Menu:");
        EnterRoom.setBounds(500, 0, 100, 30);
        EnterPanel.add(EnterRoom);
        //
        JButton PlayButton = new JButton("Play");
        PlayButton.setBounds(500, 80, 100, 30);
        EnterPanel.add(PlayButton);
        PlayButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "play", "wants to play");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                play();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton ShopButton = new JButton("Shop");
        ShopButton.setBounds(500, 160, 100, 30);
        EnterPanel.add(ShopButton);
        ShopButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "shop", "went to the shop");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            shop();
        });
        //
        JButton StatusButton = new JButton("Status");
        StatusButton.setBounds(500, 240, 100, 30);
        EnterPanel.add(StatusButton);
        StatusButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "Status", "went to status");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                status();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton CollectionButton = new JButton("Collection");
        CollectionButton.setBounds(500, 320, 100, 30);
        EnterPanel.add(CollectionButton);
        CollectionButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "Collection", "went to Collection");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                collection();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        //
        JButton SettingButton = new JButton("Setting");
        SettingButton.setBounds(500, 400, 100, 30);
        EnterPanel.add(SettingButton);
        SettingButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "Setting", "went to Setting");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            setting();
        });
        //
        JButton signoutButton = new JButton("Sing out");
        signoutButton.setBounds(500, 480, 100, 30);
        EnterPanel.add(signoutButton);
        signoutButton.addActionListener(e -> {
            frame.remove(EnterPanel);
            try {
                login.body(p.getUserName(), "exit", "exit");
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            firstQuestion();
        });
        //
        frame.add(EnterPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void play() throws IOException {
        Battle b1;
        if (p.currentDeck == null) {
            JOptionPane.showMessageDialog(frame, "You have no current deck please first choose your main deck next come and play", "null deck", JOptionPane.INFORMATION_MESSAGE);
            collection();
        } else {
            for (int i = 0; i < p.currentDeck.deckCards.size(); i++) {
                p.currentDeck.deckCards.get(i).useage++;
            }
            p.update(p);
            frame.setVisible(false);
            b1 = new Battle(p);
            b1.passiveInfo();
        }

    }

    private void shop() {
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(null);
        shopPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel shoplabel = new JLabel("what do you want?");
        shoplabel.setBounds(500, 0, 200, 30);
        shopPanel.add(shoplabel);
        //
        JButton buyButton = new JButton("buy");
        buyButton.setBounds(500, 100, 100, 30);
        shopPanel.add(buyButton);
        buyButton.addActionListener(e -> {
            frame.remove(shopPanel);
            try {
                login.body(p.getUserName(), "buy room", "went to buy room");
                buy("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton sellButton = new JButton("sell");
        sellButton.setBounds(500, 200, 100, 30);
        shopPanel.add(sellButton);
        sellButton.addActionListener(e -> {
            frame.remove(shopPanel);
            try {
                login.body(p.getUserName(), "sell room", "went to sell room");
                sell("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 300, 100, 30);
        shopPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(shopPanel);
            try {
                login.body(p.getUserName(), "back button", "went to menu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Enter();
        });
        //
        frame.add(shopPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void buy(String massage) throws IOException {
        login.body(p.getUserName(), "buyroom", "went to buy room");
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(null);
        buyPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel massagelabel = new JLabel(massage);
        massagelabel.setBounds(500, 600, 400, 30);
        massagelabel.setForeground(Color.red);
        buyPanel.add(massagelabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        buyPanel.add(backButton);
        backButton.addActionListener(e -> {
            try {
                login.body(p.getUserName(), "back button", "went to shop");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.remove(buyPanel);
            shop();
        });
        //
        JLabel buylabel = new JLabel("your diamond is = " + p.diamonds + " and cards that you can buy:");
        buylabel.setBounds(500, 0, 400, 30);
        buyPanel.add(buylabel);
        //
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        //
        int j = 0;
        for (int i = 0; i < p.allcards.size(); i++) {
            boolean flag = true;
            for (int k = 0; k < p.currentcards.size(); k++) {
                if (p.allcards.get(i).name.equals(p.currentcards.get(k).name))
                    flag = false;
            }
            if (flag) {
                BufferedImage myPicture = ImageIO.read(new File(p.allcards.get(i).icon));
                JButton buyButton = new JButton();
                buyButton.setBounds((j % 6) * 150 + 50, (j / 6) * 200 + 100, 80, 100);
                buyButton.setIcon(new ImageIcon(myPicture));
                temp.add(buyButton);
                int finalI = i;
                buyButton.addActionListener(e -> {
                    frame.remove(buyPanel);
                    try {
                        login.body(p.getUserName(), "card show", "want to buy" + p.allcards.get(finalI).name);
                        buyCardShow(p.allcards.get(finalI), "", 1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                j++;
            }
        }
        //
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(50, 100, 850, 400);
        buyPanel.add(scrollableTextArea);
        //
        frame.add(buyPanel);
        frame.repaint();
        frame.revalidate();
    }

    public void buyCardShow(Card card, String massage, int n) throws IOException {
        JPanel buyCardShowPanel = new JPanel();
        buyCardShowPanel.setLayout(null);
        buyCardShowPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel buylabel = new JLabel("your diamond is = " + p.diamonds);
        buylabel.setBounds(500, 0, 400, 30);
        buyCardShowPanel.add(buylabel);
        //
        JLabel massagelabel = new JLabel(massage);
        massagelabel.setBounds(500, 600, 400, 30);
        massagelabel.setForeground(Color.red);
        buyCardShowPanel.add(massagelabel);
        //
        JLabel information1 = new JLabel("mana: " + card.mana + " damage: " + card.damage + " health: " + card.health);
        information1.setBounds(500, 200, 500, 100);
        information1.setFont(new Font("serif", Font.BOLD, 15));
        buyCardShowPanel.add(information1);
        //
        JLabel information2 = new JLabel("description: " + card.description);
        information2.setBounds(500, 300, 600, 100);
        information2.setFont(new Font("serif", Font.BOLD, 15));
        buyCardShowPanel.add(information2);
        //
        JLabel information3 = new JLabel("special for: " + card.specialFor + " rarity: " + card.rarity);
        information3.setBounds(500, 400, 600, 100);
        information3.setFont(new Font("serif", Font.BOLD, 15));
        buyCardShowPanel.add(information3);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        buyCardShowPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(buyCardShowPanel);
            if (n == 1) {
                try {
                    login.body(p.getUserName(), "back button", "went to buy room");
                    buy("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (n == 2) {
                try {
                    login.body(p.getUserName(), "back button", "went to card room");
                    Collection c1 = new Collection(p);
                    p.update(p);
                    frame.setVisible(false);
                    c1.cards("", 20, 1, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //
        BufferedImage myPicture = ImageIO.read(new File(card.icon));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(20, 20, 150, 200);
        buyCardShowPanel.add(picLabel);
        //
        JButton buyButton = new JButton("buy: " + card.name + " for:" + card.cost);
        buyButton.setBounds(650, 630, 200, 30);
        buyCardShowPanel.add(buyButton);
        buyButton.addActionListener(e -> {
            frame.remove(buyCardShowPanel);
            if (p.diamonds >= card.cost) {
                p.diamonds -= card.cost;
                p.currentcards.add(card);
                try {
                    login.body(p.getUserName(), card.name, "have bought this card");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    buyCardShow(card, "you have bought this card successfully", n);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    buyCardShow(card, "sorry you dont have enough diamonds", n);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(buyCardShowPanel);
        frame.repaint();
        frame.revalidate();
    }

    public void sellCardShow(Card card, String massage, int n) throws IOException {
        JPanel sellCardShowPanel = new JPanel();
        sellCardShowPanel.setLayout(null);
        sellCardShowPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel buylabel = new JLabel("your diamond is = " + p.diamonds);
        buylabel.setBounds(500, 0, 400, 30);
        sellCardShowPanel.add(buylabel);
        //
        JLabel massagelabel = new JLabel(massage);
        massagelabel.setBounds(500, 600, 400, 30);
        massagelabel.setForeground(Color.red);
        sellCardShowPanel.add(massagelabel);
        //
        JLabel information1 = new JLabel("mana: " + card.mana + " damage: " + card.damage + " health: " + card.health);
        information1.setBounds(500, 200, 500, 100);
        information1.setFont(new Font("serif", Font.BOLD, 15));
        sellCardShowPanel.add(information1);
        //
        JLabel information2 = new JLabel("description: " + card.description);
        information2.setBounds(500, 300, 600, 100);
        information2.setFont(new Font("serif", Font.BOLD, 15));
        sellCardShowPanel.add(information2);
        //
        JLabel information3 = new JLabel("special for: " + card.specialFor + " rarity: " + card.rarity);
        information3.setBounds(500, 400, 600, 100);
        information3.setFont(new Font("serif", Font.BOLD, 15));
        sellCardShowPanel.add(information3);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        sellCardShowPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(sellCardShowPanel);
            if (n == 1) {
                try {
                    login.body(p.getUserName(), "back button", "went to sell room");
                    sell("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (n == 2) {
                try {
                    login.body(p.getUserName(), "back button", "went to card room");
                    p.update(p);
                    Collection c1 = new Collection(p);
                    frame.setVisible(false);
                    c1.cards("", 20, 1, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //
        BufferedImage myPicture = ImageIO.read(new File(card.icon));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(20, 20, 150, 200);
        sellCardShowPanel.add(picLabel);
        //
        JButton buyButton = new JButton("sell: " + card.name + " for:" + card.cost / 2);
        buyButton.setBounds(650, 630, 200, 30);
        sellCardShowPanel.add(buyButton);
        buyButton.addActionListener(e -> {
            frame.remove(sellCardShowPanel);
            try {
                login.body(p.getUserName(), card.name, "have sold this card");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            p.diamonds += card.cost / 2;
            p.currentcards.remove(card);
            p.sellUpdate(card);
            try {
                sellCardShow(card, "you have sold this card successfully", n);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(sellCardShowPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void sell(String massage) throws IOException {
        JPanel sellPanel = new JPanel();
        sellPanel.setLayout(null);
        sellPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel massagelabel = new JLabel(massage);
        massagelabel.setBounds(500, 600, 400, 30);
        massagelabel.setForeground(Color.red);
        sellPanel.add(massagelabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        sellPanel.add(backButton);
        backButton.addActionListener(e -> {
            try {
                login.body(p.getUserName(), "back button", "went to shop");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.remove(sellPanel);
            shop();
        });
        //
        JLabel selllabel = new JLabel("your diamond is = " + p.diamonds + " and cards that you can sell:");
        selllabel.setBounds(500, 0, 400, 30);
        sellPanel.add(selllabel);
        //
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        //
        int j = 0;
        for (int i = 0; i < p.currentcards.size(); i++) {
            if (p.currentcards.get(i).specialFor.equals("All")) {
                BufferedImage myPicture = ImageIO.read(new File(p.currentcards.get(i).icon));
                JButton sellButton = new JButton();
                sellButton.setBounds((j % 6) * 150 + 50, (j / 6) * 200 + 100, 80, 100);
                sellButton.setIcon(new ImageIcon(myPicture));
                temp.add(sellButton);
                int finalI = i;
                sellButton.addActionListener(e -> {
                    frame.remove(sellPanel);
                    try {
                        login.body(p.getUserName(), "card show", "want to sell" + p.currentcards.get(finalI).name);
                        sellCardShow(p.currentcards.get(finalI), "", 1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                j++;
            }
        }
        //
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(50, 100, 850, 400);
        sellPanel.add(scrollableTextArea);
        //
        frame.add(sellPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void status() throws IOException {
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(null);
        statusPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel statuslabel = new JLabel("this is your status:");
        statuslabel.setBounds(500, 0, 400, 30);
        statusPanel.add(statuslabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        statusPanel.add(backButton);
        backButton.addActionListener(e -> {
            try {
                login.body(p.getUserName(), "back buton", "went to menu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.remove(statusPanel);
            Enter();
        });
        //
        JPanel temp = new JPanel();
        temp.setLayout(null);
        temp.setBounds(0, 0, 1200, 2000);
        for (int i = 0; i < p.playerDeck.size() && i < 10; i++) {
            float c = 0;
            if (p.playerDeck.get(i).allGame == 0)
                c = 0;
            else
                c = p.playerDeck.get(i).winGame / p.playerDeck.get(i).allGame;
            JTextPane deckText = new JTextPane();
            deckText.setText("name: " + p.playerDeck.get(i).name + "\nwin/game: " + c
                    + "\nwin: " + p.playerDeck.get(i).winGame + "\ngame: " + p.playerDeck.get(i).allGame
                    + "\naverage mana: " + p.playerDeck.get(i).getAverageMana() + "\nHero: " + p.playerDeck.get(i).deckHero.name
                    + "\nbest card: " + p.playerDeck.get(i).getBestCard().name);
            deckText.setBackground(Color.CYAN);
            deckText.setForeground(Color.RED);
            deckText.setBounds((i % 5) * 220 + 10, (i / 5) * 220 + 30, 210, 210);
            temp.add(deckText);
        }
        //
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(0, 0, 1180, 700);
        statusPanel.add(scrollableTextArea);
        //
        frame.add(statusPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void collection() throws IOException {
        p.update(p);
        frame.setVisible(false);
        Collection c1 = new Collection(p);
        c1.collection();
    }

    private void setting() {
        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(null);
        settingPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel settingLabel = new JLabel("setting");
        settingLabel.setBounds(500, 0, 400, 30);
        settingPanel.add(settingLabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        settingPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(settingPanel);
            try {
                login.body(p.getUserName(), "back button", "went to enter room");
                Enter();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(settingPanel);
        frame.repaint();
        frame.revalidate();
    }
}
