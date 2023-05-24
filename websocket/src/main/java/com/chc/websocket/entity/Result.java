package com.chc.websocket.entity;
import lombok.Data;

@Data
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Integer code) {
        this.code = code;
    }

    private Result(int code,String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(0);
    }

    public static <T> Result<T> success(String message,T data) {
        return new Result<>(0, message,data);
    }

    public static <T> Result<T> success(int code, String msg) {
        return new Result<>(code, msg);
    }


    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }
}
