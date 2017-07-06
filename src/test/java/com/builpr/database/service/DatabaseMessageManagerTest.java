package com.builpr.database.service;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.message.Message;
import com.builpr.database.bridge.message.MessageImpl;
import com.builpr.database.bridge.message.MessageManager;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.bridge.user.UserManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Marco Geißler
 *
 * tests for the DatabaseMessageManager
 */
public class DatabaseMessageManagerTest {

    private DatabaseMessageManager databaseMessageManager;
    private DatabaseUserManager userManager;

    private static Message testMessage1;
    private static Message testMessage2;
    private static User sender;
    private static User receiver;

    private static final String SENDER_NAME = "sender";
    private static final String RECEIVER_NAME = "receiver";

    public DatabaseMessageManagerTest() {
        databaseMessageManager = new DatabaseMessageManager();
        userManager = new DatabaseUserManager();
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        sender = new UserImpl()
                .setUsername(SENDER_NAME)
                .setPassword("abcdefg")
                .setEmail("sender@test.de")
                .setFirstname("Sender")
                .setLastname("Test")
                .setBirthday(new Date(System.currentTimeMillis()))
                .setShowName(true)
                .setShowEmail(false)
                .setShowBirthday(false);

        receiver = new UserImpl()
                .setUsername(RECEIVER_NAME)
                .setPassword("abcdefg")
                .setEmail("receiver@test.de")
                .setFirstname("receiver")
                .setLastname("Test")
                .setBirthday(new Date(System.currentTimeMillis()))
                .setShowName(true)
                .setShowEmail(false)
                .setShowBirthday(false);


        MessageManager messageManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(MessageManager.class);
        UserManager userManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(UserManager.class);

        Optional<User> user1 = userManager.stream().filter(User.USERNAME.equal(SENDER_NAME)).findFirst();
        Optional<User> user2 = userManager.stream().filter(User.USERNAME.equal(RECEIVER_NAME)).findFirst();

        user1.ifPresent(userManager::remove);
        user2.ifPresent(userManager::remove);

        userManager.persist(sender);
        userManager.persist(receiver);

        testMessage1 = new MessageImpl()
                .setSenderId(sender.getUserId())
                .setReceiverId(receiver.getUserId())
                .setText("hallo");

        testMessage2 = new MessageImpl()
                .setSenderId(sender.getUserId())
                .setReceiverId(receiver.getUserId())
                .setText("hallo2");

        messageManager.stream().filter(Message.SENDER_ID.equal(sender.getUserId()).
                and(Message.RECEIVER_ID.equal(receiver.getUserId())))
                .forEach(messageManager.remover());

        messageManager.persist(testMessage1);
        messageManager.persist(testMessage2);
    }

    @Test
    public void testGetByID() {
        Message message = databaseMessageManager.getByID(testMessage1.getMessageId());

        Assert.assertEquals(testMessage1.getSenderId(), message.getSenderId());
        Assert.assertEquals(testMessage1.getReceiverId(), message.getReceiverId());
        Assert.assertEquals(testMessage1.getText(), message.getText());
    }

    @Test
    public void testPersist() {
        Message testMessage = new MessageImpl()
                .setSenderId(sender.getUserId())
                .setReceiverId(receiver.getUserId())
                .setText("hallo");

        databaseMessageManager.persist(testMessage);
        MessageManager messageManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(MessageManager.class);

        Message message = messageManager.stream().filter(Message.MESSAGE_ID.equal(testMessage.getMessageId())).findAny().get();

        Assert.assertNotNull(message);

        messageManager.remove(message);
    }

    @Test
    public void testGetMessagesForConversation() {
        List<Message> actualList = databaseMessageManager.getMessagesForConversation(sender.getUserId(), receiver.getUserId());

        Assert.assertEquals(2, actualList.size());
    }

    @Test
    public void testUpdate() {
        testMessage1.setText("another one");
        databaseMessageManager.update(testMessage1);

        MessageManager messageManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(MessageManager.class);

        Message message = messageManager.stream().filter(Message.MESSAGE_ID.equal(testMessage1.getMessageId())).findFirst().get();

        Assert.assertEquals("another one", message.getText());
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void testDelete() {
        Message message = new MessageImpl()
                .setSenderId(sender.getUserId())
                .setReceiverId(receiver.getUserId())
                .setText("delete");
        MessageManager messageManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(MessageManager.class);
        messageManager.persist(message);
        databaseMessageManager.delete(message);

        Optional<Message> messageOptional = messageManager.stream().filter(Message.MESSAGE_ID.equal(message.getMessageId())).findFirst();

        Assert.assertFalse(messageOptional.isPresent());

    }

    @Test
    public void testDeleteByID() {
        Message message = new MessageImpl()
                .setSenderId(sender.getUserId())
                .setReceiverId(receiver.getUserId())
                .setText("delete");
        MessageManager messageManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(MessageManager.class);
        messageManager.persist(message);
        databaseMessageManager.deleteByID(message.getMessageId());

        Optional<Message> messageOptional = messageManager.stream().filter(Message.MESSAGE_ID.equal(message.getMessageId())).findFirst();

        Assert.assertFalse(messageOptional.isPresent());
    }

    @Test
    public void testIsPresent() {
        Message message = new MessageImpl()
                .setSenderId(sender.getUserId())
                .setReceiverId(receiver.getUserId());

        Assert.assertTrue(databaseMessageManager.isPresent(testMessage1));
        Assert.assertTrue(databaseMessageManager.isPresent(testMessage2));
        Assert.assertFalse(databaseMessageManager.isPresent(message));
    }
}
