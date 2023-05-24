package com.chc.websocket.websocket;

import com.alibaba.fastjson.JSONObject;
import com.chc.websocket.constant.CacheKey;
import com.chc.websocket.entity.MsgData;
import com.chc.websocket.mode.EventType;
import com.chc.websocket.utils.MessageUtils;
import com.chc.websocket.utils.RedisTemplateUtil;
import com.chc.websocket.entity.UserInfo;
import com.chc.websocket.utils.SpringUtil;
import jakarta.annotation.Resource;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ServerEndpoint("/socket/{userId}")
public class WebSocketServer {

    private RedisTemplateUtil redisTemplate = SpringUtil.getBean(RedisTemplateUtil.class);

    private String userId;
    /**
     * 打开连接，保存用户数据到缓存
     */
    @OnOpen
    public void open(Session session,@PathParam("userId") String userId){
        UserInfo user = new UserInfo(){{
            setUserId(userId);
        }};
        this.userId = userId;
        boolean t = redisTemplate.hset(CacheKey.ONLINE_TABLE,userId,user);
        Map<String,Object> data = new HashMap<String,Object>(){{
            put("message","登录成功");
        }};

        MessageUtils.sendMessage(userId,session, MsgData.init(EventType.LOGIN.getValue(),data));
    }

    @OnClose
    public void close(){
        log.info("#WebSocketServer#close#用户【{}】下线",this.userId);
        Object userObj = redisTemplate.hget(CacheKey.ONLINE_TABLE,userId);
        if (null == userObj){
            log.error("#WebSocketServer#close#用户【{}】不存在",this.userId);
            return;
        }
        UserInfo user = JSONObject.parseObject(userObj.toString(),UserInfo.class);
        if (null != user.getSession()){
            try {
                user.getSession().close();
            } catch (IOException e) {
                log.error("#WebSocketServer#close#用户【{}】下线，连接关闭异常==>",userId,e);
            }
        }
    }

    @OnError
    public void onError(Throwable throwable,Session session){
        log.error("#WebSocketServer#onError#websocket异常,user=【{}】,error==>",this.userId,throwable);
    }

    @OnMessage
    public void sendMessage(String message, Session session){

    }
}