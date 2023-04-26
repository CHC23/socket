package com.chc.websocket.websocket;

import com.chc.websocket.utils.RedisTemplateUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ServerEndpoint("/socket/{userId}")
public class WebSocketServer {
    @Resource
    private RedisTemplateUtil redisTemplate;
}