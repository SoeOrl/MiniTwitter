/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.PublicUserInfo;
import business.User;
import business.Twit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import utils.RandomUser;

import static org.junit.Assert.*;
import static dataaccess.UserUtil.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jdodso227
 */
public class UserUtilTest {

    static User user;
    static Connection connection;
    static PreparedStatement ps;

    @BeforeClass
    public static void setUpClass() throws ClassNotFoundException, SQLException, ParseException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
        String username = "root";
        String password = "root";
        connection = DriverManager.getConnection(dbURL, username, password);
    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.close();
    }

    @Before
    public void setUpTest() throws SQLException {
        user = RandomUser.generate();
        ps = connection.prepareStatement("SELECT * from user WHERE username = ?");
        ps.setString(1, user.getUsername());
        assertFalse(ps.executeQuery().next());
    }

    @After
    public void tearDownTest() throws Exception {
        deleteUser(user);
    }

    /**
     * Test of insert method, of class UserUtil.
     */
    @Test
    public void testInsertUser() throws SQLException, IOException, ClassNotFoundException {

        // insert user and verify user in database
        insertUser(user);

        ps = connection.prepareStatement("SELECT * from user where username = ?");
        ps.setString(1, user.getUsername());
        ResultSet result = ps.executeQuery();

        result.next();
        User resultUser = new User(result.getString("fullName"), result.getString("email"),
                result.getString("username"), result.getString("password"),
                result.getDate("birthdate").toLocalDate(), result.getInt("questionNo"),
                result.getString("answer"));

        assertTrue(user.equals(resultUser));
        assertFalse(result.next());
    }

    /**
     * Test of searchByEmail method, of class UserUtil.
     */
    @Test
    public void testSearchByEmail() throws Exception {
        insertUser(user);
        User result = searchByEmail(user.getEmail());
        assertTrue(user.equals(result));
    }

    /**
     * Test of searchByUsername method, of class UserUtil.
     */
    @Test
    public void testSearchByUsername() throws Exception {
        insertUser(user);
        User result = searchByUsername(user.getUsername());
        assertTrue(user.equals(result));
    }

    /**
     * Test of deleteUser method, of class UserUtil.
     */
    @Test
    public void testDeleteUser() throws Exception {
        insertUser(user);
        User result = searchByUsername(user.getUsername());
        assertTrue(user.equals(result));

        deleteUser(user);
        result = searchByUsername(user.getUsername());
        assertNull(result);
    }

    /**
     * Test of updateUser method, of class UserUtil.
     */
    @Test
    public void testUpdateUser() throws Exception {
        insertUser(user);

        user.setFullName("Joe D");
        user.setBirthdate(LocalDate.of(1991, 12, 30));

        int result = updateUser(user);
        assertTrue(result == 1);

        assertTrue(user.equals(searchByUsername(user.getUsername())));
    }
}
