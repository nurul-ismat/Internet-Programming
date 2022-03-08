/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import jdbc.JdbcUtility;
import org.apache.commons.lang3.RandomStringUtils; //salt generation
import org.apache.commons.codec.digest.DigestUtils; //generate hash


/**
 *
 * @author GATES
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = {"/RegistrationServlet"})
public class RegistrationServlet extends HttpServlet {
    
    private JdbcUtility jdbcUtility;
    
    @Override
    //this method run once only for the servlet lifecycle
    //when the servlet loaded the first time in Glassfish/Tomcat
    public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";

        String dbName = "scsj3303";
        String url = "jdbc:mysql://localhost/" + dbName + "?";
        String userName = "root";
        String password = "";

        jdbcUtility = new JdbcUtility(driver,
                                      url,
                                      userName,
                                      password);

        jdbcUtility.jdbcConnect();
        
        //prepare the statement once only
        //for the entire servlet lifecycle
        jdbcUtility.prepareSQLStatementInsertUser();
    }

    @Override
    //this method run once only for the servlet lifecycle
    //when the servlet unloaded in application server (Glassfish/Tomcat)
    //or when the application server shutdown
    public void destroy() {   
        jdbcUtility.jdbcConClose();
    }     

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //generate random string for salt
	int length = 30;
        boolean useLetters = true;
        boolean useNumbers = true;
        String salt = RandomStringUtils.random(length, useLetters, useNumbers);        
        
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        String passwordHash = DigestUtils.sha512Hex(password + salt);        
        
        try {                    
            PreparedStatement ps = jdbcUtility.getPsInsertUser();
            
            ps.setString(1, name);
            ps.setString(2, login);
            ps.setString(3, passwordHash);
            ps.setString(4, salt);
            
            int insertStatus = 0;
            insertStatus = ps.executeUpdate();
            
            PrintWriter out = response.getWriter();
            
            if (insertStatus == 1) {
                out.println("<script>");
                out.println("    alert('User Registration Success');");
                out.println("    window.location = '/myapp/login.html'");
                out.println("</script>");
            }
        }
	catch (SQLException ex)
	{
            while (ex != null) {
                System.out.println ("SQLState: " + ex.getSQLState ());
                System.out.println ("Message:  " + ex.getMessage ());
		System.out.println ("Vendor:   " + ex.getErrorCode ());
                ex = ex.getNextException ();
		System.out.println ("");
            }
            
            PrintWriter out = response.getWriter();
            
            out.println("<script>");
            out.println("    alert('User Registration Failed');");
            out.println("    window.location = '/myapp/registration.html'");
            out.println("</script>");            
	}
	catch (java.lang.Exception ex)
	{
            ex.printStackTrace ();
            
            PrintWriter out = response.getWriter();
            
            out.println("<script>");
            out.println("    alert('User Registration Failed');");
            out.println("    window.location = '/myapp/registration.html'");
            out.println("</script>");
	}                  
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
