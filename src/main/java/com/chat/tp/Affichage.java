package com.chat.tp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class Affichage
 *
 * @author Sofiaa Faddi
 */
public class Affichage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Affichage.class.getName());


    /**
     * @see HttpServlet#HttpServlet()
     */
    public Affichage() {
        super();
    }

    /**
     * Intercepter la méthode get et faire la redirection
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // redirection vers servlet INIT
            this.getServletContext().getRequestDispatcher("/Init").forward(request, response);
        }catch (Exception e){
            LOGGER.log(Level.FINE, e.getMessage(), e);

        }

    }

    /**
     * Intercepter la méthode POST et faire la redirection
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // redirection vers la page Interface
        RequestDispatcher dpaffichage = getServletContext().getRequestDispatcher("/restreint/interface.jsp");
        try {
            dpaffichage.forward(request, response);
        }catch (Exception e){
            LOGGER.log(Level.FINE, e.getMessage(), e);
        }
    }

}
