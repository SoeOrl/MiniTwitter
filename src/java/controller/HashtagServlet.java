/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
/**
 *
 * @author jdodso227
 */
public class HashtagServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String message = "";
        String forwardUrl = "/home.jsp";

        try {
            forwardUrl = "/hashtag.jsp";
            String hashtag = request.getParameter("tag");
            session.setAttribute("hashtag", hashtag);
            session.setAttribute("hashtagTwits", getAllTwitsForHashtag(hashtag));

        } catch (ClassNotFoundException e) {
            message = e.getMessage();
            forwardUrl = "/login.jsp";
        }

        getServletContext()
                .getRequestDispatcher(forwardUrl)
                .forward(request, response);
    }
}
