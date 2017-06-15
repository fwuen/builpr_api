package com.builpr.restapi.model.Request;

import lombok.Getter;

/**
 *
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
