package dataaccess;

import business.User;
import business.PublicUserInfo;
import business.Twit;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
import java.util.Base64;
import java.util.Random;

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
                    + "birthdate, questionNo, answer, lastLogin,salt) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            //add values to the above SQL statement and execute it.
            String salt = getSalt();
            PreparedStatement ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            try {
                ps.setString(4, hashAndSaltPassword(user.getPassword(), salt));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            ps.setDate(5, Date.valueOf(user.getBirthdate()));
            ps.setString(6, String.valueOf(user.getQuestionNo()));
            ps.setString(7, user.getAnswer());
            ps.setTimestamp(8, Timestamp.valueOf(user.getLastLogin()));
            ps.setString(9, salt);
            int length = salt.getBytes().length;
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
             try {
                ps.setString(2, hashAndSaltPassword(user.getPassword(), user));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
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
    
    public static int updatePassword(User user, String newPassword) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String preparedSQL
                    = "UPDATE user "
                    + "SET "
                    + "  password = ? "
                    + "WHERE user.username = ?";

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL);
            ps.setString(2, user.getUsername());
             try {
                ps.setString(1, hashAndSaltPassword(newPassword, user));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

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

    public static void setLastLogin(User user, LocalDateTime now) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String sql = "UPDATE user SET lastLogin = ? WHERE email = ? ";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setTimestamp(1, Timestamp.valueOf(now));
            ps.setString(2, user.getEmail());

            ps.executeUpdate();

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
    }

    public static String getSalt() {
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[32];
        r.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public static String hashAndSaltPassword(String password, User user) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        String salt = getSaltFromDatabase(user);
        return hashPassword(password + salt);
    }

    public static String hashAndSaltPassword(String password, String salt) throws NoSuchAlgorithmException {
        return hashPassword(password + salt);
    }

    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }

            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    private static String getSaltFromDatabase(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String sql = "Select user.salt From user WHERE user.userID=(Select user.userID FROM user WHERE user.username = ?);";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return (results.getString("salt"));
            }

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return null;
    }
}
