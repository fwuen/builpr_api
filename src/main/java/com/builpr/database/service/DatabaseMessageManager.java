package com.builpr.database.service;

import com.builpr.database.bridge.message.Message;
import com.builpr.database.bridge.message.MessageManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Database manager that covers the message-table
 */
public class DatabaseMessageManager extends DatabaseManager<MessageManager> {

    /**
     * connects the manager to the database
     */
    public DatabaseMessageManager() {
        super(MessageManager.class);
    }

    /**
     * gets a message from the database by its id
     *
     * @param id the message-id
     * @return if it exists, the message, otherwise null
     */
    public Message getByID(int id) {
        Optional<Message> message = getDao().stream().filter(Message.MESSAGE_ID.equal(id)).findFirst();

        return message.orElse(null);
    }

    /**
     * persists a message in the database
     *
     * @param message the message to be persisted
     * @return the persisted message (possibly with values given by the database)
     */
    public Message persist(Message message) {
        return getDao().persist(message);
    }

    /**
     * gets a list of all messages between two users
     *
     * @param userID1 first user of the conversation
     * @param userID2 second user of the conversation
     * @return a list containing the messages of the conversation
     */
    public List<Message> getMessagesForConversation(int userID1, int userID2) {
        return getDao().stream().filter(Message.SENDER_ID.in(userID1, userID2).and(Message.RECEIVER_ID.in(userID1, userID2))).collect(Collectors.toList());
    }

    /**
     * updates a message in the database
     *
     * @param message the message to be updated
     * @return the updated message (possibly wiht values given by the database)
     */
    public Message update(Message message) {
        return getDao().update(message);
    }

    /**
     * deletes a message from the database
     *
     * @param message the message to be deleted
     * @return the deleted message
     */
    public Message delete(Message message) {
        return getDao().remove(message);
    }

    /**
     * deletes a message with the given id
     *
     * @param id the id of the message
     * @return the deleted message
     */
    public Message deleteByID(int id) {
        return getDao().remove(getByID(id));
    }

    /**
     * checks if a message is present in the database
     *
     * @param message the message to be checked
     * @return true, if present, false if not
     */
    public boolean isPresent(Message message) {
        return getDao().stream().anyMatch(Message.MESSAGE_ID.equal(message.getMessageId()));
    }
}
