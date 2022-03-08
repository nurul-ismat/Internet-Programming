/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author GATES
 */
@WebServlet(urlPatterns = {"/TableServlet"})
public class TableServlet extends HttpServlet {

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
        
        String name = "James Bond";
        String image = "bond.jpg";
        int age = 27;
        
        String name2 = "John Wick";
        String image2 = "johnwick.jpg";
        int age2 = 30;
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TableServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TableServlet at " + request.getContextPath() + "</h1>");
          
            
            out.println("<table border='1'>");
            out.println("  <thead>");
            out.println("    <tr>");
            out.println("      <th>Name</th>");
            out.println("      <th>Image</th>");
            out.println("      <th>Age</th>");
            out.println("      <th>Details</th>");
            out.println("    </tr>");
            out.println("  </thead>");
            out.println("  <tbody>");
            out.println("    <tr>");
            out.println("      <td>" + name + "</td>");
            out.println("      <td><img src='img/" + image + "' width='100'/></td>");
            out.println("      <td>" + age + "</td>");
            out.println("      <td><a href='javascript:;'>View Details</a></td>");
            out.println("    </tr>");
            out.println("    <tr>");
            out.println("      <td>" + name2 + "</td>");
            out.println("      <td><img src='img/" + image2 + "' width='100'/></td>");
            out.println("      <td>" + age2 + "</td>");
            out.println("      <td><a href='javascript:;'>View Details</a></td>");
            out.println("    </tr>");             
            out.println("  </tbody>");
            out.println("</table>"); 
            
            
            out.println("<p><a href='index.html'>Go Back</a></p>");
           
            
            out.println("</body>");
            out.println("</html>");
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
