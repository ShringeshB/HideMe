package Pages;

import Encrypt.EncryptPassword;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;


public class Dashboard  extends Form {
    private JPanel dashboardPanel;
    private JTextField inputField;
    private JButton deleteButton;
    private JTextField site_nameField;
    private JTextField usernameField;
    private JButton addButton;
    private JButton searchButton;
    private JTextArea dataArea;
    private JScrollPane scrollPane;
    private JTextField passwordField;
    private JLabel nameLabel;
    private JButton editButton;
    private int id;
    private EncryptPassword encryptPassword;

    public Dashboard(Connection con, int userid, String username){
        encryptPassword = new EncryptPassword();
        JFrame dashBoardFrame = new JFrame();
        id = userid;

        nameLabel.setText(username);
        dashBoardFrame.setTitle("hideMe | Dashboard");
        dashBoardFrame.setContentPane(dashboardPanel);
        dashBoardFrame.setSize(900,700);
        dashBoardFrame.setVisible(true);
        dashBoardFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addData(con);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeData(con);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchData(con);
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editData(con);
            }
        });

        displayData(con);
    }

    void displayData(Connection con){
        String query = "SELECT * FROM userdata WHERE user_id=?";

        try {
            PreparedStatement smt = con.prepareStatement(query);
            smt.setInt(1,id);
            ResultSet res = smt.executeQuery();

            while(res.next()!=false)
            {
                dataArea.append(" "+res.getString(2)+"\n");
                dataArea.append(" Username: "+res.getString(3)+"\n");
                String pass = res.getString(4);
                dataArea.append(" Password: "+encryptPassword.decryptedData(pass)+"\n");
                dataArea.append(" Last Modified: "+res.getString(5)+"\n");
                dataArea.append("*******************************************"+"\n\n");
            }

        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    void addData(Connection con)
    {
        if(site_nameField.getText().equals("")) return;

        String username = usernameField.getText();
        usernameField.setText("");
        String password = encryptPassword.encryptedData(passwordField.getText());
        passwordField.setText("");
        String site_name = site_nameField.getText();
        site_nameField.setText("");


        String query = "INSERT INTO userdata (user_id,site_name,site_username,site_password) VALUES(?,?,?,?);";

        try {
            PreparedStatement smt = con.prepareStatement(query);
            smt.setInt(1,id);
            smt.setString(2,site_name);
            smt.setString(3,username);
            smt.setString(4,password);
            smt.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        dataArea.setText("");
        displayData(con);
    }

    void removeData(Connection con){
        if(inputField.getText().equals("")) return;

        String site_name = inputField.getText();
        inputField.setText("");
        String query = "DELETE FROM userdata WHERE user_id = ? AND site_name = ?;";

        try{
            PreparedStatement smt = con.prepareStatement(query);
            smt.setInt(1,id);
            smt.setString(2,site_name);
            smt.execute();
            this.showDialog("Deleted Successfully");
        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        dataArea.setText("");
        displayData(con);
    }

    void searchData(Connection con)
    {
        String site_name = inputField.getText();
        inputField.setText("");
        String query = "SELECT * FROM userdata WHERE user_id = ? AND site_name = ?;";
        try{
            PreparedStatement smt = con.prepareStatement(query);
            smt.setInt(1,id);
            smt.setString(2,site_name);
            ResultSet res = smt.executeQuery();

            String username = "";
            String password = "";

                while (res.next()) {
                    username += res.getString(3);
                    password += encryptPassword.decryptedData(res.getString(4));
                }

            if(username.equals("")) {
                this.showDialog("No data Found");
            }
            else{
                this.showDialog("Username: "+username+" \n\n "+"Password: "+password);
            }
        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    void editData(Connection con)
    {
        if(site_nameField.getText().equals("")) return;

        String username = usernameField.getText();
        usernameField.setText("");
        String password = encryptPassword.encryptedData(passwordField.getText());
        passwordField.setText("");
        String site_name = site_nameField.getText();
        site_nameField.setText("");
        Date date = new Date();
        Object datetime = new java.sql.Timestamp(date.getTime());

        String query = "UPDATE userdata SET site_username = ?, site_password = ?, last_modified = ? WHERE user_id = ? AND site_name = ?;";

        try {
            PreparedStatement smt = con.prepareStatement(query);
            smt.setString(1,username);
            smt.setString(2,password);
            smt.setObject(3,datetime);
            smt.setInt(4,id);
            smt.setString(5,site_name);

            if(smt.executeUpdate()==1)
                this.showDialog("Edited data successfully !!");
            else
                this.showDialog("Data not Found ");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        dataArea.setText("");
        displayData(con);
    }
}
