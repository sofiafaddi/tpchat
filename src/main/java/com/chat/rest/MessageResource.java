package com.chat.rest;

import com.chat.modele.Message;
import com.chat.service.ChatMessageService;
import com.chat.service.GestionMessage;
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
 * @since 1.0 12/4/16
 */
@RestController
public class MessageResource {

    private static final Logger LOGGER = Logger.getLogger(MessageResource.class.getName());

    private GestionMessage gestionMessage;


    @Autowired
    public MessageResource(ChatMessageService gestionMessage) {
        this.gestionMessage = gestionMessage;
    }


    /**
     *
     * Cette méthode corresponde à l'ennoncé: Récupérer les informations du message (auteur, texte)
     *
     * @param id l'identificateur du message
     * @param response la reponse http
     * @return le id-ième message
     */
    @RequestMapping(value = "/messages/{id}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Message getMessage(@PathVariable Long id, HttpServletResponse response) {
        Message message = null;
        try {
            message = this.gestionMessage.getMessage(id);
        } catch (DataException e) {
            LOGGER.log(Level.INFO, "Message not found [id:" + id + "]", e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return message;
    }

    /**
     *
     * Cette méthode corresponds à l'ennoncé: Modifier le contenu du dernier message d'un salon (doit renvoyer une
     * erreur si ce n'est pas le dernier message)
     *
     * @param salon le nom du salon
     * @param id l'identificateur du message
     * @param message le nouveau contenu du message
     * @return le message actualisé
     */
    @RequestMapping(value = "/messages/{salon}/{id}", method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Message updateLastMessage(@PathVariable String salon, @PathVariable Long id, @RequestBody Message message,
                                     HttpServletResponse response) {

        Message lastMessage = null;
        try {
            lastMessage = gestionMessage.updateLastMessage(salon, message);
        } catch (DataException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e){
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return lastMessage;
    }

    /**
     *
     * Cette méthode corresponds à l'ennoncé: Supprimer le dernier message d'un salon (doit renvoyer une erreur
     * si ce n'est pas le dernier message)
     *
     * @param salon le nom du salon
     * @param id l'identificateur du message
     * @param response le reponse http
     */
    @RequestMapping(value = "/messages/{salon}/{id}", method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void deleteDernierMessage(@PathVariable String salon, @PathVariable Long id, HttpServletResponse response) {
        try{
            Message lastMessage = gestionMessage.getDernierMessage(salon);
            if (lastMessage.getId().equals(id)) {
                gestionMessage.deleteMessage(salon, id);
            }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (DataException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e){
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

}
