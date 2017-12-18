package com.chat.service;

import com.chat.modele.Salon;
import com.chat.modele.User;
import com.chat.rest.Utilisateur;
import com.chat.util.Constante;
import com.chat.util.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Cette classe garde toutes les méthodes sur l'inscription d'utilisateurs
 *
 * @author SOFIAA FADDI
 * @version 1.0
 * @since 1.0 11/23/16.
 */
@Service
public class ChatUtilisateurService implements GestionUtilisateur {

    private static final Logger LOGGER = Logger.getLogger(ChatUtilisateurService.class.getName());
    private List<User> userList = new ArrayList<>();

    @Autowired
    private GestionMessage gestionMessage;

    @Override
    public void addUser(String pseudo, String prenom, String nom, String mail) throws DataException {
        User user = new User(pseudo, prenom, nom, mail);
        if (!existsUsername(pseudo))
            if (!existsMail(mail))
                userList.add(user);
            else
                throw new DataException(Constante.MAIL_ALREADY_EXISTS);
        else
            throw new DataException(Constante.USERNAME_ALREADY_EXISTS);
    }

    @Override
    public void addSalonUser(Salon salon, String pseudo){
        User user = getUserByPseudo(pseudo);

        if (!user.getSalonList().stream().filter(s -> s.getName()
                .equals(salon.getName())).findFirst().isPresent()) {
            user.getSalonList().add(salon);
        }
    }

    @Override
    public boolean existsMail(String mail) {

        return userList.stream().filter(u -> u.getMail().equals(mail))
                .findFirst().isPresent();
    }

    @Override
    public boolean existsUsername(String pseudo) {
        return userList.stream().filter(u -> u.getPseudo().equals(pseudo))
                .findFirst().isPresent();
    }

    @Override
    public User getUserByPseudo(String pseudo) {
        return userList.stream().filter(u -> u.getPseudo().equals(pseudo))
                .findFirst().orElse(null);
    }

    @Override
    public User updateUser(String pseudo, User user) throws DataException {
        User exists = getUserByPseudo(user.getPseudo());
        if (exists != null) {
            Optional<User> first = userList.stream().filter(u ->
                    u.getPseudo().equals(pseudo)
            ).findFirst();

            if (!first.isPresent()) {
                exists.setPseudo(pseudo);
                return exists;

            } else {
                throw new DataException(Constante.USERNAME_ALREADY_EXISTS);
            }
        } else {
            throw new DataException(Constante.USER_NOT_EXISTS);
        }
    }

    @Override
    public Utilisateur getAllChannelsByUser(String pseudo) throws DataException {
        User user = getUserByPseudo(pseudo);
        if (user != null) {
            return newUtilisateur(user);
        } else {
            throw new DataException(Constante.USER_NOT_EXISTS);
        }
    }

    @Override
    public List<Salon> getSalonByUser(String pseudo) {
        return getUserByPseudo(pseudo).getSalonList();
    }


    @Override
    public Utilisateur newUtilisateur(User user) {
        Utilisateur utilisateur = new Utilisateur(user.getPseudo(), user.getPrenom(), user.getNom(), user.getMail());
        utilisateur.setEtat(getUserByPseudo(user.getPseudo()).getEtat());
        utilisateur.setSalons(getSalonByUser(user.getPseudo()));

        return utilisateur;
    }
}