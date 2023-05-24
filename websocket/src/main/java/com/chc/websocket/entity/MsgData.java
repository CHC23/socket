package com.chc.websocket.entity;

import lombok.Data;

import java.util.Map;

@Data
public class MsgData {
    private String eventName;
    private Map<String, Object> data;
    private String recipient;
    private int code;

    private MsgData(String eventName,Map<String, Object> data){
        this.eventName = eventName;
        this.data = data;
    }

    private MsgData(String eventName,Map<String, Object> data,String recipient){
        this.eventName = eventName;
        this.data = data;
        this.recipient = recipient;
    }

    public static MsgData init(String eventName,Map<String, Object> data){
        return new MsgData(eventName,data);
    }

    public static MsgData init(String eventName,Map<String, Object> data,String recipient){
        return new MsgData(eventName,data,recipient);
    }
}
