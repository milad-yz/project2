package org.launchcode;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;

public class korsisheeer {
    public static void main(String[] args) throws BadLocationException {
        JFrame frame=new JFrame("salam");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(500,500);
        //
        JPanel panel=new JPanel(new GridBagLayout());
        panel.setBounds(0,0,500,500);
        //
        JButton button=new JButton("mili");
        panel.add(button);
        button.addActionListener(e->{
            button.setText("milad");
            frame.repaint();
            frame.revalidate();
        });
        JTextPane text=new JTextPane();
        Document doc=text.getDocument();
        doc.insertString(doc.getLength(),"milad",null);
        panel.add(text);
        //
        frame.add(panel);
        frame.repaint();
        frame.revalidate();
    }
}
