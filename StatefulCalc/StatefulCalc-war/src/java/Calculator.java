/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.calculator.stateful.CalcBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tangguh
 */
@WebServlet(urlPatterns = {"/Calculator"})
public class Calculator extends HttpServlet {

    CalcBeanLocal calcBean = lookupCalcBeanLocal();
    

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
        try {
            double total;
            String operasi = null;
            int count = 0;
            String operation = request.getParameter("op");
            if (!request.getParameter("value").isEmpty() && operation.equals("+") ) {
                total = calcBean.add(Double.parseDouble(request.getParameter("value")));
            }
            else if (!request.getParameter("value").isEmpty() && operation.equals("-")) {
                total = calcBean.min(Double.parseDouble(request.getParameter("value")));
            }
            else if (!request.getParameter("value").isEmpty() && operation.equals("/")) {
                total = calcBean.bagi(Double.parseDouble(request.getParameter("value")));
            }
            else if (!request.getParameter("value").isEmpty() && operation.equals("X")) {
                total = calcBean.kali(Double.parseDouble(request.getParameter("value")));
            }
            else if (operation.equals("CE")) {
                total = calcBean.remove();
                count = calcBean.removeCount();
            }
            else {
                total = calcBean.getTotal();
            }
            
            if (calcBean.getCount() != 0 && operation.equals("+")) {
                operation = " + ";
                operasi = String.valueOf(calcBean.operasi()) + operation + request.getParameter("value");
            }
            
            if (calcBean.getCount() != 0 && operation.equals("-")) {
                operation = " - ";
                operasi = String.valueOf(calcBean.operasi()) + operation + request.getParameter("value");
            }
            
            if (calcBean.getCount() != 0 && operation.equals("/")) {
                operation = " / ";
                operasi = String.valueOf(calcBean.operasi()) + operation + request.getParameter("value");
            }
            
            if (calcBean.getCount() != 0 && operation.equals("X")) {
                operation = " * ";
                operasi = String.valueOf(calcBean.operasi()) + operation + request.getParameter("value");
            }
            
            PrintWriter out = response.getWriter();
            
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            rd.include(request, response);
            
            if (calcBean.getCount() == 1 ) {
                out.println(request.getParameter("value") + operation + "<br />");
            }
            
            else if (calcBean.getCount() > 1 ) {
                out.println(operasi + "<br >");
                out.println("Total: " + total + "<br >");
            }
            
        } 
        
        catch (IOException | NumberFormatException | ServletException ex) {
            PrintWriter out = response.getWriter();
            out.println("Error: " + ex.getMessage() + "<br />Silahkan isi field dengan angka");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            rd.include(request, response);
        } 
        
        finally {
            PrintWriter out = response.getWriter();
            out.close();
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

    private CalcBeanLocal lookupCalcBeanLocal() {
        try {
            Context c = new InitialContext();
            return (CalcBeanLocal) c.lookup("java:global/StatefulCalc/StatefulCalc-ejb/CalcBean!com.calculator.stateful.CalcBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
