package org.drawer.sign;

import org.drawer.Drawer;

import javax.swing.*;

public class FirstQuestion {
    private JFrame frame;
    private static FirstQuestion single_instance = null;
    public FirstQuestion() {
        frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setLayout(null);
        draw();
    }
    public static FirstQuestion getInstance() {
        if (single_instance == null)
            single_instance = new FirstQuestion();
        return single_instance;
    }

    public void draw(){
        frame.setVisible(true);
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
            new SignIn(frame);
        });
        //
        JButton no = new JButton("NO");
        no.setBounds(500, 250, 60, 40);
        firstQuestionPanel.add(no);
        no.addActionListener(e -> {
            frame.remove(firstQuestionPanel);
            new SignUp(frame);
        });
        frame.add(firstQuestionPanel);
        frame.repaint();
        frame.revalidate();
    }
}
