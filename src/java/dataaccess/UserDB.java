package dataaccess;

import business.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    public static long insert(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver;useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String preparedSQL
                    = "Insert into user(fullName, email, username, password, "
                    + "birthdate, questionNo, answer) "
                    + "Values (?, ?, ?, ?, ?, ?, ?)";

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setDate(5, new Date(user.getBirthdate().getTime()));
            ps.setString(6, String.valueOf(user.getQuestionNo()));
            ps.setString(7, user.getAnswer());

            return ps.executeUpdate();
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    public static User searchByEmail(String email) throws IOException, ClassNotFoundException {
        return search("email", email);
    }

    public static User searchByUsername(String username) throws IOException, ClassNotFoundException {
        return search("username", username);
    }

    private static User search(String fieldName, String fieldValue) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver;useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String query = String.format("SELECT * "
                    + "FROM user "
                    + "WHERE %s = ?", fieldName);

            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            ps.setString(1, fieldValue);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                return new User(result.getString("fullName"), result.getString("email"),
                        result.getString("username"), result.getString("password"),
                        result.getDate("birthdate"), result.getInt("questionNo"),
                        result.getString("answer"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }

        return null;
    }
}
