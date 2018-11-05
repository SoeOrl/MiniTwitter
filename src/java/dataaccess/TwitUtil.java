/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Twit;
import business.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author jdodso227
 */
public class TwitUtil {

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
            if (mentionedUsernames != null && mentionedUsernames.size() > 0) {
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
                    = "SELECT originUsername, originFullname, named_twits.twitId AS twitId, twit, postedDateTime FROM "
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
                        results.getInt("twitId"),
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

    public static int getNumUserTwits(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String dbusername = "root";
            String dbpassword = "root";
            Connection connection = DriverManager.getConnection(dbURL, dbusername, dbpassword);

            String query
                    = "SELECT COUNT(*) as numTwits "
                    + "FROM twit "
                    + "JOIN user ON user.userid = twit.userid "
                    + "WHERE user.username = ?";

            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            ps.setString(1, user.getUsername());

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                return result.getInt("numTwits");
            }

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        
        return 0;
    }

    public static int deleteTwit(User user, int twitId) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String dbusername = "root";
            String dbpassword = "root";
            Connection connection = DriverManager.getConnection(dbURL, dbusername, dbpassword);

            String query
                    = "DELETE FROM twit "
                    + "WHERE twitId = ? AND "
                    + "    userId = (SELECT userId FROM user WHERE username = ?)";

            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            ps.setInt(1, twitId);
            ps.setString(2, user.getUsername());

            return ps.executeUpdate();

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }

            return 1;
        }
    }
}
