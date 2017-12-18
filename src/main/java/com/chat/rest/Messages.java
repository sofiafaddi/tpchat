package com.chat.rest;

import com.chat.modele.Message;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sofiaa FADDI
 * @version 1.0
 * @since 1.0 12/5/16
 */
@XmlRootElement
public class Messages {

    private List<Message> messages;

    public Messages(){
        this.messages = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}