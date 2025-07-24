/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.learn.mycart.servlets;

import com.learn.mycart.entities.User;
import com.learn.mycart.helper.FactoryProvider;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author GAGANA K G
 */
public class RegisterServlet extends HttpServlet{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try{

                          String userName=request.getParameter("user_name");
                          String userEmail=request.getParameter("user_email");
                          String userPassword=request.getParameter("user_password");
                          String userPhone=request.getParameter("user_phone");
                          String userAddress=request.getParameter("user_address");

                          //can validations

                          if(userName.isEmpty())
                          {
                              out.println("Name is blank");
                              return;
                          }

                          //creating user object to store data

                          User user=new User(userName, userEmail,userPassword, userPhone,"default.jpg", userAddress,"normal" );


                          Session hibernateSession=FactoryProvider.getFactory().openSession();

                          Transaction tx=hibernateSession.beginTransaction();

                          int userId=(int)hibernateSession.save(user);

                          tx.commit();
                          hibernateSession.close();

                          HttpSession httpSession=request.getSession();
                          httpSession.setAttribute("message","Registration Successful! User Id is "+userId);

                          response.sendRedirect("register.jsp");
                          return;

                      }catch(Exception e){
                              e.printStackTrace();

                              //add other exceptions..unique
                      }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
