/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import business.UserValidator;
import business.SecurityQuestion;
import dataaccess.UserUtil;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Emailer;

import static dataaccess.UserUtil.searchByEmail;
import static dataaccess.UserUtil.searchByUsername;
import static dataaccess.UserUtil.insertUser;

public class MembershipServlet extends HttpServlet {

    /**
     * Allows JSP to reference security question strings
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        getServletContext().setAttribute("securityQuestions", SecurityQuestion.values());
    }

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

        String forwardUrl = "/login.jsp";
        getServletContext()
                .getRequestDispatcher(forwardUrl)
                .forward(request, response);
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
            switch (action) {
                case "signup":
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
                            if (insertUser(user) != 0) {
                                forwardUrl = "/home.jsp";
                                session.setAttribute("user", user);

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
                    break;

                case "login":
                    String identity = request.getParameter("identity");
                    String password = request.getParameter("password");

                    user = searchByUsername(identity);

                    if (user == null) {
                        user = searchByEmail(identity);
                    }

                    if (user == null || !user.getPassword().equals(password)) {
                        message = "Username/email or password are incorrect";
                        forwardUrl = "/login.jsp";
                    } else {
                        session.setAttribute("user", user);
                        response.sendRedirect("homepage");
                    }
                    break;

                case "logout":
                    user = null;
                    forwardUrl = "/login.jsp";
                    session.setAttribute("user", user);
                    break;

                case "forgotpw":
                    String email = request.getParameter("email");
                    int questionNo = Integer.parseInt(request.getParameter("questionNo"));
                    String answer = request.getParameter("answer");

                    user = UserUtil.searchByEmail(email);

                    if (user == null) {
                        message = "No user with that email exists";
                    } else if (user.getQuestionNo() == questionNo && (user.getAnswer().equals(answer))) {

                        try {
                            Emailer.sendForgotPasswordEmail(user);

                            message = String.format("Email with new password sent to %s", email);
                        } catch (MessagingException e) {
                            message = "Server Error - Could not send email";
                            message += "Error: " + e.getMessage();
                        }
                    }

                    forwardUrl = "/forgotpassword.jsp";
                    break;

                default:
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            message = "Server Error";
            forwardUrl = "/signup.jsp";

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
