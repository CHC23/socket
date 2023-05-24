package com.chc.websocket.event;

import com.chc.websocket.entity.MsgData;
import jakarta.websocket.Session;

public interface EventHandler {
    /**
     * 处理各种类型的消息发送
     */
    void EventHandler(String currentUser, Session session, String message, MsgData data);
}
