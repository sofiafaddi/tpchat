package com.chat.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sofiaa FADDI
 * @version 1.0
 * @since 1.0 11/23/16.
 */
public class User {
    public enum Status {ONLINE, OFFLINE}

    private String pseudo;
    private String prenom;
    private String nom;
    private String mail;
    private Status etat;
    private List<Salon> salonList;


    public User(){
        /* On ajoute le constructeur par défaut por l'instantiation qui fait jackson avec le json quand on appel
        * par le post de addMessage */
        salonList = new ArrayList<>();
    }

    public User(String pseudo, String prenom, String nom, String mail) {
        this.pseudo = pseudo;
        this.prenom = prenom;
        this.nom = nom;
        this.mail = mail;
        salonList = new ArrayList<>();
    }

    public Status getEtat() {
        return etat;
    }

    public void setEtat(Status etat) {
        this.etat = etat;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Salon> getSalonList() {
        return salonList;
    }

    public void setSalonList(List<Salon> salonList) {
        this.salonList = salonList;
    }

    @Override
    public boolean equals(Object obj) {

        if (super.equals(obj)) {
            User u = (User) obj;
            return this.getPseudo().equals(u.getPseudo());
        } else {
            return false;
        }
    }
}
