package com.chat.modele;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sophiaa Faddi
 * @version 1.0
 */
@XmlRootElement
public class Message {

    private Long id;

    private String contenu;
    private User user;
    private Date date;

    public Message(){
        /* on ajoute le constructeur par défaut à cause de jackson pour la conversion de json à l'objet*/
        date = new Date();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }


    public void setContenu(String contenu) {
        this.contenu = contenu;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return user + ": " + contenu + "  ----" + "\n";
    }


    public String getHourFormatted() {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatter1 = new SimpleDateFormat("dd/mm/yy");

        String hour = formatter.format(this.date);
        String day = formatter1.format(this.date);
        return "Envoyé le " + day + " à " + hour;
    }
}