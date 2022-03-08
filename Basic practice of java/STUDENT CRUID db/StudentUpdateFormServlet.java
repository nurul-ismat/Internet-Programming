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
@WebServlet(name = "StudentUpdateFormServlet", urlPatterns = {"/StudentUpdateFormServlet"})
public class StudentUpdateFormServlet extends HttpServlet {

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
        
        String strId = request.getParameter("id");
        int id = Integer.parseInt(strId);
        
        //create SQL statement    
        String sqlSelectStudentById = "SELECT * FROM students" +
                                      " WHERE id = ?";
        
        try {
            PreparedStatement psSelectStudentById = con.prepareStatement(sqlSelectStudentById);
            
            psSelectStudentById.setInt(1, id);            
            ResultSet rs = psSelectStudentById.executeQuery();
            
            PrintWriter out = response.getWriter();

            while (rs.next()) {
                
                String name = rs.getString("name");
                String matriks = rs.getString("matriks");                
                int age = rs.getInt("age");
                
                out.println("        <form method='post' action='StudentUpdateServlet'>");
                out.println("            <label>Please enter name:");
                out.println("            <input name='name' type='text' value='" + name + "' required/>");
                out.println("            </label>");
                out.println("            <br />");
                out.println("            <br />");
                out.println("            <label>Please enter matriks:");
                out.println("            <input name='matriks' type='text' value='" + matriks + "' disabled/>");
                out.println("            </label>");
                out.println("            <br />");
                out.println("            <br />");
                out.println("            <label>Please enter age:");
                out.println("            <input name='age' type='text' value='" + age + "' required/>");
                out.println("            </label>");
                out.println("            <br />");
                out.println("            <br />");
                out.println("            <label>");
                out.println("            <input type='hidden' name='id' value='" + id + "'>");
                out.println("            <input type='submit' name='Submit' value='Submit' />");
                out.println("            </label>");            
                out.println("        </form>");                
            }            

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
