package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpForm extends Form{
    private JPanel signupPanel;
    private JButton signupButton;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton loginButton;
    private JLabel titleLabel;
    private JFrame signupFrame;

    public  SignUpForm(Connection con){
        signupFrame = new JFrame();
        signupFrame.setTitle("hideMe | SignUp");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        signupFrame.setContentPane(signupPanel);
        signupFrame.setSize(350,500);
        signupFrame.setVisible(true);
        signupFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateSignUp(con);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupFrame.dispose();
                new LoginForm(con);
            }
        });
    }

    public boolean isValidUserName(Connection con, String username)
    {
        PreparedStatement smt;
        try {
            smt = con.prepareStatement("SELECT user_name FROM users WHERE user_name=?");
            smt.setString(1,username);
            ResultSet res = smt.executeQuery();

            if(res.next()) return false;
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void insertData(Connection con){
        String query = "INSERT INTO USERS (user_name,user_email,username,password) VALUES(?,?,?,?);";
        try {
            PreparedStatement smt = con.prepareStatement(query);
            smt.setString(1,usernameField.getText());
            smt.setString(2,emailField.getText());
            smt.setString(3,usernameField.getText());
            smt.setString(4,String.valueOf(passwordField1.getPassword()));
            smt.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void validateSignUp(Connection con){

        //Email Validation
        Pattern emailPattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                ")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(emailField.getText());
        boolean emailValid = emailMatcher.find();

        //Password Validation
        Pattern passwordPattern = Pattern.compile("^.{8,20}$");
        Matcher passwordMatcher = passwordPattern.matcher(String.valueOf(passwordField1.getPassword()));
        boolean passwordValid = passwordMatcher.find();

        boolean isSame = String.valueOf(passwordField1.getPassword()).equals(String.valueOf(passwordField2.getPassword()));

        boolean isValidName = !nameField.getText().equals("") && !usernameField.getText().equals("");

        boolean validUserName = isValidUserName(con,usernameField.getText());

        if(emailValid && passwordValid && isSame && isValidName && validUserName)
        {
            signupFrame.dispose();
            insertData(con);
            new LoginForm(con);
        }
        else if(!emailValid)
        {
            this.showDialog("Enter a valid Email");
        }
        else if(!validUserName)
        {
            this.showDialog("Username taken");
        }
        else if(!passwordValid)
        {
            this.showDialog("Password must contain 8 characters");
        }
        else if(!isSame)
        {
            this.showDialog("Re-entered password is not same as given password");
        }
        else
        {
            this.showDialog("Enter all required Field");
        }
    }
}
