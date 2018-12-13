/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Hashtag;
import business.Twit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author jdodso227throws IOException, ClassNotFoundException, SQLException
 */
public class HashtagUtil {

    public static int insertTwitHashtags(Twit twit) throws IOException, ClassNotFoundException {
        final StringBuilder preparedSQL = new StringBuilder();
        final ArrayList<Hashtag> hashtags = twit.getHashtags();
        int insertedRows = 0;
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(dbURL, username, password);

            if (hashtags != null) {
                for (int i = 0; i < hashtags.size(); ++i) {
                    Hashtag hashtag = hashtags.get(i);
                    String hashtagUuid;
                    boolean success = insertHashtag(hashtag, connection);

                    if (success) {
                        hashtagUuid = hashtag.getUuid();
                    } else {
                        hashtagUuid = getHashtagUuid(hashtag, connection);
                    }

                    preparedSQL.append("INSERT INTO twitHashtag(twitHashtagId, twitId, hashtagId) "
                            + "VALUES (?,?,?)");

                    PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
                    ps.setString(1, UUID.randomUUID().toString());
                    ps.setString(2, twit.getUuid());
                    ps.setString(3, hashtagUuid);

                    if (ps.executeUpdate() != 1) {
                        ps.close();
                        break;
                    }

                    preparedSQL.setLength(0);
                    updateHashtagTimesUsed(hashtag, connection, CountUpdateMode.INCREMENT);
                    insertedRows++;
                }
            }

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
            return 0;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return insertedRows;
    }

    private static boolean insertHashtag(Hashtag hashtag, Connection connection) throws SQLException {
        final StringBuilder preparedSQL = new StringBuilder();

        boolean result = false;
        try {
            preparedSQL.append("INSERT INTO hashtag(hashtagId, text) ");
            preparedSQL.append("VALUES (?,?)");

            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());

            ps.setString(1, hashtag.getUuid());
            ps.setString(2, hashtag.getText());

            result = (ps.executeUpdate() == 1);

            ps.close();

        } catch (SQLIntegrityConstraintViolationException e) {
        }

        return result;
    }

    private static String getHashtagUuid(Hashtag hashtag, Connection connection) throws SQLException {
        final StringBuilder preparedSQL = new StringBuilder();

        preparedSQL.append("SELECT hashtagId FROM hashtag WHERE text = ?");

        PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
        ps.setString(1, hashtag.getText());

        ResultSet result = ps.executeQuery();

        if (result.next()) {
            String uuid = result.getString("hashtagId");
            ps.close();
            return uuid;
        } else {
            return null;
        }
    }

    public static void updateHashtagTimesUsed(Hashtag hashtag, Connection connection, CountUpdateMode mode) throws SQLException {
        final StringBuilder preparedSQL = new StringBuilder();

        String operator = (mode == CountUpdateMode.INCREMENT) ? "+" : "-";

        preparedSQL.append("UPDATE hashtag ");
        preparedSQL.append("SET timesUsed = timesUsed " + operator + " 1 ");
        preparedSQL.append("WHERE text = ?");

        PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
        ps.setString(1, hashtag.getText());

        ps.executeUpdate();
    }

    public static ArrayList<Twit> getAllTwitsForHashtag(String hashtag) throws IOException, ClassNotFoundException {
        Connection connection = null;
        ArrayList<Twit> twits = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String dbusername = "root";
            String dbpassword = "root";
            connection = DriverManager.getConnection(dbURL, dbusername, dbpassword);

            String query
                    = "SELECT fullName, username, twit, postedDateTime "
                    + "FROM twitHashtag "
                    + "LEFT JOIN twit ON twit.twitId = twitHashtag.twitId "
                    + "LEFT JOIN hashtag ON hashtag.hashtagId = twitHashtag.hashtagId "
                    + "LEFT JOIN user ON user.userId = twit.userId "
                    + "WHERE hashtag.text = ? "
                    + "ORDER BY postedDateTime DESC";

            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            ps.setString(1, hashtag);

            ResultSet results = ps.executeQuery();

            while (results.next()) {
                twits.add(new Twit(results.getString("username"),
                        results.getString("fullName"),
                        results.getString("twit"),
                        results.getTimestamp("postedDateTime").toLocalDateTime()));
            }
            return twits;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return twits;
    }

    public static ArrayList<String> getTrendingHashtags() throws IOException, ClassNotFoundException {
        Connection connection = null;
        ArrayList<String> hashtags = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String dbusername = "root";
            String dbpassword = "root";
            connection = DriverManager.getConnection(dbURL, dbusername, dbpassword);

            String query
                    = "SELECT text "
                    + "FROM hashtag "
                    + "ORDER BY timesUsed DESC "
                    + "LIMIT 10";

            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet results = ps.executeQuery();

            while (results.next()) {
                hashtags.add(results.getString("text"));
            }
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return hashtags;
    }
    
    public static ArrayList<Hashtag> findAllHashtags(String searchText) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String query
                    = "SELECT * "
                    + "FROM hashtag "
                    + "WHERE text like ? ";
            
            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            searchText = "%" + searchText + "%";
            ps.setString(1, searchText);

            ResultSet results = ps.executeQuery();

            ArrayList<Hashtag> hashtags = new ArrayList<>();
            while (results.next()) {
                hashtags.add(new Hashtag(results.getString("text")));
            }

            return hashtags;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }

        return null;
    }

    public enum CountUpdateMode {
        INCREMENT, DECREMENT
    }
}
