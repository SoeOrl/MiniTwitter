/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import utils.RandomUser;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author jdodso227
 */
public class TwitTest {
    private static String twitBody = "Hi @user1, how is @user2? @3user is good. @4user5user6 is better. "
                + "My email is joe@email.com and that shouldn't be a user. But @z should be and so should @7";
    private static ArrayList<String> expUsers = new ArrayList<>(Arrays.asList("user1", "user2", "3user", "4user5user6", "z", "7"));
    
    User user;
    
    @Before
    public void setUpTest() {
        user = RandomUser.generate();
    }
    
    /**
     * Test of parseMentionedUsernames method, of class Twit.
     */
    @Test
    public void testParseMentionedUsernames() {
        System.out.println("parseMentionedUsernames");
                
        Twit instance = new Twit(user.getUsername(), user.getFullName(), twitBody);
        
        ArrayList<String> result = instance.parseMentionedUsernames();
        
        assertEquals(expUsers, result);
    }

    /**
     * Test of getMentionedUsernames method, of class Twit.
     */
    @Test
    public void testGetMentionedUsernames() {
        System.out.println("getMentionedUsernames");
        
        Twit instance = new Twit(user.getUsername(), user.getFullName(), twitBody);
        
        ArrayList<String> result = instance.getMentionedUsernames();
        
        assertEquals(expUsers, result);
    }
}