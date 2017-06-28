package com.builpr.database.service;

import com.builpr.database.bridge.message.Message;
import com.builpr.database.bridge.message.MessageManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
public class DatabaseMessageManager extends DatabaseManager<MessageManager> {

    public DatabaseMessageManager() {
        super(MessageManager.class);
    }

    public Message getByID(int id) {
        Optional<Message> message = getDao().stream().filter(Message.MESSAGE_ID.equal(id)).findFirst();

        return message.orElse(null);
    }

    public Message persist(Message message) {
        return getDao().persist(message);
    }

    public List<Message> getMessagesForConversation(int userID1, int userID2) {
        return getDao().stream().filter(Message.SENDER_ID.in(userID1, userID2).and(Message.RECEIVER_ID.in(userID1, userID2))).collect(Collectors.toList());
    }

    public Message update(Message message) {
        return getDao().update(message);
    }

    public void delete(Message message) {
        getDao().remove(message);
    }

    public void deleteByID(int id) {
        getDao().remove(getByID(id));
    }

    public boolean isPresent(Message message) {
        return getDao().stream().anyMatch(Message.MESSAGE_ID.equal(message.getMessageId()));
    }
}
