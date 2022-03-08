/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author GATES
 */
@WebServlet(name = "VerifyHash", urlPatterns = {"/VerifyHash"})
public class VerifyHash extends HttpServlet {

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
        
        String password = request.getParameter("password");
        String hash = request.getParameter("hash");
        
        String salt = "pk33Jv49KrbSwP6LoiiNNu8Wct4AJ4";
        String generateHash = DigestUtils.sha512Hex(password + salt);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VerifyHash</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyHash at " + request.getContextPath() + "</h1>");
            
            if (hash.equals(generateHash)) {
                
                out.println("<p>Password is correct</p>");
                out.println("<p>Password: " + password + "</p>");
                out.println("<p>PassWord hash: " + generateHash + "</p>");
                out.println("<p>Original hash: " + hash + "</p>");
            } else {
                out.println("<p>Password is incorrect</p>");
                out.println("<p>Passord: " + password + "</p>");
                out.println("<p>PassWord hash: " + generateHash + "</p>");
                out.println("<p>Original hash: " + hash + "</p>");
            }
            
            
            
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
