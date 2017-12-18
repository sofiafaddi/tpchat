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
 *
 * Servlet implementation class Message
 *
 * @author Sofiaa Faddi
 */
public class Message extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Message.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Message() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = (String)request.getSession().getAttribute("pseudo");
		String salon = (String)request.getSession().getAttribute("sallon");

		if(pseudo == null || salon == null){
			try {
                response.sendRedirect("index.jsp");
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
		RequestDispatcher dp =getServletContext().getRequestDispatcher("/restreint/message.jsp");
		try{
            dp.forward(request, response);
        }catch (Exception e){
            LOGGER.log(Level.FINE, e.getMessage(), e);
        }
	}

}
