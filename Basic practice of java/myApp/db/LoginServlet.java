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
import org.apache.commons.codec.digest.DigestUtils; //generate hash
import bean.UserProfile;
import javax.servlet.http.HttpSession;

/**
 *
 * @author GATES
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    
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
        jdbcUtility.prepareSQLStatementSelectUserInfoByLogin();
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
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        try {
            
            //get the prepared statement that is needed for jdbcutility class
            PreparedStatement ps = jdbcUtility.getPsSelectUserInfoByLogin();

	    //set parameter and execute the statement
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            
            PrintWriter out = response.getWriter();
            
            UserProfile userProfile = null;

            //generate output for browser
            while (rs.next()) {
                
                String passwordHash = rs.getString("password"); 
                String salt = rs.getString("salt"); 
                String name = rs.getString("name");  
                String photo = rs.getString("photo");  
                
                userProfile = new UserProfile();
                userProfile.setLogin(login);
                userProfile.setPasswordHash(passwordHash);
                userProfile.setSalt(salt);
                userProfile.setName(name);
                userProfile.setPhoto(photo);
            } 
            
            if (userProfile == null) {
                out.println("<script>");
                out.println("    alert('Login failed - Username/password is incorrect!');");
                out.println("    window.location = '/myapp/login.html'");
                out.println("</script>");                
            } else {
                
                String generateHash = DigestUtils.sha512Hex(password + userProfile.getSalt());

                
                if (userProfile.getPasswordHash().equals(generateHash)) {
                    
                    //clear passwordHash and salt
                    userProfile.setPasswordHash("");
                    userProfile.setSalt("");
                    
                    //create session data
                    HttpSession session = request.getSession();
                    session.setAttribute("userprofile", userProfile);

                    out.println("<script>");
                    out.println("    alert('Login Succesful!');");
                    out.println("    window.location = '/myapp/HomeServlet'");
                    out.println("</script>");                    
                    
                } else { //password incorrect
                    out.println("<script>");
                    out.println("    alert('Login failed - Username/password is incorrect!');");
                    out.println("    window.location = '/myapp/login.html'");
                    out.println("</script>");                    
                }                
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
            out.println("    alert('Select user info failed');");
            out.println("</script>");            
	}
	catch (java.lang.Exception ex)
	{
            ex.printStackTrace ();
            
            PrintWriter out = response.getWriter();
            
            out.println("<script>");
            out.println("    alert('Select user info failed');");
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
