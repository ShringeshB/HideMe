
import Pages.LoginForm;
import Pages.SignUpForm;
import database.DatabaseConnection;

import java.sql.*;

public class HideMe {
    private void connectDb(){
        DatabaseConnection connectionObj = new DatabaseConnection();
        Connection con =  connectionObj.connect();
        new LoginForm(con);
    }
    public static void main(String[] args) {
        HideMe obj = new HideMe();
        obj.connectDb();
    }
}
