/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import business.Twit;
import business.UserValidator;
import dataaccess.FollowsUtil;
import static dataaccess.HashtagUtil.getTrendingHashtags;
import dataaccess.NotificationUtil;
import dataaccess.UserUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static dataaccess.TwitUtil.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;

/**
 *
 * @author jdodso227
 */
public class HomepageServlet extends HttpServlet {

    private final static int MAX_TWIT_LENGTH = 280;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


 User user = null;
        HttpSession session = request.getSession();
        String forwardUrl = "";
        String message = "";
        Cookie cookie = null;
        Cookie[] cookies = null;
        cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                String cookieName = cookie.getName();
                if (cookieName.equals("user")) {
                   String test = cookie.getValue();
                    try {
                        user = UserUtil.searchByUsername(test);
                         session.setAttribute("user", user);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(HomepageServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
        if (user == null)
        {

        user = (User) session.getAttribute("user");   
        }
        if (user ==null)
        {
            forwardUrl = "/login.jsp";
        }
        else{       
        try {
            updateHomepage(session);
            forwardUrl = "/home.jsp";

        } catch (IOException | ClassNotFoundException e) {
            message = "Server Error - Could not retrieve your twits or other users' information";
            forwardUrl = "/login.jsp";
        }
        }
         

        getServletContext()
                .getRequestDispatcher("/home.jsp")
                .forward(request, response);
    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = null;
        HttpSession session = request.getSession();
        String forwardUrl = "";
        String message = "";
        Cookie cookie = null;
        Cookie[] cookies = null;
        cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                String cookieName = cookie.getName();
                if (cookieName.equals("user")) {
                   String test = cookie.getValue();
                    try {
                        user = UserUtil.searchByUsername(test);
                        session.setAttribute("user", user);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(HomepageServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
        if (user == null)
        {

        user = (User) session.getAttribute("user");   
        }
        String action = request.getParameter("action");




        if (user == null) {
            message = "Server Error - User could not be validated";
            forwardUrl = "/login.jsp";
        } else {
            try {
                switch (action) {
                    case "createTwit":
                        String twitBody = request.getParameter("twitBody");
                        forwardUrl = "/home.jsp";

                        if (twitBody.length() > MAX_TWIT_LENGTH) {
                            message = "Twit length must be 280 characters or less";
                        } else {
                            Twit twit = new Twit(user.getUsername(), user.getFullName(), twitBody);

                            if (allMentionedUsersExist(twit)) {
                                insertTwit(user, twit);
                                updateHomepage(session);
                            } else {
                                message = "One or more mentioned users do not exist";
                            }
                        }

                        break;

                    case "deleteTwit":
                        String twitId = request.getParameter("twitToDeleteId");
                        String twitText = request.getParameter("twitToDeleteText");
                        deleteTwit(user, twitId, twitText);
                        updateHomepage(session);

                        forwardUrl = "/home.jsp";
                        break;

                    case "updateProfile":
                        try {
                            user.setFullName(request.getParameter("fullName"));
                            user.setEmail(user.getEmail());
                            user.setUsername(user.getUsername());
                            user.setPassword(request.getParameter("password"));
                            user.setBirthdate(request.getParameter("birthdate"));
                            user.setQuestionNo(Integer.parseInt(request.getParameter("questionNo")));
                            user.setAnswer(request.getParameter("securityAnswer"));

                            UserValidator userValidator = new UserValidator(user, request.getParameter("confirmPassword"));

                            if (userValidator.isValidUpdate()) {
                                // insert to db
                                if (UserUtil.updateUser(user) == 1) {
                                    message = "Update Success!";
                                    updateHomepage(session);
                                } else {
                                    message = "Server Error - Could not complete request";
                                }
                            } else {
                                // send error message back as parameter to signup.jsp
                                message = userValidator.errorMessage;
                            }
                        } catch (java.text.ParseException e) {
                            message = "Birthdate could not parsed correctly";
                        }

                        forwardUrl = "/profile.jsp";
                        break;

                    case "follow":
                        String toFollow = request.getParameter("whoToFollow");
                        FollowsUtil.insertFollow(user, toFollow);
                        forwardUrl = "/home.jsp";
                        updateHomepage(session);
                        break;

                    case "unFollow":
                        toFollow = request.getParameter("whoToFollow");
                        FollowsUtil.stopFollowing(user, toFollow);
                        forwardUrl = "/home.jsp";
                        updateHomepage(session);
                        break;
                    default:
                        message = "Unknown Server Error";
                        forwardUrl = "/home.jsp";
                }

            } catch (Exception e) {
                message = "Server Error: " + e.getMessage();
                forwardUrl = "/home.jsp";

            } finally {
                session.setAttribute("message", message);

                if (!forwardUrl.isEmpty()) {
                    getServletContext()
                            .getRequestDispatcher(forwardUrl)
                            .forward(request, response);
                }
            }
        }
    }

    boolean allMentionedUsersExist(Twit twit) {
        ArrayList<String> usernames = twit.getMentionedUsernames();
        boolean allUsersExist = true;

        try {
            for (int i = 0; i < usernames.size(); ++i) {
                allUsersExist = allUsersExist && (UserUtil.searchByUsername(usernames.get(i)) != null);
            }
        } catch (IOException | ClassNotFoundException e) {
            allUsersExist = false;
        }
        return allUsersExist;
    }

    void updateHomepage(HttpSession session) throws IOException, ClassNotFoundException {
        User user = (User) session.getAttribute("user");

        session.setAttribute("userTwits", getTwitsForUsername(user.getUsername()));
        session.setAttribute("numTwits", getNumUserTwits(user));
        session.setAttribute("allUsers", UserUtil.getAllUsers());
        session.setAttribute("listToFollow", FollowsUtil.getAllfollowing(user));
        session.setAttribute("following", FollowsUtil.getFollowing(user));
        session.setAttribute("followed", FollowsUtil.getFollowed(user));
        session.setAttribute("Notifications", NotificationUtil.getNotifications(user));
        session.setAttribute("trendingHashtags", getTrendingHashtags());
    }
}
