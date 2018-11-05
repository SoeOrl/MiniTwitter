/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 *
 * @author jdodso227
 */
public final class Twit {

    String originUsername;
    String originFullname;
    ArrayList<String> mentionedUsernames;
    String twit;
    LocalDateTime postedDateTime;

    public Twit() {
        originUsername = null;
        originFullname = null;
        twit = "";
        postedDateTime = LocalDateTime.now();
        mentionedUsernames = new ArrayList<>();
    }

    public Twit(String originUsername, String originFullname, String twit) {
        this.originUsername = originUsername;
        this.originFullname = originFullname;
        this.twit = twit;
        this.postedDateTime = LocalDateTime.now();
        this.mentionedUsernames = parseMentionedUsernames();
    }

    public Twit(String originUsername, String originFullname, String twit, LocalDateTime postedDateTime) {
        this.originUsername = originUsername;
        this.originFullname = originFullname;
        this.twit = twit;
        this.postedDateTime = postedDateTime;
        this.mentionedUsernames = parseMentionedUsernames();
    }

    ArrayList<String> parseMentionedUsernames() {
        ArrayList<User> mentionedUsers = new ArrayList<>();

        return mentionedUsernames;
    }
    
    public String getOriginUsername() {
        return originUsername;
    }
    
    public String getOriginFullname() {
        return originFullname;
    }

    public String getTwit() {
        return twit;
    }

    public void setTwit(String twit) {
        this.twit = twit;
        this.mentionedUsernames = parseMentionedUsernames();
    }

    public ArrayList<String> getMentionedUsernames() {
        return mentionedUsernames;
    }

    public LocalDateTime getPostedDateTime() {
        return postedDateTime;
    }
}
