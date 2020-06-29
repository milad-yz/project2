package org.drawer.battle.functions;

import org.drawer.labelsAndButtons.CardLabel;
import org.stuff.cards.Minion;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class AnimationCard implements Runnable {
    double x1,x2,y1,y2;
    int count=0;
    double Vx,Vy;
    JPanel panel;
    Minion minion;
    Thread thread;
    public AnimationCard(JPanel panel, double x1, double y1, double x2, double y2, Minion minion){
        this.panel=panel;
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        this.minion=minion;
        Vx=(x2-x1)/200;
        Vy=(y2-y1)/200;
        thread=new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        CardLabel cardLabel=new CardLabel(minion);
        cardLabel.setBounds((int)x1,(int)y1,75,100);
        panel.add(cardLabel);
        Timer timer=new Timer();
        cardLabel.setOpaque(false);
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                x1+=Vx;
                y1+=Vy;
                cardLabel.setBounds((int)x1,(int)y1,75,100);
                panel.repaint();
                panel.revalidate();
                count++;
                if(count>=200){
                    panel.remove(cardLabel);
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask,0,2);
    }
}
