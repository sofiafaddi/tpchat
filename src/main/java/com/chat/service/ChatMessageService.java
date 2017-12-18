package com.chat.service;

import com.chat.modele.Message;
import com.chat.modele.Salon;
import com.chat.modele.User;
import com.chat.util.Constante;
import com.chat.util.DataException;
import com.chat.util.IdentifierUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Cette classe garde tous les fonctions sur le salon et les messages
 *
 * @author Sofia Faddi
 */
@Service
public class ChatMessageService implements GestionMessage {

    private static final Logger LOGGER = Logger.getLogger(ChatMessageService.class.getName());

    private Map<Salon, List<Message>> map = new HashMap<>();
    private Map<Salon, List<User>> userPerSalon = new HashMap<>();

    @Autowired
    private GestionUtilisateur gestionUtilisateur;

    @Autowired
    private GestionSalon gestionSalon;

    @Override
    public Message addMessage(String contenu, User user, String salonName) throws DataException {
        Message message = new Message();
        message.setContenu(contenu);
        message.setUser(user);
        message.setDate(new Date());
        message.setId(IdentifierUtil.getIdValue());

        Salon salon = gestionSalon.getSalonByName(salonName);
        salon.setLastMessage(message.getDate());

        if (map.containsKey(salon)) {
            map.get(salon).add(message);
        } else {
            map.put(salon, new ArrayList<>());
            map.get(salon).add(message);
        }


        return message;
    }

    @Override
    public void addSalon(String salonName) throws DataException {
        Salon salon = gestionSalon.getSalonByName(salonName);
        this.map.put(salon, new ArrayList<>());

    }

    @Override
    public List<Message> getMessages(String salonName) throws DataException {
        Salon salon = gestionSalon.getSalonByName(salonName);
        if (map.containsKey(salon)) {
            return map.get(salon);
        } else {
            return map.put(salon, new ArrayList<>());
        }
    }

    @Override
    public void supprimerMessages(String salonName) throws DataException {
        Salon salon = gestionSalon.getSalonByName(salonName);
        if (map.containsKey(salon)) {
            map.remove(map.get(salon));
        }
    }

    @Override
    public int nombreMessage(String salonName) throws DataException {
        Salon salon = gestionSalon.getSalonByName(salonName);
        if (map.containsKey(salon)) {
            return map.get(salon).size();
        } else {
            return -1;
        }
    }

    public Map<Salon, List<Message>> getMap() {
        return map;
    }


    public void setMap(Map<Salon, List<Message>> map) {
        this.map = map;
    }


    @Override
    public List<User> getUserList(String salon, String pseudo) throws DataException {
        Salon salonByName = this.gestionSalon.getSalonByName(salon);
        List<User> userList = this.userPerSalon.get(salonByName);

        if (userList != null) {
            return userList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void addUserToSalon(String pseudo, String salonName) throws DataException {
        Salon salon = gestionSalon.getSalonByName(salonName);
        List<User> userList = this.userPerSalon.get(salon);

        if (userList == null) {
            userList = new ArrayList<>();
            this.userPerSalon.put(salon, userList);
        }

        /* On contrôle que l'utilisateur n'existe pas déjà dans la liste */
        if (!userList.stream().filter(u -> u.getPseudo().equals(pseudo))
                .findFirst().isPresent()) {
            User user = gestionUtilisateur.getUserByPseudo(pseudo);
            userList.add(user);

            /* On ajout à la liste des salons visités*/
            gestionUtilisateur.addSalonUser(salon, pseudo);

        }
    }

    @Override
    public void removeUserToSalon(String pseudo, String salon) {
        Map.Entry<Salon, List<User>> firstFound = this.userPerSalon.entrySet().stream()
                .filter(e -> e.getKey().getName().equals(salon))
                .findFirst().get();
        List<User> userList = this.userPerSalon.get(firstFound.getKey());
        userList.removeIf(u -> u.getPseudo().equals(pseudo));
    }

    @Override
    public Message getMessage(Long id) throws DataException {

        for (List<Message> messages : map.values()) {
            for (Message m : messages) {
                if (m.getId().equals(id)) {
                    return m;
                }
            }
        }

        throw new DataException(Constante.MSG_NOT_EXISTS);
    }


    @Override
    public Message getDernierMessage(String salon) throws DataException {
        List<Message> messages;

        messages = getMessages(salon);

        Optional<Message> max = messages.stream().max((o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        if (max.isPresent()) {
            return max.get();
        } else {
            return new Message();
        }

    }

    @Override
    public List<Message> getLastMessagesAfterId(String salon, Long id) throws DataException {
        List<Message> messages = getMessages(salon);
        Message currentMessage = getMessage(id);

        return messages.stream().filter(m ->
                m.getDate().after(currentMessage.getDate())
        ).collect(Collectors.toList());
    }

    @Override
    public Message updateLastMessage(String salon, Message message) throws DataException {
        Message lastMessage = getDernierMessage(salon);

        if (lastMessage.getId().equals(message.getId())) {
            lastMessage.setContenu(message.getContenu());
            lastMessage.setDate(message.getDate());
        } else {
            throw new DataException(Constante.MSG_ISNT_LAST);
        }

        return lastMessage;
    }

    @Override
    public void deleteMessage(String salon, Long id) {
        try {
            List<Message> messages = getMessages(salon);
            messages.remove(getMessage(id));
        } catch (DataException e) {
            LOGGER.log(Level.WARNING, "Can't remove message", e);
        }
    }


    @Override
    public List<Salon> getSalonsByUser(User user) {
        List<Salon> ret = new ArrayList<>();

        this.userPerSalon.entrySet().forEach(m -> {
            if (m.getValue().stream().anyMatch(u -> u.equals(user))) {
                ret.add(m.getKey());
            }
        });

        return ret;
    }


}