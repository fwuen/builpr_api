package com.builpr.restapi.model.Response.message;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 */
public class ListMessagePayload {

    @Getter
    @Setter
    private List<MessagePayload> messageList;

    public ListMessagePayload(List<MessagePayload> messageList) {
        this.messageList = messageList;
    }
}
