/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Twit;
import business.User;
import business.UserValidator;
import dataaccess.FollowsUtil;
import dataaccess.UserUtil;
import dataaccess.HashtagUtil;
import dataaccess.TwitUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static dataaccess.HashtagUtil.getAllTwitsForHashtag;
import static dataaccess.HashtagUtil.getTrendingHashtags;
import static dataaccess.TwitUtil.deleteTwit;
import static dataaccess.TwitUtil.insertTwit;
import dataaccess.UserUtil;
import java.util.ArrayList;
/**
 *
 * @author jdodso227
 */
public class SearchServlet extends HttpServlet {
    
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
                    case "search":
                        String query = request.getParameter("query");
                        forwardUrl = "/search.jsp";

                        session.setAttribute("query", query);
                        session.setAttribute("users", UserUtil.findAllUsers(query));
                        session.setAttribute("twits", TwitUtil.findAllTwits(query));
                        session.setAttribute("hashtags", HashtagUtil.findAllHashtags(query));
                        
                        break;

                    default:
                        message = "Unknown Server Error";
                        forwardUrl = "/search.jsp";
                }

            } catch (Exception e) {
                message = "Server Error: " + e.getMessage();
                forwardUrl = "/search.jsp";

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
}