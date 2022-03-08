/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpSession;
import jdbc.JDBCUtility;

/**
 *
 * @author U
 */
@WebServlet(name = "StudentRegistrationServlet", urlPatterns = {"/StudentRegistrationServlet"})
public class StudentRegistrationServlet extends HttpServlet {
    
    private JDBCUtility jdbcUtility;
    private Connection con;
    
    public void init() throws ServletException
    {
        String driver = "com.mysql.jdbc.Driver";

        String dbName = "scsj3303";
        String url = "jdbc:mysql://localhost/" + dbName + "?";
        String userName = "root";
        String password = "";

        jdbcUtility = new JDBCUtility(driver,
                                      url,
                                      userName,
                                      password);

        jdbcUtility.jdbcConnect();
        con = jdbcUtility.jdbcGetConnection();
    }         

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        //get form data from VIEW > V-I
        String name = request.getParameter("name");
        String matriks = request.getParameter("matriks");
        int age = Integer.parseInt(request.getParameter("age"));
        
        PreparedStatement preparedStatementInsert = null;
        String sqlInsert = "INSERT INTO students(matriks, name, age, addeddate) VALUES(?, ?, ?, NOW())";
        boolean insertSuccess = false;
        
        try {
            preparedStatementInsert = con.prepareStatement(sqlInsert);
           
            preparedStatementInsert.setString(1, matriks);
            preparedStatementInsert.setString(2, name);
            preparedStatementInsert.setInt(3, age);
           
            // execute insert SQL stetement
            preparedStatementInsert.executeUpdate();
            
            if (preparedStatementInsert != null)
            {
                preparedStatementInsert.close();
                insertSuccess = true;
            }            
        }
        catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }       
        
        if (insertSuccess)
        {
            //retrieve the new data from DB
            PreparedStatement preparedStatementSelect = null;
            String sqlQuery = "SELECT * FROM students where matriks = ?";
        
            try
            {
                preparedStatementSelect = con.prepareStatement(sqlQuery);
                preparedStatementSelect.setString(1, matriks);
                ResultSet rs = preparedStatementSelect.executeQuery();
                
                while (rs.next()) 
                {
                    matriks = rs.getString("matriks");
                    name = rs.getString("name");
                    age = rs.getInt("age");
                
                    //create beans
                    Student std = new Student();
                    std.setName(name);
                    std.setMatriks(matriks);
                    std.setAge(age);
                
                    //add beans into request
                    //request.setAttribute("student", std);
                    
                    //add beans into session
                    session.setAttribute("student", std);
                    
                }
                
                if (preparedStatementSelect != null)
                {
                    preparedStatementSelect.close();
                    
                    //redirect to VIEW > V-O
                    //sendPage(request, response, "/view.jsp");
                    //sendPage(request, response, "/view2.jsp");
                    sendPage(request, response, "/view3.jsp");
                }
            }
            catch (java.lang.Exception ex)
            {
                ex.printStackTrace ();
            }
        }
    }
    
    void sendPage(HttpServletRequest req, HttpServletResponse res, String fileName) throws ServletException, IOException
    {
        // Get the dispatcher; it gets the main page to the user
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(fileName);

	if (dispatcher == null)
	{
            System.out.println("There was no dispatcher");
	    // No dispatcher means the html file could not be found.
	    res.sendError(res.SC_NO_CONTENT);
	}
	else
	    dispatcher.forward(req, res);
    }    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
