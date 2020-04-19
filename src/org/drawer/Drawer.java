package org.drawer;

import com.google.gson.Gson;
import org.drawer.Battle;
import org.fileWorks.FileWorks;
import org.fileWorks.login;
import org.player.Player;
import org.stuff.Card;
import org.stuff.Deck;
import org.stuff.Hero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Drawer  {

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

    public void firstquestion() {
        JPanel firstquestionpanel = new JPanel();
        firstquestionpanel.setLayout(null);
        firstquestionpanel.setBounds(0, 0, 1200, 800);
        //
        JLabel massage = new JLabel("already have an account?");
        massage.setBounds(400, 50, 200, 100);
        firstquestionpanel.add(massage);
        //
        JButton yes = new JButton("YES");
        yes.setBounds(500, 150, 60, 40);
        firstquestionpanel.add(yes);
        yes.addActionListener(e -> {
            frame.remove(firstquestionpanel);
            SingIn();
        });
        //
        JButton no = new JButton("NO");
        no.setBounds(500, 250, 60, 40);
        firstquestionpanel.add(no);
        no.addActionListener(e -> {
            frame.remove(firstquestionpanel);
            SingUp();
        });
        frame.add(firstquestionpanel);
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
            firstquestion();
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
                JLabel error = new JLabel("invalid username or password");
                error.setBounds(150, 300, 200, 30);
                error.setForeground(Color.red);
                SingInPanel.add(error);
                frame.repaint();
                frame.revalidate();
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
            firstquestion();
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
                    JLabel error = new JLabel("we have the same username please try another username");
                    error.setBounds(150, 300, 200, 30);
                    error.setForeground(Color.red);
                    SingUpPanel.add(error);
                    frame.repaint();
                    frame.revalidate();
                } else {
                    ArrayList<Hero> heros = Hero.heroSetter();
                    ArrayList<Card> currentCards = Card.currentCardsSetter();
                    ArrayList<Card> allCard = Card.allCardsSetter();
                    ArrayList<Deck> playerdeck = Deck.Decksetter(heros, currentCards);
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
            firstquestion();
        });
        //
        frame.add(EnterPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void play() throws IOException {
        Battle b1;
        if (p.currentDeck == null)
            collection();
        else {
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
                login.body(p.getUserName(),"buy room","went to buy room");
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
                login.body(p.getUserName(),"sell room","went to sell room");
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
                login.body(p.getUserName(),"back button","went to menu");
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
        login.body(p.getUserName(),"buyroom","went to buy room");
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
                login.body(p.getUserName(),"back button","went to shop");
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
                        login.body(p.getUserName(),"card show","want to buy"+p.allcards.get(finalI).name);
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

    private void buyCardShow(Card card, String massage, int n) throws IOException {
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
                    login.body(p.getUserName(),"back button","went to buy room");
                    buy("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (n == 2) {
                try {
                    login.body(p.getUserName(),"back button","went to card room");
                    cards("",20,1,null);
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
                    login.body(p.getUserName(),card.name,"have bought this card");
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

    private void sellCardShow(Card card, String massage, int n) throws IOException {
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
                    login.body(p.getUserName(),"back button","went to sell room");
                    sell("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (n == 2) {
                try {
                    login.body(p.getUserName(),"back button","went to card room");
                    cards("",20,1,null);
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
                login.body(p.getUserName(),card.name,"have sold this card");
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
                login.body(p.getUserName(),"back button","went to shop");
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
                        login.body(p.getUserName(),"card show","want to sell"+p.currentcards.get(finalI).name);
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
        login.body(p.getUserName(),"status","went to status");
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
                login.body(p.getUserName(),"back buton","went to menu");
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
        JPanel collectionPanel = new JPanel();
        collectionPanel.setLayout(null);
        collectionPanel.setBounds(0, 0, 1200, 800);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 240, 100, 30);
        collectionPanel.add(backButton);
        backButton.addActionListener(e -> {
            try {
                login.body(p.getUserName(),"back buton","went to menu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.remove(collectionPanel);
            Enter();
        });
        JLabel EnterRoom = new JLabel("Collection:");
        EnterRoom.setBounds(500, 0, 100, 30);
        collectionPanel.add(EnterRoom);
        //
        JButton cardButton = new JButton("Card");
        cardButton.setBounds(500, 80, 100, 30);
        collectionPanel.add(cardButton);
        cardButton.addActionListener(e -> {
            frame.remove(collectionPanel);
            try {
                login.body(p.getUserName(),"card","went to card room");
                cards("",20,1,null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton deckButton = new JButton("Deck");
        deckButton.setBounds(500, 160, 100, 30);
        collectionPanel.add(deckButton);
        deckButton.addActionListener(e -> {
            frame.remove(collectionPanel);
            try {
                login.body(p.getUserName(),"deck","went to deck room");
                deck("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(collectionPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void cards(String Hero,int mana,int filterCards,Card searchCard) throws IOException {
        login.body(p.getUserName(),"card collection","went to card collection");
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(null);
        cardsPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel cardslabel = new JLabel("this is all cards");
        cardslabel.setBounds(500, 0, 400, 30);
        cardsPanel.add(cardslabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        cardsPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(cardsPanel);
            try {
                login.body(p.getUserName(),"back buton","went to collection");
                collection();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JPanel temp = new JPanel(new GridBagLayout());
        //
        for (int i = 0; i < p.allcards.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < p.currentcards.size(); j++) {
                if (p.currentcards.get(j).name.equals(p.allcards.get(i).name)) {
                    flag = false;
                    break;
                }
            }
            if (flag&&(searchCard==null||searchCard==p.allcards.get(i))&&(filterCards==1||filterCards==3) && (p.allcards.get(i).mana==mana || mana==20)&&(p.allcards.get(i).specialFor.equals(Hero)||Hero.equals(""))) {
                BufferedImage myPicture = ImageIO.read(new File(p.allcards.get(i).icon));
                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                ColorConvertOp ccop = new ColorConvertOp(cs, null);
                myPicture = ccop.filter(myPicture, null);
                //
                JButton cardButton = new JButton();
                cardButton.setIcon(new ImageIcon(myPicture));
                cardButton.setBounds((i % 4) * 150, (i / 4) * 200, 75, 100);
                temp.add(cardButton);
                int finalI = i;
                cardButton.addActionListener(e -> {
                    frame.remove(cardsPanel);
                    try {
                        login.body(p.getUserName(),"buy","want to buy"+p.allcards.get(finalI).name);
                        buyCardShow(p.allcards.get(finalI), "", 2);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            } else if(!flag&&(searchCard==null||searchCard==p.allcards.get(i))&&(filterCards==1||filterCards==2) && (p.allcards.get(i).mana==mana || mana==20)&&(p.allcards.get(i).specialFor.equals(Hero)||Hero.equals(""))) {
                BufferedImage myPicture = ImageIO.read(new File(p.allcards.get(i).icon));
                JButton cardButton = new JButton(p.allcards.get(i).number + "x");
                cardButton.setIcon(new ImageIcon(myPicture));
                cardButton.setBounds((i % 4) * 150, (i / 4) * 200, 75, 100);
                temp.add(cardButton);
                int finalI = i;
                cardButton.addActionListener(e -> {
                    frame.remove(cardsPanel);
                    try {
                        login.body(p.getUserName(),"sell","want to sell"+p.allcards.get(finalI).name);
                        sellCardShow(p.allcards.get(finalI), "", 2);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
        //
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(50, 50, 800, 300);
        cardsPanel.add(scrollableTextArea);
        //
        JPanel temp2 = new JPanel(new GridBagLayout());
        for (int i = 0; i < 11; i++) {
            JButton manaButton = new JButton(String.valueOf(i));
            manaButton.setBounds(0,0,10, 20);
            int finalI = i;
            manaButton.addActionListener(e->{
                frame.remove(cardsPanel);
                try {
                    cards(Hero, finalI,filterCards,null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            temp2.add(manaButton);
        }
        JButton manaButton = new JButton("All");
        manaButton.setBounds(0,0,10, 20);
        manaButton.addActionListener(e->{
            frame.remove(cardsPanel);
            try {
                cards(Hero,20,filterCards,null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        temp2.add(manaButton);
        //
        JScrollPane scrollableMana = new JScrollPane(temp2);
        scrollableMana.setBounds(50, 500, 800, 100);
        cardsPanel.add(scrollableMana);
        //
        for (int i = 0; i < p.heros.size(); i++) {
            JButton HeroButton = new JButton(p.heros.get(i).name);
            HeroButton.setBounds(900,i*30+100,100, 30);
            int finalI = i;
            HeroButton.addActionListener(e->{
                frame.remove(cardsPanel);
                try {
                    cards(p.heros.get(finalI).name,mana,filterCards,null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            cardsPanel.add(HeroButton);
        }
        JButton filterCardsButton1 = new JButton("have or not");
        filterCardsButton1.setBounds(900,500,100, 30);
        filterCardsButton1.addActionListener(e->{
            frame.remove(cardsPanel);
            try {
                cards(Hero,mana,1,null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cardsPanel.add(filterCardsButton1);
        //
        JButton filterCardsButton2 = new JButton("have");
        filterCardsButton2.setBounds(900,530,100, 30);
        filterCardsButton2.addActionListener(e->{
            frame.remove(cardsPanel);
            try {
                cards(Hero,mana,2,null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cardsPanel.add(filterCardsButton2);
        //
        JButton filterCardsButton3 = new JButton("not have");
        filterCardsButton3.setBounds(900,560,100, 30);
        filterCardsButton3.addActionListener(e->{
            frame.remove(cardsPanel);
            try {
                cards(Hero,mana,3,null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cardsPanel.add(filterCardsButton3);
        //
        JTextField searchtext = new JTextField();
        searchtext.setBounds(450, 400, 100, 30);
        cardsPanel.add(searchtext);
        //
        JButton searchButton = new JButton("search");
        searchButton.setBounds(550,400,100, 30);
        searchButton.addActionListener(e->{
            frame.remove(cardsPanel);
            try {
                cards(Hero,mana,1,textFinder(searchtext.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cardsPanel.add(searchButton);
        //
        frame.add(cardsPanel);
        frame.repaint();
        frame.revalidate();
    }
    private Card textFinder(String name){
        double couter=0,max=0;
        for (int i = 0; i < p.allcards.size(); i++) {
            couter=similarity(name,p.allcards.get(i).name);
            if(couter>max)
                max=couter;
        }
        for (int i = 0; i < p.allcards.size(); i++) {
            if(similarity(p.allcards.get(i).name,name)==max)
                return p.allcards.get(i);
        }
        return p.allcards.get(0);
    }
    private double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }
    private int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    private void deck(String massage) throws IOException {
        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(null);
        deckPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel decklabel = new JLabel("this is all deck");
        decklabel.setBounds(500, 0, 400, 30);
        deckPanel.add(decklabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        deckPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(deckPanel);
            try {
                login.body(p.getUserName(),"back buuton","went to collection");
                collection();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton newdDeckButton = new JButton("new deck");
        newdDeckButton.setBounds(600, 630, 100, 30);
        deckPanel.add(newdDeckButton);
        newdDeckButton.addActionListener(e -> {
            frame.remove(deckPanel);
            try {
                login.body(p.getUserName(),"sing new deck","went to sign new deck");
                signNewDeck();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JPanel temp = new JPanel(new GridBagLayout());
        temp.setBounds(20, 40, 900, 2000);
        //
        for (int i = 0; i < p.playerDeck.size(); i++) {
            JButton deckButton = new JButton(p.playerDeck.get(i).name);
            deckButton.setBounds((i % 4) * 150, (i / 4) * 200, 150, 200);
            int finalI = i;
            deckButton.addActionListener(e -> {
                frame.remove(deckPanel);
                try {
                    login.body(p.getUserName(),"deck show","went to deck show room for"+p.playerDeck.get(finalI).name);
                    deckshow(p.playerDeck.get(finalI));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            temp.add(deckButton);
        }
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(50, 50, 800, 200);
        deckPanel.add(scrollableTextArea);
        //
        ButtonGroup buttonGroup = new ButtonGroup();
        ArrayList<JRadioButton> arb = new ArrayList<>();
        for (int i = 0; i < p.playerDeck.size(); i++) {
            JRadioButton radioButton = new JRadioButton(p.playerDeck.get(i).name);
            radioButton.setBounds((i % 5) * 150 + 150, 400, 100, 30);
            buttonGroup.add(radioButton);
            deckPanel.add(radioButton);
            arb.add(radioButton);
        }
        //
        JButton chageDeckButton = new JButton("change current deck");
        chageDeckButton.setBounds(500, 450, 200, 30);
        chageDeckButton.setFont(new Font("serif", Font.BOLD, 14));
        deckPanel.add(chageDeckButton);
        chageDeckButton.addActionListener(e -> {
            int i = 0;
            for (i = 0; i < arb.size(); i++) {
                if (arb.get(i).isSelected())
                    break;
            }
            p.currentDeck = p.playerDeck.get(i);
            try {
                login.body(p.getUserName(),"change current deck","currnetn deck:"+p.playerDeck.get(i).name);
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JLabel successlabel = new JLabel("you have changed your main deck successfully");
            successlabel.setBounds(500, 500, 300, 30);
            successlabel.setFont(new Font("serif", Font.BOLD, 14));
            deckPanel.add(successlabel);
        });
        //
        frame.add(deckPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void deckshow(Deck deck) throws IOException {
        JPanel deckShowPanel = new JPanel();
        deckShowPanel.setLayout(null);
        deckShowPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel decklShowlabel = new JLabel(deck.name + ":" + " and its hero is" + deck.deckHero.name);
        decklShowlabel.setBounds(400, 0, 500, 30);
        deckShowPanel.add(decklShowlabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        deckShowPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(deckShowPanel);
            try {
                login.body(p.getUserName(),"back button","went to deck room");
                deck("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton addButton = new JButton("add card");
        addButton.setBounds(600, 630, 100, 30);
        deckShowPanel.add(addButton);
        addButton.addActionListener(e -> {
            frame.remove(deckShowPanel);
            try {
                login.body(p.getUserName(),"add card room","went to add card room");
                add(deck);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JButton removeButton = new JButton("remove this deck");
        removeButton.setBounds(700, 630, 100, 30);
        deckShowPanel.add(removeButton);
        removeButton.addActionListener(e -> {
            frame.remove(deckShowPanel);
            p.playerDeck.remove(deck);
            if (p.currentDeck.name.equals(deck.name))
                p.currentDeck = null;
            try {
                login.body(p.getUserName(),"remove deck","remove deck:"+deck.name);
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                deck("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JPanel temp = new JPanel(new GridBagLayout());
        temp.setBounds(20, 40, 900, 2000);
        //
        for (int i = 0; i < deck.deckCards.size(); i++) {
            BufferedImage myPicture = ImageIO.read(new File(deck.deckCards.get(i).icon));
            JButton cardButton = new JButton();
            cardButton.setIcon(new ImageIcon(myPicture));
            cardButton.setBounds((i % 4) * 150, (i / 4) * 200, 150, 200);
            temp.add(cardButton);
            int finalI = i;
            cardButton.addActionListener(e -> {
                frame.remove(deckShowPanel);
                try {
                    login.body(p.getUserName(),"card show","went to card show for"+deck.deckCards.get(finalI).name);
                    cardShow(deck.deckCards.get(finalI), 1, deck);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(50, 50, 800, 300);
        deckShowPanel.add(scrollableTextArea);
        //
        frame.add(deckShowPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void add(Deck deck) throws IOException {
        JPanel addPanel = new JPanel();
        addPanel.setLayout(null);
        addPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel addlabel = new JLabel("add for " + deck.name);
        addlabel.setBounds(500, 0, 400, 30);
        addPanel.add(addlabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        addPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(addPanel);
            try {
                login.body(p.getUserName(),"back","went to deck show room");
                deckshow(deck);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        JPanel temp = new JPanel(new GridBagLayout());
        temp.setBounds(20, 40, 900, 2000);
        //
        for (int i = 0; i < p.currentcards.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < deck.deckCards.size(); j++) {
                if (deck.deckCards.get(j).name.equals(p.currentcards.get(i).name))
                    flag = false;
            }
            if (flag && (p.currentcards.get(i).specialFor.equals("All") || deck.deckHero.name.equals(p.currentcards.get(i).specialFor))) {
                BufferedImage myPicture = ImageIO.read(new File(p.currentcards.get(i).icon));
                JButton cardButton = new JButton();
                cardButton.setIcon(new ImageIcon(myPicture));
                cardButton.setBounds((i % 4) * 150, (i / 4) * 200, 150, 200);
                temp.add(cardButton);
                int finalI = i;
                cardButton.addActionListener(e -> {
                    frame.remove(addPanel);
                    try {
                        login.body(p.getUserName(),"card show","went to card show room for:"+p.currentcards.get(finalI).name);
                        cardShow(p.currentcards.get(finalI), 2, deck);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
        //
        JScrollPane scrollableTextArea = new JScrollPane(temp);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setBounds(50, 50, 800, 300);
        addPanel.add(scrollableTextArea);
        //
        frame.add(addPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void cardShow(Card card, int n, Deck deck) throws IOException {
        JPanel cardShowPanel = new JPanel();
        cardShowPanel.setLayout(null);
        cardShowPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel cardShowlabel = new JLabel(card.name + ":");
        cardShowlabel.setBounds(500, 0, 400, 30);
        cardShowPanel.add(cardShowlabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        cardShowPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(cardShowPanel);
            if (n == 1) {
                try {
                    login.body(p.getUserName(),"back button","went to deck show");
                    deckshow(deck);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (n == 2) {
                try {
                    login.body(p.getUserName(),"back button","went to add room");
                    add(deck);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //
        JLabel information1 = new JLabel("mana: " + card.mana + " damage: " + card.damage + " health: " + card.health);
        information1.setBounds(500, 200, 500, 100);
        information1.setFont(new Font("serif", Font.BOLD, 15));
        cardShowPanel.add(information1);
        //
        JLabel information2 = new JLabel("description: " + card.description);
        information2.setBounds(500, 300, 600, 100);
        information2.setFont(new Font("serif", Font.BOLD, 15));
        cardShowPanel.add(information2);
        //
        JLabel information3 = new JLabel("special for: " + card.specialFor + " rarity: " + card.rarity);
        information3.setBounds(500, 400, 600, 100);
        information3.setFont(new Font("serif", Font.BOLD, 15));
        cardShowPanel.add(information3);
        //
        BufferedImage myPicture = ImageIO.read(new File(card.icon));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(20, 20, 150, 200);
        cardShowPanel.add(picLabel);
        //
        if (n == 1) {
            JButton removeButton = new JButton("remove");
            removeButton.setBounds(600, 630, 100, 30);
            cardShowPanel.add(removeButton);
            removeButton.addActionListener(e -> {
                try {
                    login.body(p.getUserName(),card.name,"remove the card from "+deck.name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.remove(cardShowPanel);
                deck.deckCards.remove(card);
                try {
                    p.update(p);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    deckshow(deck);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (n == 2) {
            JButton addButton = new JButton("add");
            addButton.setBounds(600, 630, 100, 30);
            cardShowPanel.add(addButton);
            addButton.addActionListener(e -> {
                try {
                    login.body(p.getUserName(),card.name,"add this card to"+deck.name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.remove(cardShowPanel);
                deck.deckCards.add(card);
                try {
                    p.update(p);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    add(deck);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        frame.add(cardShowPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void signNewDeck() throws IOException {
        login.body(p.getUserName(),"sign new deck","want to sign new deck");
        JPanel SignNewDeckPanel = new JPanel();
        SignNewDeckPanel.setLayout(null);
        SignNewDeckPanel.setBounds(0, 0, 1200, 800);
        //
        JLabel createlabel = new JLabel("create deck");
        createlabel.setBounds(500, 0, 400, 30);
        SignNewDeckPanel.add(createlabel);
        //
        JButton backButton = new JButton("back");
        backButton.setBounds(500, 630, 100, 30);
        SignNewDeckPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.remove(SignNewDeckPanel);
            try {
                login.body(p.getUserName(),"back button","went to deck room");
                deck("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        ButtonGroup buttonGroup = new ButtonGroup();
        ArrayList<JRadioButton> arb = new ArrayList<>();
        for (int i = 0; i < p.heros.size(); i++) {
            JRadioButton radioButton = new JRadioButton(p.heros.get(i).name);
            radioButton.setBounds((i % 5) * 150 + 150, 450, 100, 30);
            buttonGroup.add(radioButton);
            SignNewDeckPanel.add(radioButton);
            arb.add(radioButton);
        }
        //
        JLabel decknamelabel = new JLabel("deck name:");
        decknamelabel.setBounds(450, 350, 100, 30);
        SignNewDeckPanel.add(decknamelabel);
        //
        JTextField usernametext = new JTextField();
        usernametext.setBounds(550, 350, 100, 30);
        SignNewDeckPanel.add(usernametext);
        //
        JButton createButton = new JButton("create");
        createButton.setBounds(600, 630, 100, 30);
        SignNewDeckPanel.add(createButton);
        createButton.addActionListener(e -> {
            frame.remove(SignNewDeckPanel);
            int i = 0;
            for (i = 0; i < arb.size(); i++) {
                if (arb.get(i).isSelected()) {
                    break;
                }
            }
            Deck deck = new Deck(usernametext.getText(), p.heros.get(i));
            try {
                login.body(p.getUserName(),"create new deck","have created "+deck.name+"to deck collection");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            p.playerDeck.add(deck);
            try {
                p.update(p);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                deckshow(p.playerDeck.get(p.playerDeck.size() - 1));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //
        frame.add(SignNewDeckPanel);
        frame.repaint();
        frame.revalidate();
    }

    private void setting() {

    }
}
