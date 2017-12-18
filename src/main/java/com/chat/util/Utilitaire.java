package com.chat.util;

import javax.servlet.http.Cookie;

/**
 * @author sofia faddi
 */
public class Utilitaire {

	 /**
     * Recherche le cookie "cookieCherche" et le renvoie
     * @param cookies un tableau de cookies
     * @param cookieCherche le cookie recherché
     * @return revoie le cookie si il existe, null sinon
     */
    public static Cookie getCookie(Cookie[] cookies, String cookieCherche){

        if(cookies != null){
            //recherche du bon cookie
            for (Cookie cooky : cookies) {
                if (cooky.getName().equals(cookieCherche))
                    return cooky;
            }
        }
        return null;
    }


}
