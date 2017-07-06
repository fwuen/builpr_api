package com.builpr.restapi.model.Request;

import lombok.Getter;

/**
 *@author Marco Geißler
 */
public class SendMessageRequest {

    @Getter
    private int receiverID;

    @Getter
    private String text;

    public SendMessageRequest setReceiverID(int receiverID) {
        this.receiverID = receiverID;
        return this;
    }

    public SendMessageRequest setText(String text) {
        this.text = text;
        return this;
    }
}
