package com.chat.service;

import com.chat.modele.Salon;
import com.chat.modele.User;
import com.chat.rest.Utilisateur;
import com.chat.util.DataException;

import java.util.List;

/**
 * @author SOFIAA FADDI
 * @version 1.0
 * @since 1.0 11/25/16.
 */
public interface GestionUtilisateur {

    /**
     * Ajoute un nouveau utilisateur à la liste
     *
     * @param pseudo le pseudo de l'utilisateur
     * @param prenom le prenom de l'utilisateur
     * @param nom    le nom de l'utilisateur
     * @param mail   le mail de l'utilisateur
     */
    void addUser(String pseudo, String prenom, String nom, String mail) throws DataException;


    /**
     * Cette méthode évalue l'existance de l'utilisateur
     * dans la liste avec ce mail
     *
     * @param mail le mail de l'utilisateur
     * @return true si le mail déjà exists
     */
    boolean existsMail(String mail);

    /**
     * Cette méthode évalue l'existance de l'utilisateur
     * dans la liste avec ce pseudo
     *
     * @param pseudo le pseudo de l'utilisateur
     * @return true si le pseudo déjà exists
     */
    boolean existsUsername(String pseudo);


    /**
     * Cette méthode retourne l'entité de l'utilisateur selon le pseudo et
     * le salon indiqués
     *
     * @param pseudo le pseudo de l'utilisateur
     * @return l'entité de l'utilisateur
     */
    User getUserByPseudo(String pseudo);

    /**
     * Permettre la modification d'un utilisateur
     *
     * @param pseudo le nouveau pseudo pour l'utilisateur
     * @param user   objet d'utlisateur
     * @return le user modifié
     * @throws DataException si l'utilisateur n'exists pas
     */
    User updateUser(String pseudo, User user) throws DataException;


    /**
     * Cette méthode retourne l'utilisateur avec tous les salons où il a participé.
     *
     * @param pseudo le pseudo de l'utilisateur
     * @return l'entité Utilisateur c-a-d l'utilisateur avec les salons associés
     * @throws DataException si l'utilisateur n'existe pas
     */
    Utilisateur getAllChannelsByUser(String pseudo) throws DataException;

    /**
     * Cette méthode crée une instance utilisateur à partir de 'user'
     *
     * @param user objet d'utlisateur
     * @return l'entité Utilisateur c-a-d l'utilisateur avec les salons associés
     */
    Utilisateur newUtilisateur(User user);


    /**
     * @param salon le nom du salon
     * @param pseudo le pseudo de l'utilisateur
     */
    void addSalonUser(Salon salon, String pseudo);


    /**
     * @param pseudo le pseudo de l'utilisateur
     * @return la liste de salons visités
     */
    List<Salon> getSalonByUser(String pseudo);
}
