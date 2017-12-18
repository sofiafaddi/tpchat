package com.chat.modele;

import java.util.Date;

/**
 * @author Sofiaa FADDI 
 * @version 1.0
 * @since 1.0 11/30/16.
 */
public class Salon {
    private String name;
    private Date lastMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Date lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public String toString() {
        return getName();
    }
}
