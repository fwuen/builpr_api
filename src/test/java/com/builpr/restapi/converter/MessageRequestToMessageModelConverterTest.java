package com.builpr.restapi.converter;

import com.builpr.database.bridge.message.Message;
import com.builpr.restapi.model.Request.SendMessageRequest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marco Geißler
 */
public class MessageRequestToMessageModelConverterTest {

    private SendMessageRequest request;

    public MessageRequestToMessageModelConverterTest() {
        request = new SendMessageRequest()
                .setReceiverID(5)
                .setText("hallo");
    }

    @Test
    public void testConvert() {
        int userID = 6;
        Message message = MessageRequestToMessageModelConverter.from(request, userID);

        Assert.assertEquals(request.getReceiverID(), message.getReceiverId());
        Assert.assertEquals(request.getText(), message.getText());
        Assert.assertEquals(userID, message.getSenderId());
    }
}
