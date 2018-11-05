/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Twit;
import business.User;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jdodso227
 */
public class TwitUtilTest {
    
    public TwitUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertTwit method, of class TwitUtil.
     */
    @Test
    public void testInsertTwit() throws Exception {
        System.out.println("insertTwit");
        User user = null;
        Twit twit = null;
        int expResult = 0;
        int result = TwitUtil.insertTwit(user, twit);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTwitsForUsername method, of class TwitUtil.
     */
    @Test
    public void testGetTwitsForUsername() throws Exception {
        System.out.println("getTwitsForUsername");
        String username = "";
        ArrayList<Twit> expResult = null;
        ArrayList<Twit> result = TwitUtil.getTwitsForUsername(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteTwit method, of class TwitUtil.
     */
    @Test
    public void testDeleteTwit() throws Exception {
        System.out.println("deleteTwit");
        User user = null;
        int twitId = 0;
        int expResult = 0;
        int result = TwitUtil.deleteTwit(user, twitId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
