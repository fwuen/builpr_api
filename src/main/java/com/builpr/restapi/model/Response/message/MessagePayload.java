package com.builpr.restapi.model.Response.message;

import lombok.Getter;

/**
 *@author Marco Geißler
 */
public class MessagePayload {

    @Getter
    private int messageID;

    @Getter
    private int senderID;

    @Getter
    private int receiverID;

    @Getter
    private String text;

    @Getter
    private boolean read;

    @Getter
    private String sendTime;

    public MessagePayload setMessageID(int messageID) {
        this.messageID = messageID;
        return this;
    }

    public MessagePayload setSenderID(int senderID) {
        this.senderID = senderID;
        return this;
    }

    public MessagePayload setReceiverID(int receiverID) {
        this.receiverID = receiverID;
        return this;
    }

    public MessagePayload setText(String text) {
        this.text = text;
        return this;
    }

    public MessagePayload setRead(boolean read) {
        this.read = read;
        return this;
    }

    public MessagePayload setSendTime(String sendTime) {
        this.sendTime = sendTime;
        return this;
    }
}
