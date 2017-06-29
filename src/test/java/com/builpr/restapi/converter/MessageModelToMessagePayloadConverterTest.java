package com.builpr.restapi.converter;

import com.builpr.database.bridge.message.Message;
import com.builpr.database.bridge.message.MessageImpl;
import com.builpr.restapi.model.Response.message.MessagePayload;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

public class MessageModelToMessagePayloadConverterTest {

    private Message message;

    public MessageModelToMessagePayloadConverterTest() {
        message = new MessageImpl()
                .setSenderId(5)
                .setReceiverId(6)
                .setText("hallo")
                .setRead(false)
                .setSendTime(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testConvert() {
        MessagePayload payload = MessageModelToMessagePayloadConverter.from(message);

        Assert.assertEquals(message.getSenderId(), payload.getSenderID());
        Assert.assertEquals(message.getReceiverId(), payload.getReceiverID());
        Assert.assertEquals(message.getText(), payload.getText());
        Assert.assertEquals(message.getRead(), payload.isRead());
        Assert.assertEquals(message.getSendTime().toString(), payload.getSendTime());
    }

}
