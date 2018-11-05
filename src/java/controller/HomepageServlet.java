/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import business.Twit;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static dataaccess.TwitUtil.*;
import static dataaccess.UserUtil.*;

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
            forwardUrl = "/home.jsp";

        } catch (IOException | ClassNotFoundException e) {
            message = "Server Error - Could not retrieve user twits";
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
                    case "createtwit":
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

                    case "deletetwit":
                        Twit twit = (Twit) session.getAttribute("twit");
                        deleteTwit(user, twit.getTwitId());

                        session.setAttribute("userTwits", getTwitsForUsername(user.getUsername()));

                        forwardUrl = "/home.jsp";
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
        if (usernames == null) {
            return allUsersExist;
        } else {
            try {
                for (int i = 0; i < usernames.size(); ++i) {
                    allUsersExist = allUsersExist && (searchByUsername(usernames.get(i)) != null);
                }
            } catch (IOException | ClassNotFoundException e) {
                allUsersExist = false;
            }

            return allUsersExist;
        }
    }
}
