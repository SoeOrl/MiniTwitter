package dataaccess;

import business.User;
import business.PublicUserInfo;
import business.Twit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserUtil {

    public static int insertUser(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String preparedSQL
                    = "INSERT INTO user(fullName, email, username, password, "
                    + "birthdate, questionNo, answer, lastLogin) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setDate(5, Date.valueOf(user.getBirthdate()));
            ps.setString(6, String.valueOf(user.getQuestionNo()));
            ps.setString(7, user.getAnswer());
            ps.setTimestamp(8, Timestamp.valueOf(user.getLastLogin()));

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
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
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
                        result.getDate("birthdate").toLocalDate(), result.getInt("questionNo"),
                        result.getString("answer"), result.getTimestamp("lastLogin").toLocalDateTime());
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

    public static int updateUser(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String preparedSQL
                    = "UPDATE user "
                    + "SET "
                    + "  fullName = ?, "
                    + "  password = ?, "
                    + "  birthdate = ?, "
                    + "  questionNo = ?, "
                    + "  answer = ? "
                    + "WHERE username = ?";

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getPassword());
            ps.setDate(3, Date.valueOf(user.getBirthdate()));
            ps.setInt(4, user.getQuestionNo());
            ps.setString(5, user.getAnswer());
            ps.setString(6, user.getUsername());

            return ps.executeUpdate();

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    public static int deleteUser(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String preparedSQL = "DELETE FROM user WHERE username = ?";

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getUsername());

            return ps.executeUpdate();

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    public static ArrayList<PublicUserInfo> getAllUsers() throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String sql = "SELECT fullName, username FROM user";

            //add values to the above SQL statement and execute it.
            Statement st = connection.createStatement();
            ResultSet results = st.executeQuery(sql);

            ArrayList<PublicUserInfo> allUsers = new ArrayList<>();
            while (results.next()) {
                allUsers.add(new PublicUserInfo(results.getString("fullName"), results.getString("username")));
            }
            
            return allUsers;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return null;
    }
    
    public static void setLastLogin(User user) throws IOException, ClassNotFoundException
    {
          try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String sql = "UPDATE user SET lastLogin = ? WHERE email = ? ";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setTimestamp(1, Timestamp.valueOf(user.getLastLogin()));
            ps.setString(2, user.getEmail());
            
            ps.executeUpdate();
            
             } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
    }
}
