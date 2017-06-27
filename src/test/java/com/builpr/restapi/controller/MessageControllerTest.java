package com.builpr.restapi.controller;


import com.builpr.database.bridge.message.Message;
import com.builpr.database.bridge.message.MessageImpl;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.DatabaseMessageManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.model.Request.SendMessageRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.message.MessagePayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.builpr.Constants.URL_MESSAGE;
import static com.builpr.Constants.URL_PROFILE;
import static com.builpr.Constants.URL_PROFILE_EDIT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageControllerTest extends ControllerTest {

    private static User user1;
    private static User user2;

    private static Message testMessage1;
    private static Message testMessage2;

    private static final String USER_1_USERNAME = "user1";
    private static final String USER_2_USERNAME = "user2";

    private static DatabaseUserManager userManager;
    private static DatabaseMessageManager messageManager;

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String KEY = "partnerID";

    @BeforeClass
    public static void setUpDatabase() {
        userManager = new DatabaseUserManager();
        messageManager = new DatabaseMessageManager();

        if (userManager.isPresent(USER_1_USERNAME)) {
            userManager.deleteByUsername(USER_1_USERNAME);
        }

        if (userManager.isPresent(USER_2_USERNAME)) {
            userManager.deleteByUsername(USER_2_USERNAME);
        }

        user1 = new UserImpl()
                .setUsername(USER_1_USERNAME)
                .setEmail("user1@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(Date.valueOf("1990-10-10"))
                .setFirstname("Message")
                .setLastname("User")
                .setShowBirthday(true)
                .setShowEmail(true)
                .setShowName(true);

        user2 = new UserImpl()
                .setUsername(USER_2_USERNAME)
                .setEmail("user2@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(Date.valueOf("1990-10-10"))
                .setFirstname("Message")
                .setLastname("User")
                .setShowBirthday(true)
                .setShowEmail(true)
                .setShowName(true);

        userManager.persist(user1);
        userManager.persist(user2);

        List<Message> messagesForTestUsers = messageManager.getMessagesForConversation(user1.getUserId(),
                user2.getUserId());

        for (Message message :
                messagesForTestUsers) {
            messageManager.delete(message);
        }

        testMessage1 = new MessageImpl()
                .setSenderId(user1.getUserId())
                .setReceiverId(user1.getUserId())
                .setText("Testmessage1");
        testMessage2 = new MessageImpl()
                .setSenderId(user2.getUserId())
                .setReceiverId(user2.getUserId())
                .setText("Testmessage2")
                .setRead(true);

        messageManager.persist(testMessage1);
        messageManager.persist(testMessage2);
    }

    @AfterClass
    public static void tearDownDatabase() {
        if (userManager.isPresent(USER_1_USERNAME)) {
            userManager.deleteByUsername(USER_1_USERNAME);
        }
        if (userManager.isPresent(USER_2_USERNAME)) {
            userManager.deleteByUsername(USER_2_USERNAME);
        }

        if (messageManager.isPresent(testMessage1)) {
            messageManager.delete(testMessage1);
        }
        if (messageManager.isPresent(testMessage2)) {
            messageManager.delete(testMessage2);
        }
    }

    @Test
    @WithMockUser(USER_1_USERNAME)
    public void testSendMessage() throws Exception {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .setReceiverID(user2.getUserId())
                .setText("Hallo");

        MvcResult result = mockMvc.perform(
                post(URL_MESSAGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sendMessageRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Map actualMessagePayload = (LinkedHashMap)response.getPayload();

        Assert.assertEquals(user1.getUserId(), actualMessagePayload.get("senderID"));
        Assert.assertEquals(user2.getUserId(), actualMessagePayload.get("receiverID"));
        Assert.assertEquals("Hallo", actualMessagePayload.get("text"));
        Assert.assertEquals(false, actualMessagePayload.get("read"));
    }

    @Test
    @WithMockUser(USER_1_USERNAME)
    public void testListMessages() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL_MESSAGE).param(KEY, user2.getUserId()+"")
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


    }
}
