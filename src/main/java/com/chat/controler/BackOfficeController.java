package com.chat.controler;

import com.chat.modele.Message;
import com.chat.modele.User;
import com.chat.service.GestionMessage;
import com.chat.service.GestionSalon;
import com.chat.service.GestionUtilisateur;
import com.chat.tp.Init;
import com.chat.util.Constante;
import com.chat.util.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Sofiaa FADDI
 * @version 1.0
 * @since 1.0 11/18/16.
 */
@Controller
@RequestMapping("/")
public class BackOfficeController {

    private static final Logger LOGGER = Logger.getLogger(BackOfficeController.class.getName());

    private GestionMessage gestionMessage;

    private GestionUtilisateur gestionUtilisateur;

    private GestionSalon gestionSalon;

    /**
     *
     * Constructeur pour l'instantiation des classes
     *
     * @param gestionMessage l'instance de la gestion de messages
     * @param gestionUtilisateur l'instance de la gestion d'utilisateurs
     * @param gestionSalon       l'instance de la gestions des salons
     */
    @Autowired
    public BackOfficeController(GestionMessage gestionMessage,
                                GestionUtilisateur gestionUtilisateur,
                                GestionSalon gestionSalon) {
        this.gestionMessage = gestionMessage;
        this.gestionUtilisateur = gestionUtilisateur;
        this.gestionSalon = gestionSalon;
    }

    /**
     *
     * Cette méthode inscrit un nouveau utilisateur
     *
     * @param pseudo le pseudo de l'utilisateur
     * @param name le prenom de l'utilisateur
     * @param lastName le nom de l'utilisateur
     * @param mail le mail de l'utilisateur
     * @param model le modele
     * @return à la vue initiale
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "username") String pseudo,
                          @RequestParam(value = "name") String name,
                          @RequestParam(value = "lastName") String lastName,
                          @RequestParam(value = "mail") String mail,
                          Model model) {

        try {
            gestionUtilisateur.addUser(pseudo, name, lastName, mail);

            model.addAttribute("msg", Constante.CORRECT_INSCRIPTION);
            model.addAttribute("username", pseudo);
            return "redirect:/index.jsp";

        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);

            model.addAttribute("name", name);
            model.addAttribute("lastName", lastName);
            model.addAttribute("msg", e.getMessage());

            return "redirect:/inscription.jsp";
        }

    }

    /**
     *
     * Cette méthode retourne la liste des messages du salon
     *
     * @param modelMap le modele
     * @param salon le salon auquel l'utilisateur est connecté
     * @return à la page d'affichage
     */
    @RequestMapping(value = "/{salon}", method = RequestMethod.GET)
    public String listMessages(ModelMap modelMap, @PathVariable String salon) {

        List<Message> messages;
        try {
            messages = gestionMessage.getMessages(salon);
        } catch (DataException e) {
            LOGGER.log(Level.OFF, e.getMessage(), e);
            messages = new ArrayList<>();
        }
        modelMap.put("messages", messages);

        return "restreint/affichage";
    }

    /**
     *
     * Cette méthode retorune le contenu du messages selon le
     * salon et le nombre du message
     *
     * @param modelMap le modele
     * @param salon le salon auquel l'utilisateur est connecté
     * @param num le nombre de messages
     * @return à la page d'affichage
     */
    @RequestMapping(value = "/{salon}/{num}", method = RequestMethod.GET)
    public String listMessages(ModelMap modelMap,
                               @PathVariable String salon,
                               @PathVariable Integer num) {

        List<Message> messages;
        try {
            messages = gestionMessage.getMessages(salon);

            Message message = messages.get(num);
            modelMap.put("message", message == null ? "" : message);
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
        }

        return "restreint/affichage";
    }

    /**
     *
     * Cette méthode retorune la liste d'utilisateurs pour salon
     *
     * @param modelMap le modele
     * @param salon le salon auquel l'utilisateur est connecté
     * @param request la rêquete
     * @return à la vue d'utilisateurs
     */
    @RequestMapping(value = "/user/{salon}", method = RequestMethod.GET)
    public String listUsers(ModelMap modelMap,
                            @PathVariable String salon,
                            HttpServletRequest request) {

        HttpSession session = request.getSession();
        String pseudo = session.getAttribute(Init.USERNAME).toString();


        List<User> userList;
        try{
            userList = gestionMessage.getUserList(salon, pseudo);
        }catch (DataException de){
            LOGGER.log(Level.FINE, de.getMessage(), de);
            userList = new ArrayList<>();
            LOGGER.fine("Empty userList");
        }
        modelMap.addAttribute("users", userList);

        return "restreint/listuser";
    }

    /**
     *
     * Cette méthode ajoute l'utilisateurs au salon et le mets
     * dans l'etat ONLINE
     *
     * @param pseudo le pseudo de l'utilisateur
     * @param salon le salon auquel l'utilisateur est connecté
     * @param request la rêquete
     * @param model le modele
     * @return à la vue d'interface
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "username") String pseudo,
                        @RequestParam(value = "channel") String salon,
                        HttpServletRequest request,
                        Model model) {

        if (!gestionUtilisateur.existsUsername(pseudo)) {
            model.addAttribute("msg", Constante.USER_NOT_EXISTS);
            return "redirect:/inscription.jsp";
        }

        HttpSession session = request.getSession();
        session.setAttribute(Init.USERNAME, pseudo);
        session.setAttribute(Init.CHANNEL, salon);

        try {
            gestionSalon.addSalon(salon);
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
        }
        try {
            /* On ajoute l'utilisateur à la liste d'utilisateurs
            * connectés dans le salon, mais aussi on ajoute le salon
            * dans la liste de salon visités pour l'utilisateur */
            gestionMessage.addUserToSalon(pseudo, salon);
        }catch (DataException e ){
            LOGGER.log(Level.FINE, e.getMessage(), e);
        }

        gestionUtilisateur.getUserByPseudo(pseudo).setEtat(User.Status.ONLINE);

        return "redirect:/restreint/interface.jsp";
    }

    /**
     *
     * Cette méthode fait la déconnexion de la page et mets l'utilisateur
     * au OFFLINE
     *
     * @param request la rêquete
     * @return à la page initiale
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String pseudo = getSessionAttribute(request, Init.USERNAME);
        String salon = getSessionAttribute(request, Init.CHANNEL);

        if (!"".equals(pseudo)) {
            gestionUtilisateur.getUserByPseudo(pseudo).setEtat(User.Status.OFFLINE);
            gestionMessage.removeUserToSalon(pseudo, salon);
            session.invalidate();
        }

        return "redirect:/index.jsp";
    }

    private String getSessionAttribute(HttpServletRequest request, String attribute){
        HttpSession session = request.getSession();
        if (session.getAttribute(attribute) != null){
            return session.getAttribute(attribute).toString();
        }else{
            return "";
        }
    }

}