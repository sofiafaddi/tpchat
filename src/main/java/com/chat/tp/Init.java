package com.chat.tp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class Init
 *
 * @author sofia faddi
 * @version 1.1
 */
public class Init extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Init.class.getName());
    public static final String USERNAME = "pseudo";
    public static final String CHANNEL = "salon";
    private static final String TRUE = "true";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Init() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deconnexion = request.getParameter("deco");
		HttpSession session = request.getSession();

		if(deconnexion != null && deconnexion.equals(TRUE)){
			session.invalidate();
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            try {
                dispatcher.forward(request, response);
            }catch (Exception e){
                LOGGER.log(Level.FINE, e.getMessage(), e);
            }
		}

		String pseudo = session.getAttribute(USERNAME).toString();
		if ( pseudo == null ) {

			/* Redirection vers la page publique */
			try {
                response.sendRedirect("../index.jsp");
            }catch (Exception e){
                LOGGER.log(Level.FINE, e.getMessage(), e);
            }
		}else{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/restreint/interface.jsp");
			try {
                dispatcher.forward(request, response);
            }catch (Exception e){
                LOGGER.log(Level.FINE, e.getMessage(), e);
            }
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// recupérer le pseudo et le salon depuis le formulaire index
        String salon = request.getParameter(CHANNEL);
        String pseudo = request.getParameter(USERNAME);

        // si null redirection vers la page index.jsp
        if (pseudo == null|| session == null || salon == null) {
            try {
                this.doGet(request, response);
            }catch (Exception e){
                LOGGER.log(Level.FINE, e.getMessage(), e);
            }
        }
        else {
		
			try {
				/* Mise en session d'une chaîne de caractères */
				session.setAttribute(USERNAME, pseudo );
				session.setAttribute("salon", salon);
				
				RequestDispatcher dispatcher =getServletContext().getRequestDispatcher("/restreint/interface.jsp");
				dispatcher.forward(request, response);
	
			} catch (Exception e) {
                LOGGER.log(Level.FINE, e.getMessage(), e);
			}
			
	}
	}
}
	
	
	
	


