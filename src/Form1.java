import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form1 {
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton button1;

    public Form1() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
textArea1.setText("HelloWorld");
            }
        });
    }
}
