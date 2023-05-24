package com.chc.websocket.entity;

import jakarta.websocket.Session;
import lombok.Data;

@Data
public class UserInfo {
    private String userId;
    private String userName;
    private String avatar;
    private Integer age;
    private Integer gender;
    private String birthday;
}
