package com.chat.service;

import com.chat.modele.Message;
import com.chat.modele.Salon;
import com.chat.modele.User;
import com.chat.util.DataException;

import java.util.List;

/**
 * @author Sofia Faddi
 * @version 1.0
 * @since 1.0 11/24/16
 */
public interface GestionMessage {
    /**
     * Méthode qui permet d'ajouter un message à un salon
     *
     * @param contenu le contenu du message
     * @param user  l'entité de l'utilisateur
     * @param salon   le salon du chat
     */
    Message addMessage(String contenu, User user, String salon) throws DataException;

    /**
     * Cette méthode permet d'ajouter un salon
     *
     * @param salon le salon du chat
     */
    void addSalon(String salon) throws DataException;

    /**
     * Cette méthode permet de recupérer un message en fonction du salon
     *
     * @param salon le salon du chat
     * @return la liste de messages de ce salon là.
     */
    List<Message> getMessages(String salon) throws DataException;

    /**
     * cette méthode permet de supprimer un message d'un salon
     *
     * @param salon le nom du salon
     */
    void supprimerMessages(String salon) throws DataException;


    /**
     * Cette méthode permet de récuperer le nombre de message d'un salon
     *
     * @param salon le nom du salon
     * @return nombre de messages
     */
    int nombreMessage(String salon) throws DataException;


    /**
     * @param salon le nom du salon
     * @param pseudo le pseudo de l'utilisateur actuel
     * @return la liste d'utilisateurs connectés dans ce salon
     */
    List<User> getUserList(String salon, String pseudo) throws DataException;

    /**
     * Cette méthode ajoute un utilisateurs à la
     * liste d'utilisateurs connectés
     *
     * @param pseudo le pseudo de l'utilisateur
     * @param salon le salon auquel l'utilisateur est connecté
     */
    void addUserToSalon(String pseudo, String salon) throws DataException;

    /**
     *
     * Cette méthode supprime l'utilisateur de la liste d'utilisateurs
     * connectés à la fin de la session
     *
     * @param pseudo le pseudo de l'utilisateur
     * @param salon le salon auquel l'utilisateur est connecté
     */
    void removeUserToSalon(String pseudo, String salon);

    /**
     * Cette méthode permet de récuperer un message par id
     *
     * @param id identifique le message
     * @return Un message que correspond à l'id
     */
    Message getMessage(Long id) throws DataException;

    /**
     * Cette méthode permet de supprimer un message
     *
     * @param salon Salon dans se trouve le message
     * @param id intificateur pour le message
     */
    void deleteMessage(String salon, Long id);

    /**
     * Retourne la liste d'utilisateurs qui sont dans l'instant solicité dans un salon
     *
     * @param user Utilisateur
     * @return liste avec les salons
     */
    List<Salon> getSalonsByUser(User user);

    /**
     * Trouve le dernier message
     *
     * @param salon Salon dans se trouve le message
     * @return Le dernier message pour un salon
     */
    Message getDernierMessage(String salon) throws DataException;


    /**
     *
     * Cette méthode retourne la liste de messages selon le nom du salon
     * à partir d'un nombre de message indiqué
     *
     * @param salon le nom du salon
     * @param id l'id du message à partir duquel on veut récupérer les autres messages
     * @return la liste de messages
     * @throws DataException si le salon n'existe pas
     */
    List<Message> getLastMessagesAfterId(String salon, Long id) throws DataException;


    /**
     * @param salon le nom du salon
     * @param message le message avec le nouveau contenu
     * @return le message modifié
     * @throws DataException retourne une exception si il ne s'agit pas du dernier message.
     */
    Message updateLastMessage(String salon, Message message) throws DataException;
}