package com.chat.service;

import com.chat.modele.Message;
import com.chat.modele.Salon;
import com.chat.util.DataException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SOFIAA FADDI
 * @version 1.0
 * @since 1.0 11/30/16.
 */
@Service
public class ChatSalonService implements GestionSalon{
    private static final Logger LOGGER = Logger.getLogger(ChatSalonService.class.getName());
    private List<Salon> salonList = new ArrayList<>();


    @Override
    public void addSalon(String name) throws DataException {
        Salon salon;

        try {
            salon = getSalonByName(name);
        }catch (DataException e){
            LOGGER.log(Level.OFF, e.getMessage(), e);
            salon = null;
        }

        if (salon == null) {
            salon = new Salon();
            salon.setName(name);
            salon.setLastMessage(null);
            salonList.add(salon);
        } else {
            throw new DataException("The channel already exists");
        }
    }

    @Override
    public void removeSalon(String name) throws DataException {
        getSalonByName(name);
        salonList.removeIf(s -> s.getName().equals(name));
    }

    @Override
    public Salon getSalonByName(String salon) throws DataException {


        if (salonList.stream().filter(s -> s.getName()
                .equals(salon)).findFirst().isPresent()) {
            return salonList.stream().filter(s -> s.getName()
                    .equals(salon)).findFirst().get();
        } else {
            throw new DataException("The channel doesn't exists");
        }
    }
}

