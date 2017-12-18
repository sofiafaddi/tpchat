package com.chat.rest;

import com.chat.modele.User;
import com.chat.service.GestionUtilisateur;
import com.chat.util.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Sofiaa FADDI
 * @version 1.0
 * @since 1.0 12/5/16
 */
@RestController
public class UserResource {
    private static final Logger LOGGER = Logger.getLogger(UserResource.class.getName());

    private GestionUtilisateur gestionUtilisateur;

    @Autowired
    public UserResource(GestionUtilisateur gestionUtilisateur) {
        this.gestionUtilisateur = gestionUtilisateur;
    }

    /**
     *
     * Cette méthode correspond à l'ennoncé: Récupérer le pseudo et la liste des salons auxquels il a participé
     *
     * @param username le pseudo de l'utilisateur
     * @return l'entité de l'utilisateur
     */
    @RequestMapping(value = "/users/{username}", produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @ResponseBody
    public Utilisateur getUser(@PathVariable String username, HttpServletResponse response) {
        Utilisateur utilisateur = null;
        /* TODO liste de salons */
        try {
            utilisateur = gestionUtilisateur.getAllChannelsByUser(username);
        }catch (DataException e){
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }catch (Exception e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return utilisateur;
    }

    /**
     *
     * Cette méthode correspond à l'ennoncé: Modifier le pseudo de l'utilisateur
     *
     * @param username le nouveau pseudo de l'utilisateur
     * @param user l'entite user
     * @return l'entité de l'utilisateur
     */
    @RequestMapping(value = "/users/{username}", produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT)
    @ResponseBody
    public Utilisateur modifyUsername(@PathVariable String username, @RequestBody User user,
                                      HttpServletResponse response) {

        Utilisateur utilisateur = null;
        try {
            user = gestionUtilisateur.updateUser(username, user);
            utilisateur = gestionUtilisateur.newUtilisateur(user);
        } catch (DataException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }catch (Exception e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return utilisateur;
    }
}
