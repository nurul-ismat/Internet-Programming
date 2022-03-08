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

/**
 *
 * @author GATES
 */
@WebServlet(name = "SelectStudentServlet", urlPatterns = {"/SelectStudentServlet"})
public class SelectStudentServlet extends HttpServlet {

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
        
        //prepare DB credential
        String driver = "com.mysql.jdbc.Driver";
        String dbName = "scsj3303";
        String url = "jdbc:mysql://localhost/" + dbName + "?";
        String userName = "root";
        String password = "";  
        Connection con = null;
        
        //make DB connection using credential
        try {
            Class.forName (driver);
            con = DriverManager.getConnection(url, userName, password);
            DatabaseMetaData dma = con.getMetaData ();
            System.out.println("\nConnected to " + dma.getURL());
            System.out.println("Driver       " + dma.getDriverName());
            System.out.println("Version      " + dma.getDriverVersion());
            System.out.println("");
	}
	catch (SQLException ex){
            while (ex != null)
            {
		System.out.println("SQLState: " + ex.getSQLState ());
                System.out.println("Message:  " + ex.getMessage ());
		System.out.println ("Vendor:   " + ex.getErrorCode ());
                ex = ex.getNextException ();
		System.out.println ("");
            }

            System.out.println("Connection to the database error");
	}
	catch (java.lang.Exception ex) {
            ex.printStackTrace ();
	}   
        
        //create SQL statement    
        String sqlSelectAllStudent = "SELECT * FROM students";

        //create prepared statement for insert using connection
        //and the SQL string above
        //then execute 
        try {
            PreparedStatement psSelectAllStudent = con.prepareStatement(sqlSelectAllStudent);
            
            ResultSet rs = psSelectAllStudent.executeQuery();
            
            PrintWriter out = response.getWriter();
            
            out.println("<p><a href='/javadb/insertstudent.html'>Insert Student</a></p>");
            
            out.println("<table border='1'>");
            out.println("  <thead>");
            out.println("    <tr>");
            out.println("      <th>Name</th>");
            out.println("      <th>Matriks</th>");
            out.println("      <th>Age</th>");
            out.println("      <th>Photo</th>");
            out.println("      <th>Operations</th>");
            out.println("    </tr>");
            out.println("  </thead>");
            out.println("  <tbody>");            
            
            while (rs.next()) {
                
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String matriks = rs.getString("matriks");                
                int age = rs.getInt("age");
                String photo = rs.getString("photo");
                
                out.println("    <tr>");
                out.println("      <td>" + name + "</td>");
                out.println("      <td>" + matriks + "</td>");
                out.println("      <td>" + age + "</td>");
                out.println("      <td><img src='img/" + photo + "' width=100 /></td>");
                out.println("      <td><a href='StudentUpdateFormServlet?id=" + id + "'>Update</a><br /><a href='StudentDeleteServlet?id=" + id + "'>Delete</a></td>");
                out.println("    </tr>");
            } 
            
            out.println("  </tbody>");
            out.println("</table>");            
        }
        catch (Exception ex) {    
            ex.printStackTrace ();
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
