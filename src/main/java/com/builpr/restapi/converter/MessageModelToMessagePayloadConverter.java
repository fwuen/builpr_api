package com.builpr.restapi.converter;

import com.builpr.database.bridge.message.Message;
import com.builpr.restapi.model.Response.message.MessagePayload;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Marco Geißler
 */
public class MessageModelToMessagePayloadConverter {

    public static MessagePayload from(Message message) {
        return new MessagePayload()
                .setMessageID(message.getMessageId())
                .setSenderID(message.getSenderId())
                .setReceiverID(message.getReceiverId())
                .setText(message.getText())
                .setRead(message.getRead())
                .setSendTime(message.getSendTime() == null ? new Timestamp(System.currentTimeMillis()).toString() :
                        message.getSendTime().toString());
    }

}
