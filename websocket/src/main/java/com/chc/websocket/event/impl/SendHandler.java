package com.chc.websocket.event.impl;

import com.alibaba.fastjson.JSONObject;
import com.chc.websocket.constant.CacheKey;
import com.chc.websocket.entity.MsgData;
import com.chc.websocket.entity.UserInfo;
import com.chc.websocket.event.EventHandler;
import com.chc.websocket.utils.MessageUtils;
import com.chc.websocket.utils.RedisTemplateUtil;
import jakarta.annotation.Resource;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LOGIN")
public class SendHandler implements EventHandler {
    @Resource
    private RedisTemplateUtil redisTemplate;

    @Override
    public void EventHandler(String currentUser, Session session, String message, MsgData data) {
        if (null != data.getRecipient()){
            return;
        }
        Object userObj = redisTemplate.hget(CacheKey.ONLINE_TABLE,data.getRecipient());
        if (null == userObj){
            log.error("#WebSocketServer#close#用户【{}】不存在",data.getRecipient());
            return;
        }
        UserInfo user = JSONObject.parseObject(userObj.toString(),UserInfo.class);
        if (null == user.getSession()){
            return;
        }
        MessageUtils.sendMessage(user.getUserId(),user.getSession(),data);
    }
}
