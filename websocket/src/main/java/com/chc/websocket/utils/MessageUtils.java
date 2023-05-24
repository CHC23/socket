package com.chc.websocket.utils;

import com.alibaba.fastjson.JSONObject;
import com.chc.websocket.entity.MsgData;
import com.chc.websocket.entity.Result;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageUtils {

    public static void sendMessage(String userId,Session session,MsgData data){
        try {
            session.getBasicRemote().sendText(JSONObject.toJSONString(data));
        } catch (Exception e){
            log.error("#MessageUtils#sendMessage#userId = {},消息发送异常==>",userId,e);
        }
    }
}
