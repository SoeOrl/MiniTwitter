/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

/**
 *
 * @author jdodso227
 */
public final class Twit {

    String originUsername;
    String originFullname;
    ArrayList<String> mentionedUsernames;
    String twit;
    int twitId;
    LocalDateTime postedDateTime;

    public Twit(String originUsername, String originFullname,LocalDateTime postedDateTime, String twit)
    {
        this.originUsername = originUsername;
        this.originFullname = originFullname;
        this.twit = twit;
    }
    public Twit() {
        this(null, null, "", -1, LocalDateTime.now());
    }

    public Twit(String originUsername, String originFullname, String twit) {
        this(originUsername, originFullname, twit, -1, LocalDateTime.now());
    }

    public Twit(String originUsername, String originFullname, String twit, LocalDateTime postedDateTime) {
        this(originUsername, originFullname, twit, -1, postedDateTime);
    }
    
    public Twit(String originUsername, String originFullname, String twit, int twitId, LocalDateTime postedDateTime) {
        this.originUsername = originUsername;
        this.originFullname = originFullname;
        this.twit = twit;
        this.twitId = twitId;
        this.postedDateTime = postedDateTime;
        this.mentionedUsernames = parseMentionedUsernames();
        wrapMentionsInHtml();
    }

    ArrayList<String> parseMentionedUsernames() {
        ArrayList<String> mentionedUsernames = new ArrayList<>();
        Matcher matcher = Pattern.compile("(?<=\\s@)[\\d|\\w]+").matcher(this.twit);
        while(matcher.find()) {
            mentionedUsernames.add(matcher.group());
        }
        
        return mentionedUsernames;
    }
    
    void wrapMentionsInHtml() {
        this.twit = twit.replaceAll("(?<=\\s)(@[\\d|\\w]+)", "<a class=\"taggable\">$1</a>");
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
    
    public int getTwitId() {
        return twitId;
    }

    public ArrayList<String> getMentionedUsernames() {
        return mentionedUsernames;
    }

    public LocalDateTime getPostedDateTime() {
        return postedDateTime;
    }
}
