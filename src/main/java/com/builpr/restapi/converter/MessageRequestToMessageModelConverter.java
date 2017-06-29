package com.builpr.restapi.converter;

import com.builpr.database.bridge.message.Message;
import com.builpr.database.bridge.message.MessageImpl;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.model.Request.SendMessageRequest;

import java.security.Principal;

/**
 *
 */
public class MessageRequestToMessageModelConverter {

    public static Message from(SendMessageRequest request, int userID) {
        return new MessageImpl()
                .setSenderId(userID)
                .setReceiverId(request.getReceiverID())
                .setText(request.getText());
    }

}
