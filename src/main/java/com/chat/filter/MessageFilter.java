package com.chat.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet Filter implementation class MessageFilter
 *
 * @author sofiaa faddi
 */
public class MessageFilter implements Filter {

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        /* On n'ajout pas rien ici.*/
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
		
		 /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

		/*
		 * Si l'objet utilisateur n'existe pas dans la session en cours, alors
		 * l'utilisateur n'est pas connecté il sera rédirgé vers la page index.jsp.
		 */
        if (session.getAttribute("pseudo") == null) {
		 /* Redirection vers la page publique */
            response.sendRedirect("../index.jsp");
        } else {
		 /* Affichage de la page restreinte */
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        /* On n'ajout pas rien ici.*/
    }

}
