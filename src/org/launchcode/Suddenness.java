package org.launchcode;

import java.util.ArrayList;
import java.util.HashMap;

public class Suddenness {
    public static void main(String[] args) {
//        JFrame frame=new JFrame("sign room");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1200, 800);
//        frame.setVisible(true);
//        frame.setLayout(null);
//        JPanel panel=new JPanel(null);
//        panel.setBounds(0,0,1200,800);
//        CardLabel card=new CardLabel(null);
//        panel.add(card);
//        frame.add(panel);
//        frame.repaint();
//        frame.revalidate();
        HashMap<Integer,String> hashMap=new HashMap<>();
        hashMap.put(1,"salam");
        hashMap.put(2,"bye");
        System.out.println(hashMap);
        hashMap.put(1,"bye bye");
        System.out.println(hashMap);
    }

    private static void changer(ArrayList<Integer> in) {
        in.set(0,0);
    }

    public static Motor purify(){
        return new MammadMotor();
    }
}

class Motor{
    public void stringto(){
        System.out.println("Salam");
    }
}
class MammadMotor extends Motor{
    int x;
}
class AliMotor extends Motor{

}