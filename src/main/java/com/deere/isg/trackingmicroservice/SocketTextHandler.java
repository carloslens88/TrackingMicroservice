package com.deere.isg.trackingmicroservice;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author Carlos Lens
 */
@Component
public class SocketTextHandler extends TextWebSocketHandler {

    /**
     *
     * @param session current session
     * @param message message or information to handle
     * @throws IOException when an error occurred trying to parse payload to json
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {

        String payload = message.getPayload();
        JSONObject jsonObject = new JSONObject(payload);
        session.sendMessage(new TextMessage(jsonObject.get("someAttr") + " sending test message for receiver"));
    }
}
