package dataaccess;

import business.User;
import business.Twit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DatabaseUtil {

    public static long insertUser(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String preparedSQL
                    = "INSERT INTO user(fullName, email, username, password, "
                    + "birthdate, questionNo, answer) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

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
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
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

    public static int insertTwit(User user, Twit twit) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            final StringBuilder preparedSQL = new StringBuilder();

            preparedSQL.append("INSERT INTO twit(userId, twit, postedDateTime) ");
            preparedSQL.append("VALUES ((SELECT userId FROM user WHERE username = ?), ?, ?);");

            ArrayList<String> mentionedUsernames = twit.getMentionedUsernames();
            if (mentionedUsernames.size() > 0) {
                preparedSQL.append("INSERT INTO userMention(originUserId, mentionedUserId, twitId) VALUES ");

                for (int i = 0; i < mentionedUsernames.size(); ++i) {
                    preparedSQL.append(String.format("((SELECT userId FROM user WHERE username = '%s'), "
                            + "(SELECT userId FROM user WHERE username = '%s'), "
                            + "LAST_INSERT_ID())", user.getUsername(), mentionedUsernames.get(i)));

                    if (i != mentionedUsernames.size() - 1) {
                        preparedSQL.append(", ");
                    } else {
                        preparedSQL.append(";");
                    }
                }
            }

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            ps.setString(1, user.getUsername());
            ps.setString(2, twit.getTwit());
            ps.setTimestamp(3, Timestamp.valueOf(twit.getPostedDateTime()));

            return ps.executeUpdate();
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    public static ArrayList<Twit> getTwitsForUsername(String username) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String dbusername = "root";
            String dbpassword = "root";
            Connection connection = DriverManager.getConnection(dbURL, dbusername, dbpassword);

            String query
                    = "SELECT originUsername, originFullname, twit, postedDateTime FROM "
                    + "(SELECT twitid, twit, twit.userid, username AS originUsername, fullname AS originFullname, postedDateTime "
                    + "    FROM twit "
                    + "    JOIN user ON twit.userid = user.userid) AS named_twits "
                    + "LEFT join "
                    + "    (SELECT username AS mentionedUsername, twitid FROM usermention "
                    + "     JOIN user ON usermention.mentionedUserId = user.userid) AS named_mentions "
                    + "ON named_twits.twitid = named_mentions.twitid "
                    + "WHERE originUsername = ? OR mentionedUsername = ?"
                    + "ORDER BY postedDateTime DESC";

            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            ps.setString(1, username);
            ps.setString(2, username);

            ResultSet results = ps.executeQuery();

            ArrayList<Twit> twits = new ArrayList<>();
            while (results.next()) {
                twits.add(new Twit(results.getString("originUsername"), 
                        results.getString("originFullname"), 
                        results.getString("twit"),
                        results.getTimestamp("postedDateTime").toLocalDateTime()));
            }
            
            return twits;
            
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
            
            return null;
        }
    }
}
