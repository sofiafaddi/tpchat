package com.chat.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SOFIAA FADDI
 * @version 1.0
 * @since 1.0 12/5/16.
 */
public class Util {
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());

    public String getSessionAttribute(HttpServletRequest request, String attribute){
        HttpSession session = request.getSession();
        if (session.getAttribute(attribute) != null){
            return session.getAttribute(attribute).toString();
        }else{
            return "";
        }
    }

    public void redirectToIndex(HttpServletResponse response){
        try{
            response.sendRedirect("index.jsp");
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
