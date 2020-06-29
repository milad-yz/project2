package org.drawer.battle.functions;

import org.drawer.battle.Battle;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimerCounter implements Runnable {
    private JPanel panel;
    private Battle battle;
    private int counter=60;

    public TimerCounter(JPanel panel, Battle battle) {
        this.panel = panel;
        this.battle = battle;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        JLabel label=new JLabel();
        label.setBounds(900,260,200,30);
        panel.add(label);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                label.setText("you have " + counter + " seconds to do");
                if (counter <= 0)
                    endTurn();
                counter--;
                panel.repaint();
                panel.revalidate();
            }
        };
        timer.schedule(timerTask,0,1000);
    }
    private void endTurn(){
        battle.endTurn();
    }

    public void reset() {
        counter=60;
    }
}
