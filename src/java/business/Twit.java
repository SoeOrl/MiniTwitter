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
import java.util.UUID;

/**
 *
 * @author jdodso227
 */
public final class Twit {

    String originUsername;
    String originFullname;
    ArrayList<String> mentionedUsernames;
    ArrayList<Hashtag> hashtags;
    String twit;
    UUID uuid;
    LocalDateTime postedDateTime;
    
    private final static String mentionsRegexString = "(?<=\\s)(@[\\d|\\w]+)";
    private final static String hashtagsRegexString = "(?<=[\\s]|^)(#[\\w|\\d]+)";

    public Twit() {
        this(null, null, "", UUID.randomUUID().toString(), LocalDateTime.now());
    }

    public Twit(String originUsername, String originFullname, String twit) {
        this(originUsername, originFullname, twit, UUID.randomUUID().toString(), LocalDateTime.now());
    }

    public Twit(String originUsername, String originFullname, String twit, LocalDateTime postedDateTime) {
        this(originUsername, originFullname, twit, UUID.randomUUID().toString(), postedDateTime);
    }
    
    public Twit(String originUsername, String originFullname, String twit, String twitId, LocalDateTime postedDateTime) {
        this.originUsername = originUsername;
        this.originFullname = originFullname;
        this.twit = twit;
        this.uuid = UUID.fromString(twitId);
        this.postedDateTime = postedDateTime;
        this.mentionedUsernames = parseMentionedUsernames();
        this.hashtags = parseHashtags();
        wrapMentionsAndHashtagsInHtml();
    }
    
    ArrayList<String> parseMentionedUsernames() {
        ArrayList<String> mentionedUsernames = new ArrayList<>();
        Matcher matcher = Pattern.compile(mentionsRegexString).matcher(this.twit);
        while(matcher.find()) {
            mentionedUsernames.add(matcher.group().replace("@", ""));
        }
        
        return mentionedUsernames;
    }
    
    ArrayList<Hashtag> parseHashtags() {
        ArrayList<Hashtag> hashtags = new ArrayList<>();
        Matcher matcher = Pattern.compile(hashtagsRegexString).matcher(this.twit);
        
        while(matcher.find()) {
            hashtags.add(new Hashtag(matcher.group()));
        }
        
        return hashtags;
    }
    
    void wrapMentionsAndHashtagsInHtml() {
        this.twit = twit.replaceAll(mentionsRegexString, "<a class=\"taggable\">$1</a>");
        this.twit = twit.replaceAll(hashtagsRegexString, "<a href=\"hashtag?tag=$1\" class=\"taggable\">$1</a>");
        this.twit = twit.replaceAll("tag=#", "tag=%23");
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
        this.hashtags = parseHashtags();
    }
    
    public String getUuid() {
        return uuid.toString();
    }

    public ArrayList<String> getMentionedUsernames() {
        return mentionedUsernames;
    }
    
    public ArrayList<Hashtag> getHashtags() {
        return hashtags;
    }

    public LocalDateTime getPostedDateTime() {
        return postedDateTime;
    }
}
