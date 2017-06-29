package com.builpr.restapi.controller;

import com.builpr.database.bridge.message.Message;
import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabaseMessageManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.converter.MessageModelToMessagePayloadConverter;
import com.builpr.restapi.converter.MessageRequestToMessageModelConverter;
import com.builpr.restapi.error.exception.UserNotFoundException;
import com.builpr.restapi.model.Request.SendMessageRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.message.ListMessagePayload;
import com.builpr.restapi.model.Response.message.MessagePayload;
import com.google.common.collect.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.builpr.Constants.SECURITY_CROSS_ORIGIN;
import static com.builpr.Constants.URL_MESSAGE;

/**
 *
 */
@RestController
public class MessageController {

    private DatabaseMessageManager messageManager;

    private DatabaseUserManager userManager;

    public MessageController() {
        messageManager = new DatabaseMessageManager();
        userManager = new DatabaseUserManager();
    }

    @CrossOrigin(SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_MESSAGE, method = RequestMethod.POST)
    @ResponseBody
    public Response<MessagePayload> sendMessage(@RequestBody SendMessageRequest request, Principal principal) throws UserNotFoundException {
        if (!userManager.isPresent(request.getReceiverID())) {
            throw new UserNotFoundException("The given user does not exist");
        }

        int userID = userManager.getByUsername(principal.getName()).getUserId();
        Message message = MessageRequestToMessageModelConverter.from(request, userID);
        messageManager.persist(message);
        Response<MessagePayload> response = new Response<>();
        response.setSuccess(true);
        response.setPayload(MessageModelToMessagePayloadConverter.from(message));

        return response;
    }

    @CrossOrigin(SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_MESSAGE, method = RequestMethod.GET)
    @ResponseBody
    public Response<ListMessagePayload> listMessages(@RequestParam(value = "partnerID") int partnerID, Principal principal) throws UserNotFoundException {
        User loggedInUser = userManager.getByUsername(principal.getName());

        if (!userManager.isPresent(partnerID)) {
            throw new UserNotFoundException("The given user does not exist");
        }

        List<Message> messagesForConversation = messageManager.getMessagesForConversation(loggedInUser.getUserId(), partnerID);
        List<MessagePayload> messagePayloadList = Lists.newArrayList();
        // convert all the messages to message payloads and mark them as read
        for (Message message :
                messagesForConversation) {
            messagePayloadList.add(MessageModelToMessagePayloadConverter.from(message));
            if(message.getReceiverId() == loggedInUser.getUserId()) {
                message.setRead(true);
            }
            messageManager.update(message);
        }

        Response<ListMessagePayload> response = new Response<>();
        response.setSuccess(true);
        response.setPayload(new ListMessagePayload(messagePayloadList));
        return response;
    }

}
