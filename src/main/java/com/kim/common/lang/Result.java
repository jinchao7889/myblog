package com.kim.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 5887391604554532906L;//序列化uid

    private int code;   //200正常，非200表示访问异常
    private String msg; //提示信息
    private Object data;//返回数据

    public static Result succ(Object data){
        return succ(200,"操作成功！",data);
    }

    public static Result succ(int code,String msg,Object data){
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    public static Result fail(String msg){
        return fail(400,msg,null);
    }

    public static Result fail(String msg,Object data){
        return fail(400,msg,data);
    }

    public static Result fail(int code,String msg,Object data){
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }
}
