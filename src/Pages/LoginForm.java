package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginForm extends Form {
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private JLabel titleLabel;

    private JFrame loginFrame;

    public LoginForm(Connection con){
        loginFrame = new JFrame();
        loginFrame.setTitle("hideMe | Login");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        loginFrame.setContentPane(loginPanel);
        loginFrame.setSize(500,300);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateLogin(con);
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                new SignUpForm(con);
            }
        });

    }

    void validateLogin(Connection con){
        if(this.isValid(con,usernameField.getText(), String.valueOf(passwordField.getPassword())))
        {
            loginFrame.dispose();
            new Dashboard(con,this.userid,this.name);
        }
        else
        {
            this.showDialog("Username or Password Wrong");
        }
    }
}
