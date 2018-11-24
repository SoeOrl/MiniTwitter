/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Soeren
 */
public class Notifications implements Comparable<Notifications> {

    Twit twit;
    Follow follow;
    LocalDateTime postedDateTime;

    public Notifications(Twit tweet) {
        twit = tweet;
        postedDateTime = tweet.getPostedDateTime();
        follow = null;
    }

    public Notifications(Follow userFollow, LocalDateTime time) {
        follow = userFollow;
        postedDateTime = time;
        twit = null;
    }

    @Override
    public int compareTo(Notifications notification) {
        return (this.postedDateTime.isAfter(notification.postedDateTime) ? 1
                : (this.postedDateTime.equals(notification.postedDateTime) ? 0 : -1));
    }

    public Twit getTwit() {
        return twit;
    }

    public Follow getFollow() {
        return follow;
    }

    public String getPostedDateTime() {
        return postedDateTime.toString();
    }
}
