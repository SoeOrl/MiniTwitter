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

import static dataaccess.DatabaseUtil.insertTwit;
import static dataaccess.DatabaseUtil.getTwitsForUsername;

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
            ArrayList<Twit> userTwits = getTwitsForUsername(user.getUsername());
            session.setAttribute("userTwits", userTwits);
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
                    case "twit":
                        String twitBody = request.getParameter("twit");

                        if (twitBody.length() > MAX_TWIT_LENGTH) {
                            message = "Twit length must be 280 characters or less";
                            forwardUrl = "/home.jsp";
                        } else {
                            Twit twit = new Twit(user.getUsername(), user.getFullName(), twitBody);
                            insertTwit(user, twit);
                        }

                        break;
                }
            } catch (Exception e) {

            }
        }
    }
}
