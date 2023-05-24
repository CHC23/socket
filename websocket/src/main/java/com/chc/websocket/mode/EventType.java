package com.chc.websocket.mode;

public enum EventType {
    LOGIN(1,"LOGIN","登录");

    private final int key;
    private final String value;
    private final String describe;

    EventType(int key,String value,String describe){
        this.key = key;
        this.value = value;
        this.describe = describe;
    }

    public String getValue(){
        return this.value;
    }
}
