package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MessageDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel messageField;

    public MessageDialog(){};
    public MessageDialog(String message) {
        setTitle("hideMe | Message Box");
        setContentPane(contentPane);
        setModal(true);
        setPreferredSize(new Dimension(300,200));
        getRootPane().setDefaultButton(buttonOK);
        messageField.setText(message);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        dispose();
    }

}
