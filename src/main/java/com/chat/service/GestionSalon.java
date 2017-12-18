package com.chat.service;

import com.chat.modele.Salon;
import com.chat.util.DataException;

/**
 * @author SOFIAA FADDI
 * @version 1.0
 * @since 1.0 11/30/16.
 */
public interface GestionSalon {

    /**
     *
     * Ajoute un nouveau salon
     *
     * @param name le nom du salon
     * @throws DataException retourne une exception si le salon exists déjà
     */
    void addSalon(String name) throws DataException;

    /**
     *
     * Retourne le salon par son nom
     *
     * @param salon le nom du salon
     * @return l'entité du salon
     * @throws DataException une exception s'il n'existe pas
     */
    Salon getSalonByName(String salon) throws DataException;

    /**
     *
     * Supprime le salon
     *
     * @param name le nom du salon
     * @throws DataException retourne une exception si le salon n'existe pas
     */
    void removeSalon(String name) throws DataException;
}
