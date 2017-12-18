package com.chat.tp;

import com.chat.modele.User;
import com.chat.service.GestionMessage;
import com.chat.service.GestionSalon;
import com.chat.service.GestionUtilisateur;
import com.chat.util.DataException;
import com.chat.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class Stockage
 *
 * @author Sofiaa Faddi
 */
@Controller
public class Stockage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Message.class.getName());
	private final Util util = new Util();

	@Autowired
	public GestionMessage gestionMessage;

	@Autowired
	public GestionUtilisateur gestionUtilisateur;

    @Autowired
    public GestionSalon gestionSalon;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Stockage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			this.getServletContext().getRequestDispatcher("/Init").forward(request, response);
		} catch (Exception e){
			LOGGER.log(Level.FINE, e.getMessage(), e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		String pseudo = util.getSessionAttribute(request, Init.USERNAME);
		String salon = util.getSessionAttribute(request, Init.CHANNEL);
		String message = request.getParameter("contenu");

		if ("".equals(pseudo)){
			util.redirectToIndex(response);
		}

		if (message != null){
			//ajouter message salon
			session.setAttribute("entre", request.getParameter("entre"));
			User user = gestionUtilisateur.getUserByPseudo(pseudo);
			try {
				gestionMessage.addMessage(message , user, salon);
			} catch (DataException e) {
				LOGGER.log(Level.FINE, e.getMessage(), e);
			}

		}
		request.setAttribute("salon", salon);
		RequestDispatcher dp = request.getRequestDispatcher("/restreint/interface.jsp");

		try {
			dp.forward(request, response);
		} catch (Exception e){
			LOGGER.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}
}