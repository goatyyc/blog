package com.learn.site.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class APIResponse <T>{
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private String code;
    private String msg;
    private T data;

    public APIResponse(String code){this.code = code;}

    public APIResponse(String code, T data){
        this.code = code;
        this.data = data;
    }

    public static APIResponse success(){
        return new APIResponse(SUCCESS);
    }

    public static APIResponse success(Object data){
        return new APIResponse(SUCCESS, data);
    }

    public static APIResponse fail(String msg){
        return new APIResponse(FAIL,msg);
    }

    public static APIResponse withCode(String errorCode){
        return new APIResponse(errorCode);
    }




}
