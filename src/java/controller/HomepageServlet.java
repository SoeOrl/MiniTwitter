/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import business.Twit;
import business.UserValidator;
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

/**
 *
 * @author jdodso227
 */
public class HomepageServlet extends HttpServlet {

    private final static int MAX_TWIT_LENGTH = 280;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String message = "";
        String forwardUrl = "";

        try {
            session.setAttribute("userTwits", getTwitsForUsername(user.getUsername()));
            session.setAttribute("numTwits", getNumUserTwits(user));
            session.setAttribute("allUsers", UserUtil.getAllUsers());
            forwardUrl = "/home.jsp";

        } catch (IOException | ClassNotFoundException e) {
            message = "Server Error - Could not retrieve your twits or other users' information";
            forwardUrl = "/login.jsp";
        }

        getServletContext()
                .getRequestDispatcher("/home.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String forwardUrl = "";
        String message = "";

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

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

                                session.setAttribute("userTwits", getTwitsForUsername(user.getUsername()));
                            } else {
                                message = "One or more mentioned users do not exist";
                            }
                        }

                        break;

                    case "deleteTwit":
                        Twit twit = (Twit) session.getAttribute("twit");
                        deleteTwit(user, twit.getTwitId());

                        session.setAttribute("userTwits", getTwitsForUsername(user.getUsername()));

                        forwardUrl = "/home.jsp";
                        break;

                    case "updateProfile":
                        try {
                            user.setFullName(request.getParameter("fullName"));
                            user.setEmail(request.getParameter("email"));
                            user.setUsername(request.getParameter("username"));
                            user.setPassword(request.getParameter("password"));
                            user.setBirthdate(request.getParameter("birthdate"));
                            user.setQuestionNo(Integer.parseInt(request.getParameter("questionNo")));
                            user.setAnswer(request.getParameter("answer"));

                            UserValidator userValidator = new UserValidator(user, request.getParameter("confirmPassword"));

                            if (userValidator.isValid()) {
                                // insert to db
                                if (UserUtil.updateUser(user) == 1) {
                                    message = "Update Success!";
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
                    default:
                        message = "Unknown Server Error";
                        forwardUrl = "/home.jsp";
                }

            } catch (Exception e) {
                message = e.getMessage();
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

            return allUsersExist;
        }
    }
}
