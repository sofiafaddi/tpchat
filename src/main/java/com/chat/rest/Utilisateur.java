package com.chat.rest;

import com.chat.modele.Salon;
import com.chat.modele.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Sofiaa FADDI
 * @version 1.0
 * @since 1.0 12/5/16
 */
@XmlRootElement
public class Utilisateur extends User {

    private List<Salon> salons;

    public Utilisateur(){
        /* On a un constructeur par défault por generer le XML*/
    }

    public Utilisateur(String pseudo, String prenom, String nom, String mail) {
        super(pseudo, prenom, nom, mail);
    }

    public List<Salon> getSalons() {
        return this.salons;
    }

    public void setSalons(List<Salon> salons) {
        this.salons = salons;
    }


}
