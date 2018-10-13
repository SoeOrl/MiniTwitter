/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import business.UserValidator;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static dataaccess.UserDB.insert;
import static dataaccess.UserDB.searchByEmail;
import static dataaccess.UserDB.searchByUsername;

@WebServlet(name = "membershipServlet", urlPatterns = {"/membership"})
public class membershipServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String forwardUrl = "";
        String message = "";
        User user = null;

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if (action.equals("signup")) {
                user = new User();

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
                        if (insert(user) != 0) {
                            forwardUrl = "/home.jsp";
                        } else {
                            message = "Server Error - Could not complete request";
                            forwardUrl = "/signup.jsp";
                        }
                    } else {
                        // send error message back as parameter to signup.jsp
                        message = userValidator.errorMessage;
                        forwardUrl = "/signup.jsp";
                    }
                } catch (java.text.ParseException e) {
                    message = "Birthdate could not parsed correctly";
                    forwardUrl = "/signup.jsp";
                }
            } else if (action == "login") {

                String identity = request.getParameter("identity");
                String password = request.getParameter("password");

                user = searchByUsername(identity);
                
                if(user == null) {
                    user = searchByEmail(identity);
                }
                
                if(user == null || !user.getPassword().equals(password)) {
                    message = "Username/email or password are incorrect";
                    forwardUrl = "/login.jsp";
                } else {
                    forwardUrl = "/home.jsp";
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            message = "Server Error";
            forwardUrl = "/signup.jsp";
            
        } finally {
            session.setAttribute("message", message);
            session.setAttribute("user", user);

            getServletContext()
                    .getRequestDispatcher(forwardUrl)
                    .forward(request, response);
        }
    }
}
