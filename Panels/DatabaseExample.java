package Panels;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class DatabaseExample {
    public static void main(String[] args) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getDatabaseInformation();
            }
        }, 0, 5000);

    }

    public static void getDatabaseInformation() {
        String url = "jdbc:mysql://localhost:3306/giraffe";
        String username = "root";
        String password = "Sanupinu23";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from sellersList");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}