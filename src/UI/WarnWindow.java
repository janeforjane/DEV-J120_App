package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarnWindow {


    public WarnWindow (String msg) {

        JFrame warnFrame = new JFrame();
        warnFrame.setTitle("Warning!");
        warnFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        warnFrame.setLayout(new BorderLayout());
//        warnFrame.pack();
        warnFrame.setSize(290,70);
        warnFrame.setBounds(230,230,290,80);

        JPanel msgPanel = new JPanel();
        JLabel msgLabel = new JLabel(msg);
        JButton msgButton = new JButton("OK");

        msgPanel.add(msgLabel);
        warnFrame.add(msgPanel, BorderLayout.CENTER);
        warnFrame.add(msgButton, BorderLayout.SOUTH);

        msgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                warnFrame.setVisible(false);

            }
        });





        warnFrame.setVisible(true);
    }
}
